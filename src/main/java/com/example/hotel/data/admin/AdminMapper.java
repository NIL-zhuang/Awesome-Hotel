package com.example.hotel.data.admin;

import com.example.hotel.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Mapper
@Repository
public interface AdminMapper {

    int addManager(User user);

    int addSalesPerson(User user);

    List<User> getAllManagers();

    List<User> getHotelManagers(@Param("hotelId") Integer hotelId);

    List<User> getAllSalesPerson();

    int deleteManager(Integer id);

    int deleteSalesPerson(Integer id);
}
