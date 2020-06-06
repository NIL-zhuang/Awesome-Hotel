package com.example.hotel.controller.VIP;

import com.example.hotel.bl.VIP.VIPService;
import com.example.hotel.po.ClientVIP;
import com.example.hotel.po.CorpVIP;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/membership")
public class VIPController {

    @Autowired
    private VIPService vipService;

    @PostMapping("/registerClientMembership")
    public ResponseVO registerClientMembership(@RequestParam(value = "id") Integer id,
                                         @RequestParam(value = "birthday") String birthday) {
        return vipService.registerAsClientVIP(id, birthday);
    }

    @GetMapping("/{id}/getUserVIP")
    public ClientVIP getUserVIP(@PathVariable int id){
        return vipService.getVIPbyUserId(id);
    }

    @PostMapping("/registerCorpMembership")
    public ResponseVO registerCorpMembership(@RequestParam(value = "corpName")String corpName){
        return vipService.registerAsCorpVIP(corpName);
    }

    @GetMapping("/{corpName}/getCorpVIP")
    public CorpVIP getCorpVIP(@PathVariable String corpName){
        return vipService.getVIPbyCorpName(corpName);
    }

    @GetMapping("/CorpMembershipCheck")
    public boolean VIPCorpCheck(@RequestParam(value = "corpName")String corpName){
        return vipService.VIPCorpCheck(corpName);
    } // 网站添加企业优惠券时候用到
}
