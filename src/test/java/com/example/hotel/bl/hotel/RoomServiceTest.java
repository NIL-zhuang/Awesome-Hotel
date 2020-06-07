package com.example.hotel.bl.hotel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/7 10:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @Test
    @Transactional
    public void retrieveHotelRoomInfo() {
    }

    @Test
    @Transactional
    public void retrieveHotelRoomInfoByType() {
    }

    @Test
    @Transactional
    public void insertRoomInfo() {
    }

    @Test
    @Transactional
    public void updateRoomInfo() {
    }

    @Test
    @Transactional
    public void getRoomCurNum() {
    }
}