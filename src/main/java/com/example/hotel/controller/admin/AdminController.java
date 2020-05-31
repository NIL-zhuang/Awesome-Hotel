package com.example.hotel.controller.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@RestController()
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @PostMapping("/addManager")
    public ResponseVO addManager(@RequestBody UserForm userForm){
        return adminService.addManager(userForm);
    }

    @PostMapping("/addSalesPerson")
    public ResponseVO addSalesPerson(@RequestBody UserForm userForm){
        return adminService.addSalesPerson(userForm);
    }

    @PostMapping("/getAllManagers")
    public ResponseVO getAllManagers(){
        return ResponseVO.buildSuccess(adminService.getAllManagers());
    }

    @PostMapping("/getAllSalesPerson")
    public ResponseVO getAllSalesPerson(){
        return ResponseVO.buildSuccess(adminService.getAllSalesPerson());
    }

    @PostMapping("/{id}/deleteHotelManager")
    public ResponseVO deleteHotelManager(@PathVariable Integer id) {
        return adminService.deleteManager(id);
    }

    @PostMapping("/{id}/deleteSalesPerson")
    public ResponseVO deleteSalesPerson(@PathVariable Integer id) {
        return adminService.deleteSalesPerson(id);
    }
}
