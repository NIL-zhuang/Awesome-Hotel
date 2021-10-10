package com.example.hotel.data.order;

import com.example.hotel.po.Comment;
import com.example.hotel.po.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Mapper
@Repository
public interface OrderMapper {

    void addOrder(Order order);

    List<Order> getAllOrders();

    List<Order> getUserOrders(@Param("userid") int userid);

//    List<Order> getHotelOrders(@Param("hotelId") int hotelId); 并不依赖这个方法的实现

    void annulOrder(@Param("orderid") int orderid);

    int checkIn(@Param("orderId") int orderId);

    int finishOrder(@Param("orderId") int orderId);

    int abnormalOrder(@Param("orderId") int orderId);

    Order getOrderById(@Param("orderid") int orderid);

    int updateComment(Comment comment);

    Comment getComment(@Param("orderId") int orderId);

    List<Comment> getAllComment();

    List<Comment> getUserComment(@Param("userId") Integer userId);

    List<Comment> getHotelComment(@Param("hotelId") Integer hotelId);

    int annulComment(@Param("orderId") Integer orderId);

    int argueAbnormalOrder(@Param("orderId") Integer orderId, @Param("reason") String reason);
}
