package com.example.hotel.bl.order;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.po.Order;
import com.example.hotel.vo.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/7 10:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private HotelService hotelService;


    @Test
    @Transactional
    public void addOrder1() {
        OrderVO orderVO1 = new OrderVO() {{
            setCheckInDate("2020-07-01");
            setCheckOutDate("2020-07-05");
            setCreateDate("2020-06-17");
            setHotelId(1);
            setOrderState("未入住");
            setPrice(399.0);
            setRoomNum(1);
            setUserId(5);
            setHaveChild(false);
            setHotelName("汉庭酒店");
            setHotelPhoneNum("123456777");
            setPeopleNum(2);
            setRoomType("BigBed");
            setId(4);
        }};
        ResponseVO responseVO = orderService.addOrder(orderVO1);
    }

    @Test
    @Transactional
    public void addOrder2() {
        OrderVO orderVO2 = new OrderVO() {{
            setCheckInDate("2020-06-05");
            setCheckOutDate("2020-07-01");
            setCreateDate("2020-06-17");
            setHotelId(1);
            setOrderState("未入住");
            setPrice(399.0);
            setRoomNum(1);
            setUserId(4);
            setHaveChild(false);
            setHotelName("汉庭酒店");
            setHotelPhoneNum("123456777");
            setPeopleNum(2);
            setRoomType("BigBed");
            setId(4);
        }};
        ResponseVO responseVO = orderService.addOrder(orderVO2);
        Assert.assertThat(responseVO.getMessage(), is("预订时间错误"));
    }

    @Test
    @Transactional
    public void addOrder3() {
        OrderVO orderVO2 = new OrderVO() {{
            setCheckInDate("2020-06-29");
            setCheckOutDate("2020-07-01");
            setCreateDate("2020-06-17");
            setHotelId(1);
            setOrderState("未入住");
            setPrice(399.0);
            setRoomNum(1);
            setUserId(8);
            setHaveChild(false);
            setHotelName("汉庭酒店");
            setHotelPhoneNum("123456777");
            setPeopleNum(2);
            setRoomType("大床房");
            setId(4);
        }};
        ResponseVO responseVO = orderService.addOrder(orderVO2);
        Assert.assertThat(responseVO.getMessage(), is("信用值过低，无法预订酒店"));
    }

    @Test
    @Transactional
    public void getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        int num = orders.size();
        addOrder1();
        orders = orderService.getAllOrders();
        num = orders.size() - num;
        Assert.assertThat(num, is(1));
    }

    @Test
    @Transactional
    public void getHotelOrders() {
        List<Order> orders = orderService.getHotelOrders(1);
        int num = orders.size();
        addOrder1();
        orders = orderService.getHotelOrders(1);
        num = orders.size() - num;
        Assert.assertThat(num, is(1));
    }

    @Test
    @Transactional
    public void getUserOrders() {
        List<Order> orders = orderService.getUserOrders(1);
        Assert.assertThat(orders.size(), is(1));
    }

    @Test
    @Transactional
    public void annulOrder() {
        ResponseVO responseVO = orderService.annulOrder(1);
    }

    @Test
    @Transactional
    public void checkIn() {
        ResponseVO responseVO = orderService.checkIn(1);
        Assert.assertThat(responseVO.getContent().toString(), is("办理入住成功"));
    }

    @Test
    @Transactional
    public void probableAbnormalOrder() {
        List<Order> orders = orderService.probableAbnormalOrder(1);
        Assert.assertThat(orders.size(), is(1));
    }

    @Test
    @Transactional
    public void abnormalOrder() {
        ResponseVO responseVO = orderService.abnormalOrder(1);
    }

    @Test
    @Transactional
    public void getComment() {
        CommentVO commentVO = orderService.getComment(1);
        Assert.assertThat(commentVO.getComment(), is("你的野区我养猪"));
    }

    @Test
    @Transactional
    public void annulComment() {
        ResponseVO responseVO = orderService.annulComment(1);
        Assert.assertThat(responseVO.getContent().toString(), is("1"));
    }

    @Test
    @Transactional
    public void getHotelComment() {
        List<CommentVO> commentVOS = orderService.getHotelComment(3);
        Assert.assertThat(commentVOS.size(), is(0));
    }

    @Test
    @Transactional
    public void addComment() {
        CommentVO commentVO = new CommentVO() {{
            setUserId(1);
            setOrderId(1);
            setComment("Just so so ~ ");
            setEnvironment(4);
            setEquipment(4);
            setPoints(5);
            setSanitation(5);
            setService(4);
        }};
        orderService.checkIn(1);
        orderService.addComment(commentVO);
        CommentVO comment = orderService.getComment(1);
        Assert.assertThat(comment.getComment(), is("Just so so ~ "));
    }

    @Test
    @Transactional
    public void getUserComments() {
        ResponseVO responseVO = orderService.getUserComments(1);
        Assert.assertThat(((List<CommentVO>) responseVO.getContent()).size(), is(1));
    }

    @Test
    @Transactional
    public void getOrdersInMonthOfHotel() {
        List<List<Order>> summary = orderService.getOrdersInMonthOfHotel(1);
        int num = summary.size();
        Assert.assertThat(num, is(31));
        //测试内部，由于结果随时间改变，故测试完成后注释掉
        //Assert.assertThat(summary.get(19).size(), is(0));
    }

    @Test
    @Transactional
    public void getOrdersInMonthOfAll() {
        List<List<Order>> summary = orderService.getOrdersInMonthOfAll();
        int num = summary.size();
        Assert.assertThat(num, is(31));
        //测试内部，由于结果随时间改变，故测试完成后注释掉
        //Assert.assertThat(summary.get(19).size(), is(0));
    }

    @Test
    @Transactional
    public void filterOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<Order> ordersOf1 = orderService.getHotelOrders(1);
        String beginTime = "2020-06-01";
        String endTime = "2020-06-03";
        orders = orderService.filterOrders(orders, beginTime, endTime);
        ordersOf1 = orderService.filterOrders(ordersOf1, beginTime, endTime);
        Assert.assertThat(1, is(orders.size()));
        Assert.assertThat(1, is(ordersOf1.size()));
    }

    @Test
    @Transactional
    public void getOrderableRoom() {
        HotelVO hotel = orderService.getOrderableRoom(1, "2020-06-01", "2020-06-03");
        int num = hotel.getId();
        List<RoomVO> rooms = hotel.getRooms();
        Assert.assertThat(num, is(1));
        Assert.assertThat(rooms.get(0).getCurNum(), is(19));
    }

    @Test
    @Transactional
    public void checkRoomByOrder() {
        HotelVO hotelVO = hotelService.retrieveHotelDetails(1);
        List<RoomVO> roomVOS = hotelVO.getRooms();
        List<Order> orders = orderService.getHotelOrders(1);
        orders = orderService.filterOrders(orders, "2020-06-01", "2020-06-02");
        roomVOS = orderService.checkRoomByOrder(roomVOS, orders);
        RoomVO roomVO = roomVOS.get(0);
        Assert.assertThat(roomVO.getCurNum(), is(19));
    }

    @Test
    @Transactional
    public void getRoomCurNumByOrder() {
        int curNum = orderService.getRoomCurNumByOrder(1, "2020-06-01", "2020-06-02", "BigBed");
        Assert.assertThat(curNum, is(19));
    }
}