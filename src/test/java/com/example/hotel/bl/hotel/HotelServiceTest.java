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
 * @date 2020/6/7 10:23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HotelServiceTest {

    @Autowired
    private HotelService hotelService;

    @Test
    @Transactional
    public void addHotel() {
    }

    @Test
    @Transactional
    public void deleteHotel() {
    }

    @Test
    @Transactional
    public void updateRoomInfo() {
    }

    @Test
    @Transactional
    public void retrieveHotels() {
    }

    @Test
    @Transactional
    public void retrieveHotelDetails() {
    }

    @Test
    @Transactional
    public void getRoomCurNum() {
    }

    @Test
    @Transactional
    public void retrieveAvailableHotelDetails() {
    }

    @Test
    @Transactional
    public void checkRoom() {
    }

    @Test
    @Transactional
    public void addLike() {
    }

    @Test
    @Transactional
    public void removeLike() {
    }

    @Test
    @Transactional
    public void getLike() {
    }

    @Test
    @Transactional
    public void addCollection() {
    }

    @Test
    @Transactional
    public void getCollections() {
    }

    @Test
    @Transactional
    public void addQuestion() {
    }

    @Test
    @Transactional
    public void annualQuestion() {
    }

    @Test
    @Transactional
    public void updateHotelPicture() {
    }
}