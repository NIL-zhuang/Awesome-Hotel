package com.example.hotel.data.hotel;

import com.example.hotel.enums.BedType;
import com.example.hotel.enums.BreakFast;
import com.example.hotel.enums.RoomType;
import com.example.hotel.po.HotelRoom;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.*;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/7 10:38
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoomMapperTest {

    @Autowired
    private RoomMapper roomMapper;

    private final HotelRoom room = new HotelRoom() {{
        setRoomType(RoomType.DoubleBed);
        setBedType(BedType.BigBed);
        setBreakfast(BreakFast.Noodles);
        setCurNum(7);
        setHotelId(1);
        setPeopleNum(2);
        setPrice(100.0);
        setTotal(7);
    }};

    @Test
    @Transactional
    public void updateRoomInfo() {
        roomMapper.updateRoomInfo(1, "BigBed", 15);
        Assert.assertThat(roomMapper.getRoomCurNum(1, "BigBed"), is(5));
    }

    @Test
    @Transactional
    public void insertRoom() {
        int val = roomMapper.insertRoom(room);
        Assert.assertEquals(roomMapper.selectRoomsByHotelId(1).size(), 4);
        Assert.assertEquals(val, 1);
    }

    @Test
    public void selectRoomsByHotelId() {
        Assert.assertEquals(roomMapper.selectRoomsByHotelId(1).size(), 3);
    }

    @Test
    public void getRoomCurNum() {
        Assert.assertEquals(roomMapper.getRoomCurNum(1, "BigBed"), 20);
    }
}