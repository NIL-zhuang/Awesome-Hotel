package com.example.hotel;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.enums.RoomType;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/25 15:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FunctionTest {

    @Autowired
    private AdminService adminService;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;

    @Test
    @Transactional
    public void AdminTest() {
        final String manager_email = "manager_email@qq.com";
        final String sales_email = "sales_email@qq.com";
        final String manager = "manager";
        final String sales = "sales";
        final String phone = "12312312312";
        final String password = "password";
        UserForm managerForm = new UserForm() {{
            setEmail(manager_email);
            setPassword(password);
            setPhoneNumber(phone);
            setUserName(manager);
            setHotelId(9);
        }};
        UserForm salesForm = new UserForm() {{
            setEmail(sales_email);
            setPassword(password);
            setPhoneNumber(phone);
            setUserName(sales);
        }};
        adminService.addManager(managerForm);
        List<User> managerList = adminService.getAllManagers();
        assertEquals(managerList.size(), 2);
        adminService.addSalesPerson(salesForm);
        List<User> salesList = adminService.getAllSalesPerson();
        assertEquals(salesList.size(), 2);
        adminService.deleteManager(managerList.get(1).getId());
        assertEquals(adminService.getAllManagers().size(), 1);
        adminService.deleteSalesPerson(salesList.get(1).getId());
        assertEquals(adminService.getAllSalesPerson().size(), 1);
    }

    @Test
    @Transactional
    public void HotelTest() throws ServiceException {
        HotelForm hotelForm = new HotelForm() {{
            setAddress("nanjing");
            setDescription("某会议中心");
            setName("小破南的会议中心");
            setBizRegion("XiDan");
            setPhoneNum("12312312312");
            setHotelStar("Five");
            setPhoneNum("12312312312");
        }};
        RoomVO roomVO = new RoomVO() {{
            setCurNum(10);
            setTotal(10);
            setRoomType("BigBed");
            setBedType("BigBed");
            setBreakfast("None");
            setPeopleNum(7);
            setPrice(120.0);
        }};
        CommentVO commentVO = new CommentVO() {{
            setUserId(1);
            setService(3);
            setSanitation(3);
            setPoints(3);
            setEquipment(3);
            setEnvironment(3);
            setComment("好烦啊不想测了");
        }};
        CommentVO commentVO1 = new CommentVO() {{
            setService(5);
            setEnvironment(5);
            setPoints(5);
            setSanitation(5);
            setEquipment(5);
            setUserId(3);
            setComment("烦死了吐了");
        }};
        hotelService.addHotel(hotelForm);
        List<HotelVO> hotelVOS = hotelService.retrieveHotels();
        assertEquals(hotelVOS.size(), 4);
        HotelVO NJUHotel = new HotelVO();
        for (HotelVO hotelVO : hotelVOS) {
            if (hotelVO.getName().equals("小破南的会议中心")) {
                NJUHotel = hotelVO;
                break;
            }
        }
        int hotelId = NJUHotel.getId();
        roomVO.setId(hotelId);
        hotelForm.setAddress("东南大学");
        hotelService.updateHotelInfo(hotelId, hotelForm);
        assertEquals(hotelService.retrieveHotelDetails(hotelId).getAddress(), "东南大学");
        roomService.insertRoomInfo(roomVO);
        assertEquals(roomService.retrieveHotelRoomInfo(hotelId).size(), 1);
        assertEquals(roomService.retrieveHotelRoomInfoByType(hotelId, RoomType.BigBed).size(), 1);
        hotelService.updateRoomInfo(hotelId, "BigBed", 5);
        assertEquals(roomService.retrieveHotelRoomInfo(hotelId).get(0).getCurNum(), 5);
        roomService.deleteRoom(hotelId, "BigBed");
        assertEquals(roomService.retrieveHotelRoomInfo(hotelId).size(), 0);
        hotelService.addComment(commentVO, hotelId);
        assertThat(hotelService.retrieveHotelDetails(hotelId).getRate(), is(3.0));
        hotelService.addComment(commentVO1, hotelId);
        assertThat(hotelService.retrieveHotelDetails(hotelId).getRate(), is(4.0));
        hotelService.deleteHotel(hotelId);
        assertEquals(hotelService.retrieveHotels().size(), 3);
    }

    @Test
    @Transactional
    public void OrderTest() {
        final OrderVO orderVO = new OrderVO() {{
            setCheckInDate("2020-07-01");
            setCheckOutDate("2020-08-01");
            setCreateDate("2020-01-01");
            setHotelId(1);
            setOrderState("未入住");
            setPrice(100.0);
            setRoomNum(1);
            setUserId(4);
            setHaveChild(false);
            setHotelName("汉庭酒店");
            setPeopleNum(2);
            setHotelPhoneNum("12312312312");
            setRoomType("DoubleBed");
            setClientName("jacky");
            setPhoneNumber("77777777777");
        }};
        orderService.addOrder(orderVO);
        assertEquals(orderService.getAllOrders().size(), 2);
        assertEquals(orderService.getHotelOrders(1).size(), 2);
        Order order = orderService.getUserOrders(4).get(0);
        assertNotNull(order);
        orderService.abnormalOrder(order.getId());
        // 因为不是异常订单
        assertEquals(orderService.getUserOrders(4).get(0).getOrderState(), "未入住");
        orderMapper.abnormalOrder(order.getId());
        assertEquals(orderService.getUserOrders(4).get(0).getOrderState(), "异常订单");
        orderService.argueAbnormalOrder(order.getId(), "申诉");
        assertEquals(orderService.getUserOrders(4).get(0).getComments(), "申诉");
        orderService.handleAbnormal(order.getId(), 1.0);
        assertEquals(orderService.getUserOrders(4).get(0).getOrderState(), "已完成");

        final CommentVO commentVO = new CommentVO() {{
            setComment("123");
            setEquipment(5);
            setSanitation(5);
            setPoints(5);
            setEnvironment(5);
            setService(5);
            setOrderId(order.getId());
        }};
        orderService.addComment(commentVO);
        assertThat(orderService.getUserOrders(4).get(0).getPoints(), is(5));
        assertEquals(orderService.getUserOrders(4).get(0).getComments(), "123");
        orderService.annulComment(order.getId());
        assertNull(orderService.getUserOrders(4).get(0).getComments());
    }
}
