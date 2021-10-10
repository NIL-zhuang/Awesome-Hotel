package com.example.hotel.controller.order;

import com.example.hotel.bl.order.OrderService;
import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.apache.coyote.Response;
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

    @PostMapping("/{orderId}/checkIn")
    public ResponseVO checkIn(@PathVariable Integer orderId){
        return orderService.checkIn(orderId);
    }

    @PostMapping("/{orderId}/finishOrder")
    public ResponseVO finishOrder(@PathVariable Integer orderId) {
        return orderService.finishOrder(orderId);
    }

    @PostMapping("/{orderId}/abnormalOrder")
    public ResponseVO abnormalOrder(@PathVariable Integer orderId) {
        return orderService.abnormalOrder(orderId);
    }

    @PostMapping("/{orderId}/handleAbnormal")
    public ResponseVO handleAbnormal(@PathVariable Integer orderId,@RequestParam double ratio){
        return orderService.handleAbnormal(orderId, ratio);
    }

    @GetMapping("/{hotelId}/probableAbnormalOrder")
    public ResponseVO probableAbnormalOrder(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(orderService.probableAbnormalOrder(hotelId));
    }

    @GetMapping("/{hotelId}/getOrdersInMonthOfHotel")
    public ResponseVO getOrdersInMonthOfHotel(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(orderService.getOrdersInMonthOfHotel(hotelId));
    }

    @GetMapping("/getOrdersInMonthOfAll")
    public ResponseVO getOrdersInMonthOfAll() {
        return ResponseVO.buildSuccess(orderService.getOrdersInMonthOfAll());
    }

    @GetMapping("/getHotelRoom")
    public ResponseVO getOrderableRoom(@RequestParam(value = "hotelId") Integer hotelId,
                                       @RequestParam(value = "startTime") String startTime,
                                       @RequestParam(value = "endTime") String endTime) {
        return ResponseVO.buildSuccess(orderService.getOrderableRoom(hotelId, startTime, endTime));
    }

    @PostMapping("/{orderId}/argueAbnormalOrder")
    public ResponseVO argueAbnormalOrder(@PathVariable Integer orderId, @RequestParam String reason) {
        return orderService.argueAbnormalOrder(orderId, reason);
    }
}
