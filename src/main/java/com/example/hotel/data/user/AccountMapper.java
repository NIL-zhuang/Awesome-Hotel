package com.example.hotel.data.user;

import com.example.hotel.enums.VIPType;
import com.example.hotel.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface AccountMapper {

    /**
     * 创建一个新的账号
     */
    void createNewAccount(User user);

    /**
     * 根据用户名查找账号
     */
    User getAccountByEmail(@Param("email") String email);

    User getAccountById(@Param("id") int id);

    /**
     * 更新用户信息
     */
    void updateAccount(@Param("id") int id, @Param("password") String password, @Param("userName") String username, @Param("phoneNumber") String phonenumber);

    void updateCredit(@Param("id") int id, @Param("credit") double credit);

    void updateBirthday(@Param("id") int id, @Param("birthday") String birthday);

    void updateVIPType(@Param("id") int id, @Param("vipType")VIPType vipType);

    void updateCorporate(@Param("id") int id, @Param("corporate") String corporate);

    void updateAnnulTime(@Param("id") int id, @Param("time") int time);

    void updatePortrait(@Param("id") Integer id, @Param("url") String url);

    List<User> getAllUsers();
}
