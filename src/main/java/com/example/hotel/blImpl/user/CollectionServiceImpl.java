package com.example.hotel.blImpl.user;

import com.example.hotel.bl.user.CollectionService;
import com.example.hotel.data.collection.CollectionMapper;
import com.example.hotel.po.Collection;
import com.example.hotel.vo.CollectionVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/15 21:52
 */
@Service
public class CollectionServiceImpl implements CollectionService {

    private static final String ADD_COLLECTION_SUCCESS = "收藏成功";
    private static final String ADD_COLLECTION_FAIL = "收藏失败";
    private static final String ANNUL_SUCCESS = "撤销成功";
    private static final String ANNUL_FAIL = "撤销失败";
    @Autowired
    private CollectionMapper collectionMapper;

    @Override
    public ResponseVO addCollection(CollectionVO collectionVO) {
        try {
            Collection collection = new Collection();
            BeanUtils.copyProperties(collectionVO, collection);
            collectionMapper.addCollection(collection);
            return ResponseVO.buildSuccess(ADD_COLLECTION_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(ADD_COLLECTION_FAIL);
        }
    }

    @Override
    public ResponseVO annulCollection(Integer collectionId) {
        try {
            collectionMapper.annualCollection(collectionId);
            return ResponseVO.buildSuccess(ANNUL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(ANNUL_FAIL);
        }
    }

    @Override
    public List<CollectionVO> getUserCollection(Integer userId) {
        List<Collection> collections = collectionMapper.getUserCollection(userId);
        return getCollectionVOS(collections);
    }

    private List<CollectionVO> getCollectionVOS(List<Collection> collections) {
        List<CollectionVO> collectionVOS = new ArrayList<>();
        for (Collection collection : collections) {
            CollectionVO collectionVO = new CollectionVO();
            BeanUtils.copyProperties(collection, collectionVO);
            collectionVOS.add(collectionVO);
        }
        return collectionVOS;
    }

    @Override
    public Integer getHotelCollection(Integer hotelId) {
        List<Collection> collections = collectionMapper.getHotelCollection(hotelId);
        return getCollectionVOS(collections).size();
    }

    @Override
    public Boolean userCollection(Integer userId, Integer hotelId) {
        List<Collection> collections = collectionMapper.getUserCollection(userId);
        for (Collection collection : collections) {
            if (collection.getHotelID().equals(hotelId)) {
                return true;
            }
        }
        return false;
    }
}
