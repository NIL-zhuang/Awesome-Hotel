package com.example.hotel.bl.order;

import com.example.hotel.po.Order;
import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

/**
 * @author chenyizong
 * @date 2020-03-04
 */
public interface OrderService {

    /**
     * 预订酒店
     */
    ResponseVO addOrder(OrderVO orderVO);

    /**
     * 获得所有订单信息
     */
    List<Order> getAllOrders();

    /**
     * 查看酒店的所有订单
     */
    List<Order> getHotelOrders(Integer hotelId);

    /**
     * 获得指定用户的所有订单信息
     */
    List<Order> getUserOrders(int userId);

    /**
     * 撤销订单
     */
    ResponseVO annulOrder(int orderId);

    ResponseVO checkIn(int orderId);

    List<Order> probableAbnormalOrder(Integer hotelId);

    ResponseVO abnormalOrder(int orderId, double minCreditRatio);

    ResponseVO finishOrder(int orderId);

    CommentVO getComment(int orderId);

    ResponseVO annulComment(int orderId);

    // 根据酒店Id获取所有对应的评价
    List<CommentVO> getHotelComment(int hotelId);

    ResponseVO addComment(CommentVO commentVO);

    ResponseVO getUserComments(Integer userId);

    List<Order> getOrdersInMonth(List<Order> orders);  //从输入流的订单中找到近30天的并返回

    ResponseVO getOrdersInMonthOfHotel(Integer hotelId);

    ResponseVO getOrdersInMonthOfAll();

    List<Order> filterOrders(List<Order> orders,String beginTime, String endTime);  //找到与入住及退房时间有关联的订单


}
