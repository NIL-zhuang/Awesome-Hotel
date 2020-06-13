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

import static org.hamcrest.CoreMatchers.*;


/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/7 10:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class VIPMapperTest {

    @Autowired
    private VIPMapper vipMapper;

    private final ClientVIP clientVIP = new ClientVIP(5) {{
        setConsumption(5);
        setLevel(3);
        setReduction(0.88);
    }};

    private final CorpVIP corpVIP = new CorpVIP("Nanjing University") {{
        setLevel(2);
        setReduction(0.4);
        setStatus(1);
    }};

    @Test
    @Transactional
    public void registerAsClientVIP() {
        vipMapper.registerAsClientVIP(clientVIP);
        ClientVIP vip = vipMapper.getVIPbyUserId(5);
        Assert.assertEquals(vip.getConsumption(), 5);
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
    public void clientLevelUp() {
    }

    @Test
    public void registerAsCorpVIP() {
    }

    @Test
    public void getVIPbyCorpName() {
    }

    @Test
    public void addVIPCorpConsumption() {
    }

    @Test
    public void corpLevelUp() {
    }

    @Test
    public void testRegisterAsClientVIP() {
    }

    @Test
    public void freezeClientVIP() {
    }

    @Test
    public void restoreClientVIP() {
    }

    @Test
    public void testGetVIPbyUserId() {
    }

    @Test
    public void testAddVIPClientConsumption() {
    }

    @Test
    public void testClientLevelUp() {
    }

    @Test
    public void testRegisterAsCorpVIP() {
    }

    @Test
    public void freezeCorpVIP() {
    }

    @Test
    public void restoreCorpVIP() {
    }

    @Test
    public void testGetVIPbyCorpName() {
    }

    @Test
    public void testAddVIPCorpConsumption() {
    }

    @Test
    public void testCorpLevelUp() {
    }

    @Test
    public void getAllVIPClient() {
    }

    @Test
    public void getAllVIPCorp() {
    }
}