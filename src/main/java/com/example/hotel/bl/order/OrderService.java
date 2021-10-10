package com.example.hotel.bl.order;

import com.example.hotel.po.Order;
import com.example.hotel.vo.*;

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

    ResponseVO abnormalOrder(int orderId);

    ResponseVO handleAbnormal(int orderId, double ratio);

    ResponseVO finishOrder(int orderId);

    CommentVO getComment(int orderId);

    ResponseVO annulComment(int orderId);

    // 根据酒店Id获取所有对应的评价
    List<CommentVO> getHotelComment(int hotelId);

    ResponseVO addComment(CommentVO commentVO);

    ResponseVO getUserComments(Integer userId);

    List<List<Order>> getOrdersInMonth(List<Order> orders);  //从输入流的订单中找到近30天的并返回

    List<List<Order>> getOrdersInMonthOfHotel(Integer hotelId);

    List<List<Order>> getOrdersInMonthOfAll();

    List<Order> filterOrders(List<Order> orders,String beginTime, String endTime);  //找到与入住及退房时间有关联的订单

    HotelVO getOrderableRoom(Integer hotelId, String beginTime, String endTime);  //对特定的酒店，返回可预订的房间的信息

    List<RoomVO> checkRoomByOrder(List<RoomVO> rooms, List<Order> orders); //通过房间信息和对应的订单信息，检查酒店房间情况

    Integer getRoomCurNumByOrder(Integer hotelId, String beginTime, String endTime, String type); //通过订单查找酒店特定房间的可用房间数，用于addOrder时的检验

    ResponseVO argueAbnormalOrder(Integer orderId, String reason);
}
