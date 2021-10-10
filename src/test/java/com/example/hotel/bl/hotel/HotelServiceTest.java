package com.example.hotel.bl.hotel;

import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.HotelForm;
import com.example.hotel.vo.HotelVO;
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
 * @date 2020/6/7 10:23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HotelServiceTest {

    @Autowired
    private HotelService hotelService;


    @Test
    @Transactional
    public void addHotel() throws ServiceException {
        HotelForm hotelForm = new HotelForm(){{
           setDescription("123");
           setAddress("南京");
           setBizRegion("XiDan");
           setHotelStar("Three");
           setName("6667");
           setPhoneNum("123456789");
           setRate(4.1);
        }};
        hotelService.addHotel(hotelForm);
        List<HotelVO> hotelVOS = hotelService.retrieveHotels();
        int size = hotelVOS.size();
        HotelVO hotelVO = hotelVOS.get(size-1);
        Assert.assertThat(hotelVO.getName(),is("6667"));
    }

    @Test
    @Transactional
    public void updateHotelInfo() throws ServiceException {
        HotelForm hotelForm = new HotelForm(){{
           setName("汉庭酒店");
           setAddress("456");
           setDescription("789");
        }};
       hotelService.updateHotelInfo(1,hotelForm);
       HotelVO hotel = hotelService.retrieveHotelDetails(1);
       Assert.assertThat(hotel.getName(),is("汉庭酒店"));
       Assert.assertThat(hotel.getAddress(),is("456"));
       Assert.assertThat(hotel.getDescription(),is("789"));
    }

    @Test
    @Transactional
    public void deleteHotel() {
        hotelService.deleteHotel(2);
        HotelVO hotelVO = hotelService.retrieveHotelDetails(2);
        Assert.assertThat(null,is(hotelVO.getId()));
    }

    @Test
    @Transactional
    public void updateRoomInfo() {
        hotelService.updateRoomInfo(1, "DoubleBed",5);
        HotelVO hotelVO = hotelService.retrieveHotelDetails(1);
        List<RoomVO> roomVOS = hotelVO.getRooms();
        Assert.assertThat(roomVOS.get(1).getCurNum(),is(25));
    }

    @Test
    @Transactional
    public void retrieveHotels() {
        List<HotelVO> hotelVOS = hotelService.retrieveHotels();
        int num = hotelVOS.size();
        Assert.assertThat(num,is(39));
    }

    @Test
    @Transactional
    public void retrieveHotelDetails() {
        HotelVO hotelVO = hotelService.retrieveHotelDetails(1);
        List<RoomVO> rooms = hotelVO.getRooms();
        int num = rooms.size();
        int DoubleBed = rooms.get(1).getCurNum();
        Assert.assertThat(num,is(3));
        Assert.assertThat(DoubleBed,is(30));
    }

    @Test
    @Transactional
    public void getRoomCurNum() {
        int num = hotelService.getRoomCurNum(1,"DoubleBed");
        Assert.assertThat(num,is(30));
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