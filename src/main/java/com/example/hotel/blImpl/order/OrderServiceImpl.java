package com.example.hotel.blImpl.order;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final static String RESERVE_ERROR = "预订失败";
    private final static String ROOMNUM_LACK = "预订房间数量剩余不足";
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    HotelService hotelService;
    @Autowired
    AccountService accountService;

    @Override
    public ResponseVO addOrder(OrderVO orderVO) {
        int reserveRoomNum = orderVO.getRoomNum();
        int curNum = hotelService.getRoomCurNum(orderVO.getHotelId(),orderVO.getRoomType());
        if(reserveRoomNum>curNum){
            return ResponseVO.buildFailure(ROOMNUM_LACK);
        }
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            String curdate = sf.format(date);
            orderVO.setCreateDate(curdate);
            orderVO.setOrderState("已预订");
            User user = accountService.getUserInfo(orderVO.getUserId());
            orderVO.setClientName(user.getUserName());
            orderVO.setPhoneNumber(user.getPhoneNumber());
            Order order = new Order();
            BeanUtils.copyProperties(orderVO,order);
            orderMapper.addOrder(order);
            hotelService.updateRoomInfo(orderVO.getHotelId(),orderVO.getRoomType(),orderVO.getRoomNum());
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
    public List<Order> getUserOrders(int userid) {
        return orderMapper.getUserOrders(userid);
    }

    // added by hx
    @Override
    public ResponseVO annulOrder(int orderid) {
        //取消订单逻辑的具体实现（注意可能有和别的业务类之间的交互）
        Order order = orderMapper.getOrderById(orderid);
        //String orderState = order.getOrderState();
        order.setOrderState("已撤销");//这里暂时不考虑重复撤销的情况
        //扣除信用积分
        String checkInDate = order.getCheckInDate();
        String now = getSystemDate();
        int gap = getDays(now,checkInDate);
        if(gap==1||gap==0){
            int userID = order.getUserId();
            User user = accountService.getUserInfo(userID);
            double price = order.getPrice();
            double credit = user.getCredit();
            credit -= price*0.5;

        }
        orderMapper.annulOrder(orderid);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public CommentVO getComment(int orderId) {
        return null;
    }

    @Override
    public List<CommentVO> getHotelComment(int hotelId) {
        return null;
    }

    @Override
    public ResponseVO addComment(CommentVO commentVO) {
        return null;
    }

    // added by hx
    //获取YYYY-MM-DD格式的当前系统日期
    public static String getSystemDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return sdf.format(date);
    }

    // added by hx
    //计算当前日期距离入住日期还差多少天
    public static int getDays(String now, String checkInDate){
        int yearGap = Integer.parseInt(checkInDate.substring(0,4)) - Integer.parseInt(now.substring(0,4));
        int monthGap = Integer.parseInt(checkInDate.substring(5,7)) - Integer.parseInt(now.substring(5,7));
        int dayGap = Integer.parseInt(checkInDate.substring(8,10)) - Integer.parseInt(now.substring(8,10));
        return dayGap + 30*monthGap + 365*yearGap;
    }
}
