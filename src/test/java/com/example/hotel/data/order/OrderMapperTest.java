package com.example.hotel.data.order;

import com.example.hotel.po.Comment;
import com.example.hotel.po.Order;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/7 10:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMapperTest {

    @Autowired
    private OrderMapper orderMapper;

    private final Order order = new Order() {{
        setOrderState("未入住");
        setCheckInDate("2020-06-01");
        setCheckOutDate("2020-06-02");
        setClientName("clearlove7");
        setCreateDate("2020-06-01");
        setHotelId(2);
        setHotelName("野区养猪场");
        setHaveChild(true);
        setHotelPhoneNum("123456");
        setPeopleNum(7);
        setPrice(777.0);
        setUserId(77);
        setRoomNum(2);
        setId(1);
    }};

    private final Comment comment = new Comment() {{
        setOrderId(1);
        setUserId(1);
        setComment("对面打野一直进我野区");
        setEnvironment(3);
        setEnvironment(3);
        setEquipment(3);
        setPoints(3);
        setSanitation(3);
        setService(3);
    }};

    @Test
    @Transactional
    public void addOrder() {
        orderMapper.addOrder(order);
        Assert.assertThat(orderMapper.getUserOrders(77).get(0).getHotelName(), is("野区养猪场"));
    }

    @Test
    @Transactional
    public void getAllOrders() {
        List<Order> orderList = orderMapper.getAllOrders();
        Assert.assertThat(orderList.size(), is(1));
        Assert.assertThat(orderMapper.getOrderById(1).getPrice(), is(100.0));
    }

    @Test
    @Transactional
    public void getUserOrders() {
        List<Order> orderList = orderMapper.getUserOrders(1);
        Assert.assertThat(orderList.size(), is(1));
        Assert.assertThat(orderMapper.getOrderById(1).getPrice(), is(100.0));
    }

    @Test
    @Transactional
    public void annulOrder() {
        orderMapper.annulOrder(1);
        Assert.assertThat(orderMapper.getOrderById(1).getOrderState(), is("已撤销"));
    }

    @Test
    @Transactional
    public void finishOrder() {
        orderMapper.finishOrder(1);
        Assert.assertThat(orderMapper.getOrderById(1).getOrderState(), is("已完成"));
    }

    @Test
    @Transactional
    public void getOrderById() {
        Assert.assertThat(orderMapper.getOrderById(1).getPrice(), is(100.0));
    }

    @Test
    @Transactional
    public void updateComment() {
        orderMapper.updateComment(comment);
        Assert.assertThat(orderMapper.getComment(1).getComment(), is("对面打野一直进我野区"));
    }

    @Test
    @Transactional
    public void getComment() {
        Comment comment = orderMapper.getComment(1);
        Assert.assertThat(comment.getComment(), is("你的野区我养猪"));
    }

    @Test
    @Transactional
    public void getAllComment() {
        List<Comment> comments = orderMapper.getAllComment();
        Assert.assertThat(comments.size(), is(1));
        Assert.assertThat(comments.get(0).getComment(), is("你的野区我养猪"));
    }

    @Test
    @Transactional
    public void getUserComment() {
        List<Comment> comments = orderMapper.getUserComment(1);
        Assert.assertThat(comments.size(), is(1));
        Assert.assertThat(comments.get(0).getComment(), is("你的野区我养猪"));
    }

    @Test
    @Transactional
    public void getHotelComment() {
        List<Comment> comments = orderMapper.getHotelComment(1);
        Assert.assertThat(comments.size(), is(1));
        Assert.assertThat(comments.get(0).getComment(), is("你的野区我养猪"));
    }

    @Test
    @Transactional
    public void annulComment() {
        orderMapper.annulComment(1);
        Comment comment = orderMapper.getComment(1);
        Assert.assertNull(comment.getComment());
    }
}