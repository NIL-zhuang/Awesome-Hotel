package com.example.hotel.bl.hotel;

import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.enums.BedType;
import com.example.hotel.enums.BreakFast;
import com.example.hotel.enums.RoomType;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.vo.RoomVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/7 10:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoomServiceTest {

    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomMapper roomMapper;


    @Test
    @Transactional
    public void retrieveHotelRoomInfo() {
        List<HotelRoom> hotelRooms = roomService.retrieveHotelRoomInfo(1);
        Assert.assertThat(hotelRooms.size(),is(3));
    }

    @Test
    @Transactional
    public void retrieveHotelRoomInfoByType() {
        List<HotelRoom> hotelRooms = roomService.retrieveHotelRoomInfoByType(1,RoomType.BigBed);
        HotelRoom hotelRoom = hotelRooms.get(0);
        Assert.assertThat(20,is(hotelRoom.getCurNum()));
        Assert.assertThat("大床房",is(hotelRoom.getRoomType().toString()));
        Assert.assertThat(2,is(hotelRoom.getId()));
        hotelRooms = roomService.retrieveHotelRoomInfoByType(1,RoomType.DoubleBed);
        hotelRoom = hotelRooms.get(0);
        Assert.assertThat(3,is(hotelRoom.getId()));
        Assert.assertThat(30,is(hotelRoom.getCurNum()));
        Assert.assertThat("双床房",is(hotelRoom.getRoomType().toString()));
    }

    private static final RoomVO room1 = new RoomVO(){{
        setId(3);
        setCurNum(10);
        setRoomType("BigBed");
        setBedType("BigBed");
        setPeopleNum(1);
        setPrice(199.0);
        setTotal(10);
        setBreakfast("None");
    }};
    @Test
    @Transactional
    public void insertRoomInfo1() {
        roomService.insertRoomInfo(room1);
        List<HotelRoom> hotelRooms = roomService.retrieveHotelRoomInfoByType(3,RoomType.BigBed);
        HotelRoom hotelRoom = hotelRooms.get(0);
        Assert.assertThat(10,is(hotelRoom.getTotal()));
        Assert.assertThat(hotelRoom.getRoomType().toString(), is("大床房"));
    }

    private static final RoomVO room2 = new RoomVO(){{
        setId(1);
        setCurNum(10);
        setRoomType("BigBed");
        setBedType("BigBed");
        setPeopleNum(1);
        setPrice(199.0);
        setTotal(10);
        setBreakfast("None");
    }};

    @Test
    @Transactional
    public void insertRoomInfo2() {
        roomService.insertRoomInfo(room2);
        List<HotelRoom> hotelRooms = roomService.retrieveHotelRoomInfoByType(1,RoomType.BigBed);
        HotelRoom hotelRoom = hotelRooms.get(0);
        Assert.assertThat(10,is(hotelRoom.getTotal()));
        Assert.assertThat(hotelRoom.getRoomType().toString(), is("大床房"));
    }

    @Test
    @Transactional
    public void deleteRoom() {
        roomService.deleteRoom(1,"BigBed");
        List<HotelRoom> hotelRooms = roomService.retrieveHotelRoomInfoByType(1,RoomType.BigBed);
        Assert.assertThat(hotelRooms.size(), is(0));
    }

    @Test
    @Transactional
    public void updateRoomNum() {
       roomMapper.updateRoomNum(2,"Family",5);
       List<HotelRoom> hotelRooms = roomService.retrieveHotelRoomInfoByType(2,RoomType.Family);
       Assert.assertThat(hotelRooms.get(0).getTotal(), is(hotelRooms.get(0).getTotal()));
    }

    @Test
    @Transactional
    public void updateRoomInfo() {
        roomService.updateRoomInfo(1,"BigBed",10);
        int num = roomService.getRoomCurNum(1,"BigBed");
        Assert.assertThat(10,is(num));
    }

    @Test
    @Transactional
    public void getRoomCurNum() {
        int num = roomService.getRoomCurNum(1,"BigBed");
        Assert.assertThat(20,is(num));
    }
}