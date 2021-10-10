package com.example.hotel;

import com.example.hotel.bl.VIP.VIPService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.enums.UserType;
import com.example.hotel.enums.VIPType;
import com.example.hotel.po.Credit;
import com.example.hotel.po.Order;
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
 * @date 2020/6/25 15:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegrationTest {

    @Autowired
    private AccountService accountService;
    @Autowired
    private VIPService vipService;
    @Autowired
    private OrderService orderService;

    private OrderVO orderVO = new OrderVO() {{
        setCheckInDate("2020-06-26");
        setCheckOutDate("2020-06-27");
        setCreateDate("2020-06-21");
        setHotelId(1);
        setOrderState("未入住");
        setPrice(400.0);
        setRoomNum(1);
        setHaveChild(false);
        setHotelName("汉庭酒店");
        setHotelPhoneNum("12345678901");
        setPhoneNumber("12312312312");
        setPeopleNum(2);
        setRoomType("BigBed");
        setId(4);
    }};

    @Test
    @Transactional
    public void IntegrateTest() {
        // 注册用户并登录
        accountService.registerAccount(new UserVO() {{
            setEmail("123123@qq.com");
            setPassword("123123");
            setUserName("test");
            setCredit(100);
            setAnnulTime(3);
            setPhoneNumber("12312312312");
            setUserType(UserType.Client);
        }});
        UserVO userVO = accountService.login(new UserForm() {{
            setEmail("123123@qq.com");
            setPassword("123123");
        }});
        assertEquals(userVO.getUserName(), "test");
        // VIP
        vipService.registerAsClientVIP(userVO.getId(), "2000-01-01");
        assertEquals(accountService.getUserInfo(userVO.getId()).getVipType(), VIPType.Client);
        vipService.freezeClientVIP(userVO.getId());
        vipService.restoreClientVIP(userVO.getId());
        ClientVIPVO clientVIPVO = (ClientVIPVO) vipService.getVIPbyUserId(userVO.getId()).getContent();
        assertEquals(clientVIPVO.getStatus(), 1);
        // 疯狂下单撤单把annulTime耗完
        orderVO.setUserId(userVO.getId());
        orderService.addOrder(orderVO);
        Order order = orderService.getUserOrders(userVO.getId()).get(0);
        orderService.annulOrder(order.getId());
        assertThat(accountService.getUserInfo(userVO.getId()).getAnnulTime(), is(2));
        orderService.addOrder(orderVO);
        orderService.annulOrder(orderService.getUserOrders(userVO.getId()).get(1).getId());
        assertThat(accountService.getUserInfo(userVO.getId()).getAnnulTime(), is(1));
        orderService.addOrder(orderVO);
        orderService.annulOrder(orderService.getUserOrders(userVO.getId()).get(2).getId());
        assertThat(accountService.getUserInfo(userVO.getId()).getAnnulTime(), is(0));
        // 撤销订单和信誉积分
        orderService.addOrder(orderVO);
        orderService.annulOrder(orderService.getUserOrders(userVO.getId()).get(3).getId());
        assertThat(accountService.getUserInfo(userVO.getId()).getCredit(), is(-100.0));
        List<CreditVO> creditVOS = (List<CreditVO>) accountService.getUserCreditChanges(userVO.getId()).getContent();
        assertThat(creditVOS.get(3).getChange(),is(-200));
    }

}
