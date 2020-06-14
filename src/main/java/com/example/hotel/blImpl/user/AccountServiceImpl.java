package com.example.hotel.blImpl.user;

import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.data.user.CreditMapper;
import com.example.hotel.enums.VIPType;
import com.example.hotel.po.Credit;
import com.example.hotel.po.User;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AccountServiceImpl implements AccountService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    private final static String UPDATE_ERROR = "修改失败";

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private CreditMapper creditMapper;

    @Override
    public ResponseVO registerAccount(UserVO userVO) {
        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        user.setPassword(PasswordEncryptHelper.getMD5(user.getPassword()));
        user.setAnnulTime(3);
        try {
            accountMapper.createNewAccount(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public UserVO login(UserForm userForm) {
        User user = accountMapper.getAccountByEmail(userForm.getEmail());
        if (!user.getPassword().equals(PasswordEncryptHelper.getMD5(userForm.getPassword()))) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public UserVO getUserInfo(int id) {
        User user = accountMapper.getAccountById(id);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public UserVO getUserInfoByEmail(String email) {
        User user = accountMapper.getAccountByEmail(email);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
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

//    @Override
//    public ResponseVO personalVIP(int id, String birthday) {
//        try {
//            accountMapper.updateBirthday(id, birthday);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseVO.buildFailure(UPDATE_ERROR);
//        }
//        return ResponseVO.buildSuccess(true);
//    }
//
//    @Override
//    public ResponseVO corporateVIP(int id, String corporate) {
//        try {
//            accountMapper.updateCorporate(id, corporate);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseVO.buildFailure(UPDATE_ERROR);
//        }
//        return ResponseVO.buildSuccess(true);
//    }

//    @Override
//    public ResponseVO normalUser(String corporate) {
//        return null;
//    }

    @Override
    public void updateBirthday(int id, String birthday){
        try {
            accountMapper.updateBirthday(id, birthday);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void registerAsVIP(int id){
        try {
            accountMapper.updateVIPType(id, VIPType.Client);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void freezeVIP(int id) {
        try{
            accountMapper.updateVIPType(id, VIPType.Normal);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public ResponseVO updatePortrait(int userId, String url) {
        try {
            accountMapper.updatePortrait(userId, url);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public List<String> getManagerTelephone(int hotelId) {
        return null;
    }

    @Override
    public ResponseVO chargeCredit(int userId, int change) {
        try{
            accountMapper.chargeCredit(userId, change);
            User user = accountMapper.getAccountById(userId);
            creditMapper.addCredit(new Credit(userId, change, user.getCredit()));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess();
    }
}
