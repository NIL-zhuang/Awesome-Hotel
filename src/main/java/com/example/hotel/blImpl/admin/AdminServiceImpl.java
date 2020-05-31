package com.example.hotel.blImpl.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.blImpl.user.PasswordEncryptHelper;
import com.example.hotel.data.admin.AdminMapper;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.User;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Service
public class AdminServiceImpl implements AdminService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    private final static String ACCOUNT_NOTFOUND = "账号不存在";

    @Autowired
    AdminMapper adminMapper;

    @Override
    public ResponseVO addManager(UserForm userForm) {
        User user = new User();
        user.setEmail(userForm.getEmail());
        user.setUserName(userForm.getUserName());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setPassword(PasswordEncryptHelper.getMD5(userForm.getPassword()));
        user.setHotelID(userForm.getHotelId());
        user.setUserType(UserType.HotelManager);
        // 似乎还需要设置姓名手机号等？
        try {
            adminMapper.addManager(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO addSalesPerson(UserForm userForm) {
        User user = new User();
        user.setEmail(userForm.getEmail());
        user.setUserName(userForm.getUserName());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setPassword(userForm.getPassword());
        user.setUserType(UserType.SalesPerson);
        // 似乎还需要设置姓名手机号等？
        try {
            adminMapper.addSalesPerson(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<User> getAllManagers() {
        return adminMapper.getAllManagers();
    }

    @Override
    public List<User> getAllSalesPerson() {
        return adminMapper.getAllSalesPerson();
    }

}
