package com.example.hotel.bl.hotel;

import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.SearchBodyVO;
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
public class HotelSearchServiceTest {

    @Autowired
    private HotelSearchService hotelSearchService;


    private final SearchBodyVO searchBodyVO1 = new SearchBodyVO(){{
        setCheckInDate("2020-07-11");
        setCheckOutDate("2020-07-13");
        setAddress("南京");
        setBizRegion("西单");
        setKeyWords(new String[]{"温泉","便宜","早餐"});
        setMaxPrice(1000);
        setMinScore(3.0);
        setHotelStar(new String[]{"五星级"});
    }};

    private final SearchBodyVO searchBodyVO2 = new SearchBodyVO(){{
        setCheckInDate("2020-06-01");
        setCheckOutDate("2020-06-03");
        setAddress("北京");
        setBizRegion("西单");
        setKeyWords(new String[]{"温泉","便宜","早餐"});
        setMaxPrice(1000);
        setMinScore(3.0);
        setHotelStar(new String[]{"四星级"});
    }};

    @Test
    @Transactional
    //测试星级
    public void searchHotel1() {
        List<HotelVO> hotelVOS = hotelSearchService.searchHotel(searchBodyVO1);
        int num = hotelVOS.size();
        Assert.assertThat(num, is(0));
    }

    @Test
    @Transactional
    public void searchHotel2() {
        List<HotelVO> hotelVOS = hotelSearchService.searchHotel(searchBodyVO2);
        int num = hotelVOS.size();
        Assert.assertThat(num, is(0));
    }
}