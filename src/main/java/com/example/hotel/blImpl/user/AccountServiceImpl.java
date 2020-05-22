package com.example.hotel.blImpl.user;

import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.po.User;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AccountServiceImpl implements AccountService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    private final static String UPDATE_ERROR = "修改失败";

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public ResponseVO registerAccount(UserVO userVO) {
        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        user.setPassword(PasswordEncryptHelper.getMD5(user.getPassword()));
        user.setAnnualTime(3);
        try {
            accountMapper.createNewAccount(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public User login(UserForm userForm) {
        User user = accountMapper.getAccountByName(userForm.getEmail());
        // 正式上线的时候要删掉以下3行，现有的管理员和酒店工作人员特殊处理
        if (null == user) return null;
        if (user.getEmail().equals("333@qq.com") || user.getEmail().equals("123@qq.com")) {
            return user;
        }
        if (!user.getPassword().equals(PasswordEncryptHelper.getMD5(userForm.getPassword()))) {
            return null;
        }
        return user;
    }

    @Override
    public User getUserInfo(int id) {
        return accountMapper.getAccountById(id);
    }

    @Override
    public ResponseVO updateUserInfo(int id, String password, String username, String phoneNumber) {
        try {
            accountMapper.updateAccount(id, PasswordEncryptHelper.getMD5(password), username, phoneNumber);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    //todo
    @Override
    public ResponseVO updateCredit(int id, double credit) {
        try {
            accountMapper.updateCredit(id, credit);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO personalVIP(int id, String birthday) {
        try {
            accountMapper.updateBirthday(id, birthday);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }
}
