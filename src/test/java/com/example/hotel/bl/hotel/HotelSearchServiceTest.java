package com.example.hotel.bl.hotel;

import com.example.hotel.bl.order.OrderService;
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

    @Autowired
    private OrderService orderService;

    private final SearchBodyVO searchBodyVO = new SearchBodyVO(){{
        setCheckOutDate("2020-07-11");
        setCheckOutDate("2020-07-13");
        setAddress("南京");
        setBizRegion("西单");
        setKeyWords(new String[]{"温泉","便宜","早餐"});
        setMaxPrice(1000);
        setMinScore(3.0);
        setHotelStar(new String[]{"三星级","四星级"});
    }};

    @Test
    @Transactional
    public void searchHotel() {
        //测试星级
        List<HotelVO> hotelVOS = hotelSearchService.searchHotel(searchBodyVO);
        int num = hotelVOS.size();
        Assert.assertThat(num, is(2));
    }
}