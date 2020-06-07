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
    public ResponseVO getUserVIP(@PathVariable int id){
        System.out.println(id);
        ClientVIP clientVIP = vipService.getVIPbyUserId(id);
        return ResponseVO.buildSuccess(clientVIP);
    }

    @PostMapping("/registerCorpMembership")
    public ResponseVO registerCorpMembership(@RequestParam(value = "corpName")String corpName){
        return vipService.registerAsCorpVIP(corpName);
    }

    @GetMapping("/{corpName}/getCorpVIP")
    public ResponseVO getCorpVIP(@PathVariable String corpName){
        CorpVIP corpVIP = vipService.getVIPbyCorpName(corpName);
        return ResponseVO.buildSuccess(corpVIP);
    }

    @GetMapping("/CorpMembershipCheck")
    public boolean VIPCorpCheck(@RequestParam(value = "corpName")String corpName){
        return vipService.VIPCorpCheck(corpName);
    } // 网站添加企业优惠券时候用到
}
