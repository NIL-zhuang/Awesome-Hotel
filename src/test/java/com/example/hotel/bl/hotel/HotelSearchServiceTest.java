package com.example.hotel.bl.hotel;

import com.example.hotel.enums.HotelTag;
import com.example.hotel.po.Hotel;
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
        setHotelStar(new String[]{"三星级"});
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

    private final SearchBodyVO searchBodyVO3 = new SearchBodyVO(){{
        setCheckInDate("2020-06-01");
        setCheckOutDate("2020-06-03");
        setAddress("南京");
        setBizRegion("西单");
        setKeyWords(new String[]{"温泉","便宜","早餐"});
        setMaxPrice(100);
        setMinScore(3.0);
        setHotelStar(new String[]{"四星级"});
    }};

    private final SearchBodyVO searchBodyVO4_1 = new SearchBodyVO(){{
        setCheckInDate("2020-06-01");
        setCheckOutDate("2020-06-03");
        setAddress("南京");
        setBizRegion("西单");
        setKeyWords(new String[]{"温泉","便宜","早餐"});
        setMaxPrice(1000);
        setMinScore(5.0);
        setHotelStar(new String[]{"四星级"});
    }};

    private final SearchBodyVO searchBodyVO4_2 = new SearchBodyVO(){{
        setCheckInDate("2020-06-01");
        setCheckOutDate("2020-06-03");
        setAddress("南京");
        setBizRegion("西单");
        setKeyWords(new String[]{"温泉","便宜","早餐"});
        setMaxPrice(1000);
        setMinScore(4.7);
        setHotelStar(new String[]{"四星级"});
    }};

    private final SearchBodyVO searchBodyVO5 = new SearchBodyVO(){{
        setCheckInDate("2020-06-01");
        setCheckOutDate("2020-06-03");
        setAddress("南京");
        setBizRegion("西单");
        setKeyWords(new String[]{HotelTag.Breakfast.toString(), HotelTag.Cheap.toString()});
        setMaxPrice(1000);
        setMinScore(4.7);
        setHotelStar(new String[]{"四星级"});
    }};

    @Test
    @Transactional
    //测试星级
    public void searchHotel1() {
        List<HotelVO> hotelVOS = hotelSearchService.searchHotel(searchBodyVO1);
        int num = hotelVOS.size();
        Assert.assertThat(num, is(1));
    }

    @Test
    @Transactional
    //测试地址
    public void searchHotel2() {
        List<HotelVO> hotelVOS = hotelSearchService.searchHotel(searchBodyVO2);
        int num = hotelVOS.size();
        Assert.assertThat(num, is(0));
    }

    @Test
    @Transactional
    //测试价格
    public void searchHotel3() {
        List<HotelVO> hotelVOS = hotelSearchService.searchHotel(searchBodyVO3);
        int num = hotelVOS.size();
        Assert.assertThat(num, is(0));
    }

    @Test
    @Transactional
    //测试评分1
    public void searchHotel4_1() {
        List<HotelVO> hotelVOS = hotelSearchService.searchHotel(searchBodyVO4_1);
        int num = hotelVOS.size();
        Assert.assertThat(num, is(1));
    }

    @Test
    @Transactional
    //测试评分2
    public void searchHotel4_2() {
        List<HotelVO> hotelVOS = hotelSearchService.searchHotel(searchBodyVO4_2);
        int num = hotelVOS.size();
        Assert.assertThat(num,is(1));
    }

    @Test
    @Transactional
    //测试关键词
    public void searchHotel5() {
        List<HotelVO> hotelVOS = hotelSearchService.searchHotel(searchBodyVO5);
        int num = hotelVOS.size();
        Assert.assertThat(num,is(1));
        Assert.assertThat(hotelVOS.get(0).getName(),is("如家酒店"));
    }
}