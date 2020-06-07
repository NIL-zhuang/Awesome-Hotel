package com.example.hotel.controller.VIP;

import com.example.hotel.bl.VIP.VIPService;
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
        return vipService.getVIPbyUserId(id);
    }

    @PostMapping("/addVIPUserConsumption")
    public ResponseVO addVIPUserConsumption(@RequestParam(value = "id")Integer id,
                                            @RequestParam(value = "amount")Integer amount){
        return vipService.addVIPClientConsumption(id,amount);
    }

    @PostMapping("/{id}/ClientVIPLevelUp")
    public ResponseVO ClientVIPLevelUp(@PathVariable Integer id){
        return vipService.clientLevelUp(id);
    }

    @PostMapping("/registerCorpMembership")
    public ResponseVO registerCorpMembership(@RequestParam(value = "corpName")String corpName){
        return vipService.registerAsCorpVIP(corpName);
    }

    @GetMapping("/{corpName}/getCorpVIP")
    public ResponseVO getCorpVIP(@PathVariable String corpName){
        return vipService.getVIPbyCorpName(corpName);
    }

    @PostMapping("/addVIPCorpConsumption")
    public ResponseVO addVIPCorpConsumption(@RequestParam(value = "corpName") String corpName,
                                            @RequestParam(value = "amount")Integer amount){
        return vipService.addVIPCorpConsumption(corpName, amount);
    }

    @PostMapping("/{corpName}/CorpVIPLevelUp")
    public ResponseVO CorpVIPLevelUp(@PathVariable String corpName){
        return vipService.corpLevelUp(corpName);
    }

    @GetMapping("/{corpName}/CorpMembershipCheck")
    public ResponseVO VIPCorpCheck(@PathVariable String corpName){
        return ResponseVO.buildSuccess(vipService.VIPCorpCheck(corpName));
    } // 网站添加企业优惠券时候用到
}
