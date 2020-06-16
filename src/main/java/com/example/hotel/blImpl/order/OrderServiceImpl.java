package com.example.hotel.blImpl.order;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.po.Comment;
import com.example.hotel.po.Order;
import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
    private final static String ABNORMAL_ORDER = "已设定为异常订单";
    private final static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private RoomService roomService;

    // TODO 时间有误，注意下面那个时间；然后updateRoomInfo是在退房部份才修改curNum
    @Override
    public ResponseVO addOrder(OrderVO orderVO) {
        int reserveRoomNum = orderVO.getRoomNum();
        int curNum = hotelService.getRoomCurNum(orderVO.getHotelId(), orderVO.getRoomType());
//        int curNum = roomService.getRoomCurNumByTime(orderVO.getHotelId(), orderVO.getCheckInDate(), orderVO.getCheckOutDate(), orderVO.getRoomType());
        if (reserveRoomNum > curNum) {
            return ResponseVO.buildFailure(ROOMNUM_LACK);
        }
        try {
            Date date = new Date(System.currentTimeMillis());
            String curdate = sf.format(date);
            orderVO.setCreateDate(curdate);
            orderVO.setOrderState("已预订");
            Order order = new Order();
            BeanUtils.copyProperties(orderVO, order);
            orderMapper.addOrder(order);
            hotelService.updateRoomInfo(orderVO.getHotelId(), orderVO.getRoomType(), orderVO.getRoomNum());
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
        //String orderState = order.getOrderState();
        order.setOrderState("已撤销");//这里暂时不考虑重复撤销的情况
        //扣除信用积分
        String checkInDate = order.getCheckInDate();

        int gap = getDays(checkInDate);
        if (gap == 1 || gap == 0) {
            int userID = order.getUserId();
            UserVO user = accountService.getUserInfo(userID);
            double price = order.getPrice();
            double credit = user.getCredit();
            credit -= price * 0.5;
            accountService.updateCredit(userID, credit);
        }
        orderMapper.annulOrder(orderId);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO checkIn(int orderId) {
        return ResponseVO.buildSuccess(orderMapper.checkIn(orderId));
    }

    @Override
    public ResponseVO finishOrder(int orderId) {
        Order order = orderMapper.getOrderById(orderId);
        orderMapper.finishOrder(orderId);
        // 更新酒店房间数量，因为那个就是直接减法了，那我现在就减个负数叭
        hotelService.updateRoomInfo(order.getHotelId(), order.getRoomType(), -order.getRoomNum());
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
        return ResponseVO.buildSuccess(orderMapper.updateComment(comment));
    }

    @Override
    public ResponseVO getUserComments(Integer userId) {
        return ResponseVO.buildSuccess(orderMapper.getUserComment(userId));
    }


    /**
     * 获取输入的订单中，发生在最近一个月的
     */
    @Override
    public List<Order> getOrdersInMonth(List<Order> orders) {
        String now = getSystemDate();
        for (Order order : orders) {
            String createDate = order.getCreateDate();
            int days = getGap(now,createDate);
            if (days > 30)
                orders.remove(order);
        }

        return orders;
    }

    /**
     * 获取特定酒店近一个月的所有订单
     */
    @Override
    public ResponseVO getOrdersInMonthOfHotel(Integer hotelId) {
        List<Order> orders = getHotelOrders(hotelId);
        orders = getOrdersInMonth(orders);
        ResponseVO responseVO = new ResponseVO();
        responseVO.setContent(orders);
        responseVO.setSuccess(true);
        return responseVO;
    }


    /**
     * 获取最近一个月产生的所有订单
     */
    @Override
    public ResponseVO getOrdersInMonthOfAll() {
        List<Order> orders = getAllOrders();
        orders = getOrdersInMonth(orders);
        ResponseVO responseVO = new ResponseVO();
        responseVO.setContent(orders);
        responseVO.setSuccess(true);
        return responseVO;
    }

    /**
     * 基于时间进行清洗，返回与输入的时间段发生重叠的订单
     * 要改的话可以用两种情况取反，会快一点
     */
    @Override
    public List<Order> filterOrders(List<Order> orders, String beginTime, String endTime) {
        List<Order> relatedOrder = new ArrayList<>();
        if(beginTime==null || endTime==null)
            return relatedOrder;
        if(getGap(beginTime,endTime)<=0)
            return relatedOrder;
        for (Order order : orders) {
            int gap1 = getGap(order.getCheckOutDate(), beginTime);       //订单中的退房日期 - 搜索中的入住日期
            int gap2 = getGap(endTime, order.getCheckInDate());         //搜素的退房日期 - 订单中的入住日期

            if (!((gap1<0)||(gap2<0))) {
                if(order.getOrderState().equals("未入住") || order.getOrderState().equals("已入住"))           //确保订单为未入住的有效订单
                    relatedOrder.add(order);
            }
        }
        return relatedOrder;
    }

    @Override
    public ResponseVO markAbnormalOrders(Integer orderId) {
        //todo
        return ResponseVO.buildSuccess(ABNORMAL_ORDER);
    }


    /**
     * 获取YYYY-MM-DD格式的当前系统日期
     * @return
     */
    private static String getSystemDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * 当前日期到订单中入住日期的时间差
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

}
