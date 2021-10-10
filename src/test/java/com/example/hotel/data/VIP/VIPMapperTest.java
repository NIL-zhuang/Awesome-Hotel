package com.example.hotel.data.VIP;

import com.example.hotel.po.ClientVIP;
import com.example.hotel.po.CorpVIP;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/7 10:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class VIPMapperTest {

    @Autowired
    private VIPMapper vipMapper;

    private final String corpName = "Nanjing University";

    private final ClientVIP clientVIP = new ClientVIP(5) {{
        setConsumption(5);
        setLevel(3);
        setReduction(0.88);
    }};

    private final CorpVIP corpVIP = new CorpVIP() {{
        setStatus(1);
        setReduction(0.88);
        setLevel(3);
        setConsumption(5);
        setCorporationName(corpName);
    }};

    @Test
    @Transactional
    public void registerAsClientVIP() {
        vipMapper.registerAsClientVIP(clientVIP);
        ClientVIP vip = vipMapper.getVIPbyUserId(5);
        Assert.assertEquals(vip.getConsumption(), 0);
        Assert.assertEquals(vip.getReduction(), 0.88, 1e-5);
    }

    @Test
    public void getVIPbyUserId() {
        // the same as registerAsClientVIP
    }

    @Test
    @Transactional
    public void addVIPClientConsumption() {
        vipMapper.registerAsClientVIP(clientVIP);
        vipMapper.addVIPClientConsumption(5, 100);
        Assert.assertEquals(vipMapper.getVIPbyUserId(5).getConsumption(), 100);
    }

    @Test
    @Transactional
    public void clientLevelUp() {
        vipMapper.registerAsClientVIP(clientVIP);
        vipMapper.clientLevelUp(5, 0.05);
        Assert.assertEquals(vipMapper.getVIPbyUserId(5).getLevel(), 4);
    }

    @Test
    @Transactional
    public void registerAsCorpVIP() {
        vipMapper.registerAsCorpVIP(corpVIP);
        CorpVIP corpVIP = vipMapper.getVIPbyCorpName(corpName);
        Assert.assertEquals(corpVIP.getConsumption(), 5);
        Assert.assertEquals(corpVIP.getLevel(), 3);
    }

    @Test
    public void getVIPbyCorpName() {
        // the same as registerAsCorpVIP
    }

    @Test
    @Transactional
    public void addVIPCorpConsumption() {
        vipMapper.registerAsCorpVIP(corpVIP);
        vipMapper.addVIPCorpConsumption(corpName, 5);
        Assert.assertEquals(vipMapper.getVIPbyCorpName(corpName).getConsumption(), 10);
    }

    @Test
    @Transactional
    public void corpLevelUp() {
        vipMapper.registerAsCorpVIP(corpVIP);
        vipMapper.corpLevelUp(corpName, 0.05);
        Assert.assertEquals(vipMapper.getVIPbyCorpName(corpName).getLevel(), 4);
    }

    @Test
    @Transactional
    public void freezeClientVIP() {
        vipMapper.registerAsClientVIP(clientVIP);
        vipMapper.freezeClientVIP(5);
        Assert.assertEquals(vipMapper.getVIPbyUserId(5).getStatus(), 0);
    }

    @Test
    @Transactional
    public void restoreClientVIP() {
        vipMapper.registerAsClientVIP(clientVIP);
        vipMapper.freezeClientVIP(5);
        vipMapper.restoreClientVIP(5);
        Assert.assertEquals(vipMapper.getVIPbyUserId(5).getStatus(), 1);
    }

    @Test
    @Transactional
    public void getAllVIPClient() {
        vipMapper.registerAsClientVIP(clientVIP);
        List<ClientVIP> clientVIPS = vipMapper.getAllVIPClient();
        Assert.assertEquals(clientVIPS.size(), 1);
        Assert.assertEquals(clientVIPS.get(0).getUserId(), 5);
    }

    @Test
    @Transactional
    public void getAllVIPCorp() {
        vipMapper.registerAsCorpVIP(corpVIP);
        List<CorpVIP> corpVIPS = vipMapper.getAllVIPCorp();
        Assert.assertEquals(corpVIPS.size(), 1);
        Assert.assertEquals(corpVIPS.get(0).getCorporationName(), corpName);
    }
}