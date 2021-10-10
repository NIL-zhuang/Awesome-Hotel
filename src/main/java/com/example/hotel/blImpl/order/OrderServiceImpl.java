package com.example.hotel.blImpl.order;

import com.example.hotel.bl.VIP.VIPService;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.enums.RoomType;
import com.example.hotel.enums.VIPType;
import com.example.hotel.po.Comment;
import com.example.hotel.po.Order;
import com.example.hotel.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenyizong
 * @date 2020-03-04
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final static String RESERVE_ERROR = "预订失败";
    private final static String ROOMNUM_LACK = "预订房间数量剩余不足";
    private final static String FINISH_ORDER = "退房成功";
    private final static String WRONG_TIME = "预订时间错误";
    private final static String REPEAT_ANNUL = "不可重复撤销订单";
    private final static String COMPLETED_ORDER = "已经入住过的订单不可撤销";
    private final static String ABNORMAL_ORDER = "异常订单不可撤销";
    private final static String LABEL_ABNORMAL = "已标记为异常订单";
    private final static String CHECK_IN = "办理入住成功";
    private final static String LOW_CREDIT = "信用值过低，无法预订酒店";
    private final static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private VIPService vipService;

    @Override
    public ResponseVO addOrder(OrderVO orderVO) {
        //判断用户信用值
        UserVO user = accountService.getUserInfo(orderVO.getUserId());
        double credit = user.getCredit();
        if (!(credit > 0))
            return ResponseVO.buildFailure(LOW_CREDIT);

        int reserveRoomNum = orderVO.getRoomNum();
        int curNum = getRoomCurNumByOrder(orderVO.getHotelId(), orderVO.getCheckInDate(), orderVO.getCheckOutDate(), orderVO.getRoomType());
        if (reserveRoomNum > curNum) {
            return ResponseVO.buildFailure(ROOMNUM_LACK);
        }
        String now = getSystemDate();
        int gap = getGap(orderVO.getCheckInDate(), now);
        if (gap < 0) {
            return ResponseVO.buildFailure(WRONG_TIME);
        }
        try {
            Date date = new Date(System.currentTimeMillis());
            String curdate = sf.format(date);
            orderVO.setCreateDate(curdate);
            orderVO.setOrderState("未入住");
            Order order = new Order();
            BeanUtils.copyProperties(orderVO, order);
            orderMapper.addOrder(order);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(RESERVE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderMapper.getAllOrders();
    }

    @Override
    public List<Order> getHotelOrders(Integer hotelId) {
        List<Order> orders = getAllOrders();
        return orders.stream().filter(order -> order.getHotelId().equals(hotelId)).collect(Collectors.toList());
    }

    @Override
    public List<Order> getUserOrders(int userId) {
        return orderMapper.getUserOrders(userId);
    }


    @Override
    public ResponseVO annulOrder(int orderId) {
        //取消订单逻辑的具体实现（注意可能有和别的业务类之间的交互）
        Order order = orderMapper.getOrderById(orderId);
        switch (order.getOrderState()) {
            case "已撤销":
                return ResponseVO.buildFailure(REPEAT_ANNUL);
            case "已入住":
            case "已退房":
                return ResponseVO.buildFailure(COMPLETED_ORDER);
            case "异常订单":
                return ResponseVO.buildFailure(ABNORMAL_ORDER);
        }
        orderMapper.annulOrder(orderId);

        //扣除信用积分
        String checkInDate = order.getCheckInDate();
        int gap = getDays(checkInDate);
        if (gap == 0) {
            int change = -(int) (0.5 * order.getPrice());
            accountService.chargeCredit(order.getUserId(), change, "撤销临近订单");
        }
        hotelService.updateRoomInfo(order.getHotelId(), order.getRoomType(), -order.getRoomNum());

        return ResponseVO.buildSuccess(true);
    }


    @Override
    public ResponseVO checkIn(int orderId) {
        orderMapper.checkIn(orderId);
        Order order = orderMapper.getOrderById(orderId);
        hotelService.updateRoomInfo(order.getHotelId(), order.getRoomType(), order.getRoomNum());
        return ResponseVO.buildSuccess(CHECK_IN);
    }

    @Override
    public List<Order> probableAbnormalOrder(Integer hotelId) {
        List<Order> orders = getHotelOrders(hotelId);
        List<Order> abnormal = new ArrayList<>();
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (Order order : orders) {
            LocalDate beginDateTime = LocalDate.parse(order.getCheckInDate(), formatter);
            if (date.isAfter(beginDateTime) && order.getOrderState().equals("未入住")) {
                abnormal.add(order);
            }
        }
        return abnormal;
    }

    @Override
    public ResponseVO abnormalOrder(int orderId) {
        Order order = orderMapper.getOrderById(orderId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkIn = LocalDate.parse(order.getCheckInDate(), formatter);
        LocalDate now = LocalDate.now();
        if (now.isBefore(checkIn)) {
            return ResponseVO.buildFailure("订单未逾期，无法标记异常");
        }
        // 每次出现异常订单要减去信用值
        accountService.chargeCredit(order.getUserId(), -(int) (order.getPrice() * 0.5), "异常订单");
        orderMapper.abnormalOrder(orderId);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO handleAbnormal(int orderId, double ratio) {
        Order order = orderMapper.getOrderById(orderId);
        accountService.chargeCredit(order.getUserId(), (int) (order.getPrice() * 0.5 * ratio), "撤销异常订单");
        orderMapper.finishOrder(orderId);
        return ResponseVO.buildSuccess();
    }

    @Override
    public ResponseVO finishOrder(int orderId) {
        Order order = orderMapper.getOrderById(orderId);
        orderMapper.finishOrder(orderId);
        // 更新酒店房间数量，因为那个就是直接减法了，那我现在就减个负数叭
        hotelService.updateRoomInfo(order.getHotelId(), order.getRoomType(), -order.getRoomNum());
        accountService.chargeCredit(order.getUserId(), (int) (order.getPrice() * 0.5), "完成订单" + orderId);
        UserVO userVO = accountService.getUserInfo(order.getUserId());
        int price = (int) Math.round(order.getPrice());
        String corporation = userVO.getCorporation();
        if (corporation != null && vipService.VIPCorpCheck(corporation)) {
            vipService.addVIPCorpConsumption(userVO.getCorporation(), price);
        }
        if (userVO.getVipType() == VIPType.Client) {
            vipService.addVIPClientConsumption(order.getUserId(), price);
        }
        return ResponseVO.buildSuccess(FINISH_ORDER);
    }

    @Override
    public CommentVO getComment(int orderId) {
        Comment comment = orderMapper.getComment(orderId);
        CommentVO commentVO = new CommentVO();
        BeanUtils.copyProperties(comment, commentVO);
        return commentVO;
    }

    @Override
    public ResponseVO annulComment(int orderId) {
        CommentVO commentVO = getComment(orderId);
        Order order = orderMapper.getOrderById(orderId);
        hotelService.annulComment(commentVO, order.getHotelId());
        return ResponseVO.buildSuccess(orderMapper.annulComment(orderId));
    }

    @Override
    public List<CommentVO> getHotelComment(int hotelId) {
        List<Comment> comments = orderMapper.getHotelComment(hotelId);
        return comments.stream().map(r -> {
            CommentVO commentVO = new CommentVO();
            commentVO.setUserId(r.getUserId());
            commentVO.setSanitation(r.getSanitation());
            commentVO.setPoints(r.getPoints());
            commentVO.setOrderId(r.getOrderId());
            commentVO.setEquipment(r.getEquipment());
            commentVO.setComment(r.getComment());
            commentVO.setEnvironment(r.getEnvironment());
            commentVO.setService(r.getService());
            return commentVO;
        }).collect(Collectors.toList());
    }

    @Override
    public ResponseVO addComment(CommentVO commentVO) {
        Order order = orderMapper.getOrderById(commentVO.getOrderId());
        if (order.getOrderState().equals("未入住")) {
            return ResponseVO.buildFailure("订单未入住，无法评价");
        }
        Comment comment = new Comment() {{
            this.setComment(commentVO.getComment());
            this.setEnvironment(commentVO.getEnvironment());
            this.setEquipment(commentVO.getEquipment());
            this.setOrderId(commentVO.getOrderId());
            this.setPoints(commentVO.getPoints());
            this.setSanitation(commentVO.getSanitation());
            this.setUserId(commentVO.getUserId());
            this.setService(commentVO.getService());
        }};
        hotelService.addComment(commentVO, order.getHotelId());
        return ResponseVO.buildSuccess(orderMapper.updateComment(comment));
    }

    @Override
    public ResponseVO getUserComments(Integer userId) {
        return ResponseVO.buildSuccess(orderMapper.getUserComment(userId));
    }


    /**
     * 获取输入的订单中，发生在最近一个月的
     * 此方法获取的订单均为事实订单，即用户已经付费（已入住 或 已完成）
     * 返回数据的格式为 List<List<Order>> ，大小为31，代变从距今0天（当天）到30天中的订单，第i天的索引为i
     */
    @Override
    public List<List<Order>> getOrdersInMonth(List<Order> orders) {
        String now = getSystemDate();
        List<Order> temp = new ArrayList<>();
        List<List<Order>> summary = new ArrayList<>();

        //整理orders并归入temp中转
        for (Order order : orders) {
            String createDate = order.getCheckInDate();
            int days = getGap(now, createDate);
            if (days <= 30 && (order.getOrderState().equals("已完成") || order.getOrderState().equals("已入住")))
                temp.add(order);
        }
        //将order按天放入summary中
        for (int i = 30; i >= 0; i--) {
            List<Order> orderList = new ArrayList<>();
            for (Order order : temp) {
                int day = getGap(now, order.getCheckInDate());
                if (day == i) {
                    orderList.add(order);
                    orders.remove(order);
                }
            }
            summary.add(orderList);
        }
        return summary;
    }

    /**
     * 获取特定酒店近一个月的所有订单
     */
    @Override
    public List<List<Order>> getOrdersInMonthOfHotel(Integer hotelId) {
        List<Order> orders = getHotelOrders(hotelId);
        return getOrdersInMonth(orders);
    }


    /**
     * 获取最近一个月产生的所有订单
     */
    @Override
    public List<List<Order>> getOrdersInMonthOfAll() {
        List<Order> orders = getAllOrders();
        return getOrdersInMonth(orders);
    }

    /**
     * 基于时间进行清洗，返回与输入的时间段发生重叠的订单
     * 要改的话可以用两种情况取反，会快一点
     */
    @Override
    public List<Order> filterOrders(List<Order> orders, String beginTime, String endTime) {
        List<Order> relatedOrder = new ArrayList<>();
        if (beginTime == null || endTime == null)
            return relatedOrder;
        if (getGap(endTime, beginTime) <= 0)
            return relatedOrder;
        for (Order order : orders) {
            int gap1 = getGap(order.getCheckOutDate(), beginTime);       //订单中的退房日期 - 搜索中的入住日期
            int gap2 = getGap(endTime, order.getCheckInDate());         //搜素的退房日期 - 订单中的入住日期

            if (!((gap1 <= 0) || (gap2 <= 0))) {
                //确保订单为未入住或已入住的有效订单
                if (order.getOrderState().equals("未入住") || order.getOrderState().equals("已入住")) {
                    order.setRoomType(order.getRoomType());
                    relatedOrder.add(order);
                }
            }
        }
        return relatedOrder;
    }

    /**
     * 通过订单号和入住时间，查找可用的房间情况，并在response中返回hotel
     */
    @Override
    public HotelVO getOrderableRoom(Integer hotelId, String beginTime, String endTime) {
        HotelVO hotel = hotelService.retrieveHotelDetails(hotelId);
        List<RoomVO> rooms = hotel.getRooms();
        List<RoomVO> roomVOS = new ArrayList<>();
        //确保输入的房间情况不为空
        if (!rooms.isEmpty()) {
            List<Order> orders = getHotelOrders(hotelId);
            orders = filterOrders(orders, beginTime, endTime);
            roomVOS = checkRoomByOrder(rooms, orders);
        }

        hotel.setRooms(roomVOS);
        return hotel;
    }

    @Override
    public List<RoomVO> checkRoomByOrder(List<RoomVO> rooms, List<Order> orders) {
        HashMap<String, Integer> Type2Num = new HashMap<>();
        for (RoomVO room : rooms) {
            Type2Num.put(room.getRoomType(), room.getTotal());
        }
        for (Order order : orders) {
            if (Type2Num.containsKey(RoomType.valueOf(order.getRoomType()).toString())) {
                int curNum = Type2Num.get(RoomType.valueOf(order.getRoomType()).toString()) - order.getRoomNum();
                Type2Num.put(RoomType.valueOf(order.getRoomType()).toString(), curNum);
            }
        }

        List<RoomVO> roomVOS = new ArrayList<>();

        for (RoomVO room : rooms) {
            if (Type2Num.get(room.getRoomType()) > 0)
                room.setCurNum(Type2Num.get(room.getRoomType()));
            roomVOS.add(room);
        }
        return roomVOS;
    }

    @Override
    public Integer getRoomCurNumByOrder(Integer hotelId, String beginTime, String endTime, String type) {
        HotelVO hotelVO = getOrderableRoom(hotelId, beginTime, endTime);
        List<RoomVO> rooms = hotelVO.getRooms();
        int curNum = 0;
        for (RoomVO room : rooms) {
            if (room.getRoomType().equals(RoomType.valueOf(type).toString())) {
                curNum = room.getCurNum();
                break;
            }
        }
        return curNum;
    }


    /**
     * 获取YYYY-MM-DD格式的当前系统日期
     *
     * @return
     */
    private static String getSystemDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * 当前日期到订单中入住日期的时间差
     *
     * @param checkInDate
     * @return
     */
    private int getDays(String checkInDate) {
        String now = getSystemDate();
        return getGap(checkInDate, now);
    }

    /**
     * 返回两个日期之间相差的天数
     */
    private int getGap(String date1, String date2) {
        LocalDate Date1 = LocalDate.of(Integer.parseInt(date1.substring(0, 4)),
                Integer.parseInt(date1.substring(5, 7)), Integer.parseInt(date1.substring(8, 10)));
        LocalDate Date2 = LocalDate.of(Integer.parseInt(date2.substring(0, 4)),
                Integer.parseInt(date2.substring(5, 7)), Integer.parseInt(date2.substring(8, 10)));
        return (int) (Date1.toEpochDay() - Date2.toEpochDay());
    }

    @Override
    public ResponseVO argueAbnormalOrder(Integer orderId, String reason) {
        orderMapper.argueAbnormalOrder(orderId, reason);
        return ResponseVO.buildSuccess(true);
    }
}




