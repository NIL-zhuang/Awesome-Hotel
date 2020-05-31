package com.example.hotel.controller.user;

import com.example.hotel.bl.user.AccountService;
import com.example.hotel.po.User;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserInfoVO;
import com.example.hotel.vo.UserVO;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController()
@RequestMapping("/api/user")
public class AccountController {
    private final static String ACCOUNT_INFO_ERROR = "用户名或密码错误";
    private final static String ACCOUNT_NOTFOUND = "账号不存在";
    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public ResponseVO login(@RequestBody UserForm userForm) {
        User user = accountService.login(userForm);
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
        User user = accountService.getUserInfo(id);
        if(user==null){
            return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
        }
        return ResponseVO.buildSuccess(user);
    }

    @PostMapping("/{id}/userInfo/update")
    public ResponseVO updateInfo(@RequestBody UserInfoVO userInfoVO,@PathVariable int id){
        return accountService.updateUserInfo(id,userInfoVO.getPassword(),userInfoVO.getUserName(),userInfoVO.getPhoneNumber());
    }

    // TODO 用户注册会员
    @PostMapping("/registerMembership")
    public ResponseVO registerMembership(@RequestParam Integer id,
                                         @RequestParam String birthday) {
        System.out.println(id);
        System.out.println(birthday);
        return ResponseVO.buildSuccess(true);
    }

    @GetMapping("/getUserInfoByEmail")
    public ResponseVO getUserInfoByEmail(@RequestParam String email) {
        User user = accountService.getUserInfoByEmail(email);
        if(user==null){
            return ResponseVO.buildFailure(ACCOUNT_NOTFOUND);
        }
        return ResponseVO.buildSuccess(user);
    }

    // TODO 按照充值金额给用户充值信誉积分
    @PostMapping("/{id}/chargeCredit")
    public ResponseVO chargeCredit(@RequestParam Integer money, @PathVariable Integer id) {
        System.out.println(id);
        System.out.println(money);
        return ResponseVO.buildSuccess(true);
    }
}
