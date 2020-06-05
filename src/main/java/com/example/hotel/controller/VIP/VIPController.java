package com.example.hotel.controller.VIP;

import com.example.hotel.bl.VIP.VIPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class VIPController {

    @Autowired
    private VIPService vipService;


}
