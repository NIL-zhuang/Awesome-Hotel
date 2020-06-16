package com.example.hotel.controller.user;

import com.example.hotel.bl.user.AccountService;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserInfoVO;
import com.example.hotel.vo.UserVO;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController()
@RequestMapping("/api/user")
public class AccountController {
    private final static String ACCOUNT_INFO_ERROR = "用户名或密码错误";
    private final static String ACCOUNT_NOTFOUND = "账号不存在";
    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public ResponseVO login(@RequestBody UserForm userForm) {
        UserVO user = accountService.login(userForm);
        if (user == null) {
            return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
        }
        return ResponseVO.buildSuccess(user);
    }

    @PostMapping("/register")
    public ResponseVO registerAccount(@RequestBody UserVO userVO) {
        return accountService.registerAccount(userVO);
    }


    @GetMapping("/{id}/getUserInfo")
    public ResponseVO getUserInfo(@PathVariable int id) {
        UserVO user = accountService.getUserInfo(id);
        if(user==null){
            return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
        }
        return ResponseVO.buildSuccess(user);
    }

    @PostMapping("/{id}/userInfo/update")
    public ResponseVO updateInfo(@RequestBody UserInfoVO userInfoVO,@PathVariable int id){
        return accountService.updateUserInfo(id,userInfoVO.getPassword(),userInfoVO.getUserName(),userInfoVO.getPhoneNumber());
    }

    @PostMapping("/{id}/updateUserBirthday")
    public ResponseVO updateBirthday(@RequestParam Integer id, @RequestParam String birthday) {
        try {
            accountService.updateBirthday(id, birthday);
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(ACCOUNT_NOTFOUND);
        }
        return ResponseVO.buildSuccess(true);
    }

    @PostMapping("/registerCorporationMembership")
    public ResponseVO registerCorporationMembership(@RequestParam Integer id,
                                                    @RequestParam String corporation) {
        System.out.println(id);
        System.out.println(corporation);
        return ResponseVO.buildSuccess(true);
    }

    @GetMapping("/getUserInfoByEmail")
    public ResponseVO getUserInfoByEmail(@RequestParam String email) {
        UserVO user = accountService.getUserInfoByEmail(email);
        if(user==null){
            return ResponseVO.buildFailure(ACCOUNT_NOTFOUND);
        }
        return ResponseVO.buildSuccess(user);
    }

    @PostMapping("/{id}/chargeCredit")
    public ResponseVO chargeCredit(@PathVariable Integer id,
                                   @RequestParam(value = "change") Integer change, @RequestParam(value = "reason")String reason) {
        return accountService.chargeCredit(id, change, reason);
    }

    @GetMapping("/{id}/getUserCreditChanges")
    public ResponseVO getUserCreditChanges(@PathVariable Integer id){
        return accountService.getUserCreditChanges(id);
    }

    @GetMapping("/getAllUsers")
    public ResponseVO getAllUsers() {
        try {
            List<UserVO> allUsers = accountService.getAllUsers();
            return ResponseVO.buildSuccess(allUsers);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("获取所有用户失败");
        }
    }
}
