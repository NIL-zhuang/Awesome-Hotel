package com.example.hotel.data.user;

import com.example.hotel.po.Credit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/5/24 17:24
 */
@Repository
@Mapper
public interface CreditMapper {

    int addCredit(Credit credit);

    List<Credit> getUserCredit(@Param("userId") Integer userId);

    List<Credit> getAllCredit();
}
