package com.example.hotel.controller.order;

import com.example.hotel.bl.order.OrderService;
import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: chenyizong
 * @Date: 2020-02-29
 */


@RestController()
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/addOrder")
    public ResponseVO reserveHotel(@RequestBody OrderVO orderVO) {
        return orderService.addOrder(orderVO);
    }

    @GetMapping("/getAllOrders")
    public ResponseVO retrieveAllOrders() {
        return ResponseVO.buildSuccess(orderService.getAllOrders());
    }

    @GetMapping("/{userId}/getUserOrders")
    public ResponseVO retrieveUserOrders(@PathVariable int userId) {
        return ResponseVO.buildSuccess(orderService.getUserOrders(userId));
    }

    @GetMapping("/{orderId}/annulOrder")
    public ResponseVO annulOrder(@PathVariable int orderId) {
        return orderService.annulOrder(orderId);
    }


    @GetMapping("/{hotelId}/allOrders")
    public ResponseVO retrieveHotelOrders(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(orderService.getHotelOrders(hotelId));
    }

    @PostMapping("/addComment")
    public ResponseVO addComment(@RequestBody CommentVO commentVO) {
        return orderService.addComment(commentVO);
    }

    @GetMapping("/{orderId}/annulComment")
    public ResponseVO annulComment(@PathVariable Integer orderId) {
        return ResponseVO.buildSuccess(orderService.annulComment(orderId));
    }

    @GetMapping("/{orderId}/getComment")
    public ResponseVO getComment(@PathVariable Integer orderId) {
        return ResponseVO.buildSuccess(orderService.getComment(orderId));
    }

    @GetMapping("/{hotelId}/getHotelComment")
    public ResponseVO getHotelComment(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(orderService.getHotelComment(hotelId));
    }

    @GetMapping("/{userId}/getUserComment")
    public ResponseVO getUserComment(@PathVariable Integer userId) {
        return ResponseVO.buildSuccess(orderService.getUserComments(userId));
    }

    @PostMapping("{orderId}/checkIn")
    public ResponseVO checkIn(@PathVariable Integer orderId) {
        return orderService.checkIn(orderId);
    }

    @PostMapping("/{orderId}/finishOrder")
    public ResponseVO finishOrder(@PathVariable Integer orderId) {
        return orderService.finishOrder(orderId);
    }

    @PostMapping("/{orderId}/abnormalOrder")
    public ResponseVO abnormalOrder(@PathVariable Integer orderId, @RequestParam double ratio) {
        return orderService.abnormalOrder(orderId, ratio);
    }

    @GetMapping("/{hotelId}/probableAbnormalOrder")
    public ResponseVO probableAbnormalOrder(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(orderService.probableAbnormalOrder(hotelId));
    }

    @GetMapping("/{hotelId}/getOrdersInMonthOfAll")
    public ResponseVO getOrdersInMonthOfHotel(@PathVariable Integer hotelId) {
        return orderService.getOrdersInMonthOfHotel(hotelId);
    }

    @GetMapping("/getOrdersInMonthOfAll")
    public ResponseVO getOrdersInMonthOfAll() {
        return orderService.getOrdersInMonthOfAll();
    }

}
