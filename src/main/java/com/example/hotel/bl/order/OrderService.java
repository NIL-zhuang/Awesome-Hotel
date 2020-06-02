package com.example.hotel.bl.order;

import com.example.hotel.po.Order;
import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
public interface OrderService {

    /**
     * 预订酒店
     *
     * @param orderVO
     * @return
     */
    ResponseVO addOrder(OrderVO orderVO);

    /**
     * 获得所有订单信息
     *
     * @return
     */
    List<Order> getAllOrders();

    /**
     * 查看酒店的所有订单
     *
     * @param hotelId
     * @return
     */
    List<Order> getHotelOrders(Integer hotelId);

    /**
     * 获得指定用户的所有订单信息
     *
     * @param userid
     * @return
     */
    List<Order> getUserOrders(int userid);

    /**
     * 撤销订单
     *
     * @param orderid
     * @return
     */
    ResponseVO annulOrder(int orderid);

    ResponseVO finishOrder(int orderId);

    CommentVO getComment(int orderId);

    ResponseVO annulComment(int orderId);

    // 根据酒店Id获取所有对应的评价
    List<CommentVO> getHotelComment(int hotelId);

    ResponseVO addComment(CommentVO commentVO);

    ResponseVO getUserComments(Integer userId);
}
