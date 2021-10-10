package com.example.hotel.data.collection;

import com.example.hotel.data.user.CollectionMapper;
import com.example.hotel.po.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/7 10:38
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CollectionMapperTest {

    @Autowired
    private CollectionMapper collectionMapper;

    private final Collection collection = new Collection() {{
        setHotelID(3);
        setUserID(5);
    }};

    @Test
    @Transactional
    public void addCollection() {
        int val = collectionMapper.addCollection(collection);
        Assert.assertEquals(val, 1);
        Assert.assertEquals(collectionMapper.getUserCollection(5).size(), 1);
    }

    @Test
    @Transactional
    public void annualCollection() {
        collectionMapper.addCollection(collection);
        collectionMapper.annualCollection(collectionMapper.getUserCollection(5).get(0).getId());
        Assert.assertEquals(collectionMapper.getUserCollection(5).size(), 0);
    }

    @Test
    @Transactional
    public void getUserCollection() {
//        pass
    }

    @Test
    @Transactional
    public void getHotelCollection() {
        collectionMapper.addCollection(collection);
        Assert.assertEquals(collectionMapper.getHotelCollection(3).size(), 1);
    }
}