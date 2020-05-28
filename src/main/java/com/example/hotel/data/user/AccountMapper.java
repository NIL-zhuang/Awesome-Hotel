package com.example.hotel.data.user;

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
     *
     * @return
     */
    int createNewAccount(User user);

    /**
     * 根据用户名查找账号
     *
     * @param email
     * @return
     */
    User getAccountByName(@Param("email") String email);

    User getAccountById(@Param("id") int id);

    /**
     * 更新用户信息
     *
     * @param id
     * @param password
     * @param username
     * @param phonenumber
     * @return
     */
    int updateAccount(@Param("id") int id, @Param("password") String password, @Param("userName") String username, @Param("phoneNumber") String phonenumber);

    int updateCredit(@Param("id") int id, @Param("credit") double credit);

    int updateBirthday(@Param("id") int id, @Param("birthday") String birthday);

    int updateCorporate(@Param("id") int id, @Param("corporate") String corporate);

    int annualCorporate(@Param("corporate") String corporate);

    int updateAnnualTime(@Param("id") int id, @Param("time") int time);

    int updatePortrait(@Param("id") Integer id, @Param("url") String url);

}
