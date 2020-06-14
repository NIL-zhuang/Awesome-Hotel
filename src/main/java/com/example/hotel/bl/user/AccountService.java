package com.example.hotel.bl.user;

import com.example.hotel.po.User;
import com.example.hotel.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huwen
 * @date 2019/3/23
 */
public interface AccountService {
    /**
     * 注册账号
     *
     * @return 登录成功 or 失败
     */
    ResponseVO registerAccount(UserVO userVO);

    /**
     * 用户登录，登录成功会将用户信息保存再session中
     */
    UserVO login(UserForm userForm);

    /**
     * 获取用户个人信息
     */
    UserVO getUserInfo(int id);

    /**
     * 获取用户个人信息
     */
    UserVO getUserInfoByEmail(String email);

    /**
     * 更新用户个人信息
     */
    ResponseVO updateUserInfo(int id, String password, String username, String phonenumber);

    //todo
    ResponseVO updateCredit(int id, double credit);

//    ResponseVO personalVIP(int id, String birthday);
//
//    ResponseVO corporateVIP(int id, String corporate);

//    ResponseVO normalUser(String corporate);

    void updateBirthday(int id, String birthday); // 注册会员时使用

    void registerAsVIP(int id); // 同上

    void freezeVIP(int id); // 同上

    ResponseVO updatePortrait(int userId, String url);

    List<String> getManagerTelephone(int hotelId);

    List<UserVO> getAllUsers();
}
