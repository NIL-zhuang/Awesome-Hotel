package com.example.hotel.bl.VIP;

import com.example.hotel.po.ClientVIP;
import com.example.hotel.po.CorpVIP;
import com.example.hotel.vo.ClientVIPVO;
import com.example.hotel.vo.CorpVIPVO;
import com.example.hotel.vo.ResponseVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.junit.Assert.*;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/9 9:49
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class VIPServiceTest {

    @Autowired
    private VIPService vipService;

    private final String NJU = "Nanjing University";

    @Test
    @Transactional
    public void registerAsClientVIP() {
        vipService.registerAsClientVIP(5, "2020-01-01");
        ClientVIPVO clientVIPVO = (ClientVIPVO) vipService.getVIPbyUserId(5).getContent();
        assertEquals(clientVIPVO.getConsumption(), 0);
        assertEquals(clientVIPVO.getLevel(), 1);
    }

    @Test
    @Transactional
    public void freezeClientVIP() {
        vipService.registerAsClientVIP(5, "2020-01-01");
        vipService.freezeClientVIP(5);
        ClientVIPVO clientVIPVO = (ClientVIPVO) vipService.getVIPbyUserId(5).getContent();
        assertEquals(clientVIPVO.getConsumption(), 0);
        assertEquals(clientVIPVO.getLevel(), 1);
        assertEquals(clientVIPVO.getStatus(), 0);
    }

    @Test
    @Transactional
    public void restoreClientVIP() {
        vipService.registerAsClientVIP(5, "2020-01-01");
        vipService.freezeClientVIP(5);
        vipService.restoreClientVIP(5);
        ClientVIPVO clientVIPVO = (ClientVIPVO) vipService.getVIPbyUserId(5).getContent();
        assertEquals(clientVIPVO.getConsumption(), 0);
        assertEquals(clientVIPVO.getLevel(), 1);
        assertEquals(clientVIPVO.getStatus(), 1);
    }

    @Test
    @Transactional
    public void getVIPbyUserId() {
        // already pass
    }

    @Test
    @Transactional
    public void addVIPClientConsumption() {
        vipService.registerAsClientVIP(5, "2020-01-01");
        vipService.addVIPClientConsumption(5, 100);
        ClientVIPVO clientVIPVO = (ClientVIPVO) vipService.getVIPbyUserId(5).getContent();
        assertEquals(clientVIPVO.getConsumption(), 100);
    }

    @Test
    @Transactional
    public void registerAsCorpVIP() {
        vipService.registerAsCorpVIP(NJU);
        ResponseVO responseVO = vipService.getVIPbyCorpName(NJU);
        assertEquals(((CorpVIPVO) responseVO.getContent()).getCorporationName(), NJU);
    }

    @Test
    @Transactional
    public void freezeCorpVIP() {
        vipService.registerAsCorpVIP(NJU);
        vipService.freezeCorpVIP(NJU);
        CorpVIPVO corpVIPVO = (CorpVIPVO) vipService.getVIPbyCorpName(NJU).getContent();
        assertEquals(corpVIPVO.getStatus(), 0);
    }

    @Test
    @Transactional
    public void restoreCorpVIP() {
        vipService.registerAsCorpVIP(NJU);
        vipService.freezeCorpVIP(NJU);
        vipService.restoreCorpVIP(NJU);
        CorpVIPVO corpVIPVO = (CorpVIPVO) vipService.getVIPbyCorpName(NJU).getContent();
        assertEquals(corpVIPVO.getStatus(), 1);
    }

    @Test
    @Transactional
    public void getVIPbyCorpName() {
        // already pass
    }

    @Test
    @Transactional
    public void addVIPCorpConsumption() {
        vipService.registerAsCorpVIP(NJU);
        vipService.addVIPCorpConsumption(NJU, 100);
        CorpVIPVO corpVIPVO = (CorpVIPVO) vipService.getVIPbyCorpName(NJU).getContent();
        assertEquals(corpVIPVO.getConsumption(), 100);
    }

    @Test
    @Transactional
    public void VIPCorpCheck() {
        vipService.registerAsCorpVIP(NJU);
        assertTrue(vipService.VIPCorpCheck(NJU));
    }

    @Test
    @Transactional
    public void getAllVIPClient() {
        vipService.registerAsClientVIP(5, "2020-01-01");
        List<ClientVIP> clientVIPS = (List<ClientVIP>) vipService.getAllVIPClient().getContent();
        assertEquals(clientVIPS.size(), 1);
    }

    @Test
    @Transactional
    public void getAllVIPCorp() {
        vipService.registerAsCorpVIP(NJU);
        List<CorpVIP> corpVIPS = (List<CorpVIP>) vipService.getAllVIPCorp().getContent();
        assertEquals(corpVIPS.size(), 1);
    }
}