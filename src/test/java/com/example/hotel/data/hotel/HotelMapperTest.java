package com.example.hotel.data.hotel;

import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;
import com.example.hotel.po.Hotel;
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
public class HotelMapperTest {

    @Autowired
    private HotelMapper hotelMapper;

    private final Hotel hotel = new Hotel() {{
        setHotelName("test hotel");
        setAddress("my home");
        setBizRegion(BizRegion.XiDan);
        setHotelStar(HotelStar.Five);
        setDescription("test");
        setPhoneNum("110");
        setPoints(5);
    }};

    @Test
    @Transactional
    public void insertHotel() {
        int val = hotelMapper.insertHotel(hotel);
        Assert.assertThat(val, is(1));
    }

    @Test
    @Transactional
    public void deleteHotel() {
        hotelMapper.deleteHotel(1);
        Assert.assertThat(hotelMapper.selectAllHotel().size(), is(2));
    }

    @Test
    @Transactional
    public void selectAllHotel() {
        Assert.assertThat(hotelMapper.selectAllHotel().size(), is(3));
    }

    @Test
    @Transactional
    public void selectById() {
        Assert.assertThat(hotelMapper.selectById(1).getHotelName(), is("汉庭酒店"));
    }

    @Test
    @Transactional
    public void updatePicture() {
        int val = hotelMapper.updatePicture(1, "www.bilibili.com");
        Assert.assertThat(val, is(1));
    }

    @Test
    @Transactional
    public void updateHotelPoints() {
        int val = hotelMapper.updateHotelPoints(1, 1, 1.0, 1.0, 1.0, 1.0, 1.0);
        Assert.assertThat(hotelMapper.selectById(1).getPoints(), is(1.0));
    }
}