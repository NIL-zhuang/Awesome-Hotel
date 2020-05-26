package com.example.hotel.blImpl.user;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.controller.hotel.HotelController;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;
import com.example.hotel.po.Hotel;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.RoomVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: hx
 * @Date: 2020-05-24
 */

public class SearchHotel {
    private final static String FIND_FAIL = "未找到匹配的酒店";

    @Autowired
    private HotelService hotelService;
    private HotelController hotelController;

    private String chechInDate;
    private String checkOutDate;
    private String address;
    private String bizRegion;
    private String hotelStar;  //酒店星级种类为长度为3的bitmap , 类型为String
    private String keyWords;
    private double maxPrice = 1000;
    private double minPrice = 0;
    private double minScore = 2.5;

    /**
     * 表单一
     * @param checkInDate
     * @param checkOutDate
     * @param address
     * @param bizRegion
     * @param hotelStar
     * @param keyWords
     * @param maxPrice
     * @param minScore
     */
    public void setInfo(String checkInDate,String checkOutDate,String address,String bizRegion,String hotelStar,String keyWords,double maxPrice,double minScore){
        setInfo(checkInDate,checkOutDate,address,bizRegion,hotelStar,maxPrice,minScore);
        this.keyWords = keyWords;
    }

    /**
     * 表单二
     * @param checkInDate
     * @param checkOutDate
     * @param address
     * @param bizRegion
     * @param hotelStar
     * @param maxPrice
     * @param minScore
     */

    public void setInfo(String checkInDate,String checkOutDate,String address,String bizRegion,String hotelStar,double maxPrice,double minScore){
        setInfo(checkInDate,checkOutDate,address,bizRegion,hotelStar,maxPrice);
        this.minScore = minScore;
    }

    /**
     * 表单三
     * @param checkInDate
     * @param checkOutDate
     * @param address
     * @param bizRegion
     * @param hotelStar
     * @param keyWords
     * @param maxPrice
     */

    public void setInfo(String checkInDate,String checkOutDate,String address,String bizRegion,String hotelStar,String keyWords,double maxPrice){
        setInfo(checkInDate,checkOutDate,address,bizRegion,hotelStar,keyWords);
        this.maxPrice = maxPrice;
    }

    /**
     * 表单四
     * @param checkInDate
     * @param checkOutDate
     * @param address
     * @param bizRegion
     * @param hotelStar
     * @param maxPrice
     */

    public void setInfo(String checkInDate,String checkOutDate,String address,String bizRegion,String hotelStar,double maxPrice){
        setInfo(checkInDate,checkOutDate,address,bizRegion,hotelStar);
        this.maxPrice = maxPrice;
    }

    /**
     * 表单五
     * @param checkInDate
     * @param checkOutDate
     * @param address
     * @param bizRegion
     * @param hotelStar
     */

    public void setInfo(String checkInDate,String checkOutDate,String address,String bizRegion,String hotelStar){
        setInfo(checkInDate,checkOutDate,address,bizRegion);
        this.hotelStar = hotelStar;
    }

    /**
     * 表单六
     * @param checkInDate
     * @param checkOutDate
     * @param address
     * @param bizRegion
     * @param maxPrice
     */

    public void setInfo(String checkInDate,String checkOutDate,String address,String bizRegion,double maxPrice){
        setInfo(checkInDate,checkOutDate,address,bizRegion);
        this.maxPrice = maxPrice;
    }

    /**
     * 表单七
     * @param checkInDate
     * @param checkOutDate
     * @param address
     * @param minScore
     */

    public void setInfo(String checkInDate,String checkOutDate,String address,double minScore){
        setInfo(checkInDate,checkOutDate,address);
        this.minScore = minScore;
    }

    /**
     * 表单八
     * @param checkInDate
     * @param checkOutDate
     * @param address
     * @param bizRegion
     * @param hotelStar
     * @param keyWords
     */

    public void setInfo(String checkInDate,String checkOutDate,String address,String bizRegion,String hotelStar,String keyWords){
        setInfo(checkInDate,checkOutDate,address,bizRegion,hotelStar);
        this.keyWords = keyWords;
    }

    /**
     * 表单九
     * @param checkInDate
     * @param checkOutDate
     * @param address
     * @param bizRegion
     */


    public void setInfo(String checkInDate,String checkOutDate,String address,String bizRegion){
        setInfo(checkInDate,checkOutDate,address);
        this.bizRegion = bizRegion;
    }

    /**
     * 表单十
     * @param checkInDate
     * @param checkOutDate
     * @param address
     */


    public void setInfo(String checkInDate,String checkOutDate,String address){
        this.chechInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.address = address;
    }


    /**
     * 搜索，以 List<HotelVO> 格式返回符合要求的酒店
     * @return
     */
    public List<HotelVO> search(){
        List<HotelVO> hotelVOS = hotelService.retrieveHotels();
        List<HotelVO> targetHotels = null;     //关联度高的hotel
        List<HotelVO> others = null;   //关联度较小的hotel

        for(HotelVO hotel:hotelVOS){    //对所有的hotel，调取房间信息并检验
            ResponseVO response = hotelController.getAvailableRoom(hotel.getId(),chechInDate,checkOutDate);
            hotel = (HotelVO)response.getContent();

            //数据锁定的遍历，得到时间和地址都符合的hotel
            List<RoomVO> rooms = hotel.getRooms();

            /**
             * judge为Boolean变量
             * 对表单中刚性条件进行判定，如果某项不符合则为false
             * 如果符合，则酒店进入候选池
             * 会把没有空房的酒店剔除
             *
             */
            boolean judge = !rooms.isEmpty() && checkAddress(hotel,address) && checkPrice(hotel,maxPrice);
            if(judge)
                others.add(hotel);
        }

        for(HotelVO hotel:others){
            int score = 0;
            score += checkBizRegion(hotel,bizRegion);
            score += checkKeyWords(hotel,keyWords);
            score += checkHotelStar(hotel,hotelStar);
            if(score>6)
                targetHotels.add(hotel);
        }

        return targetHotels;
    }

    /**
     * 刚性条件
     * 检查价格是否符合要求
     *
     */

    private boolean checkPrice(HotelVO hotel, double maxPrice){
        boolean judge = true;
        double hotelPrice = hotel.getMinPrice();
        return maxPrice > hotelPrice;
    }

    /**
     * 刚性条件
     * 检查地址是否符合要求
     *
     */

    private boolean checkAddress(HotelVO hotel,String address){
        return hotel.getAddress().equals(address);
    }

    /**
     * 非刚性条件
     * 检查商圈是否符合要求，如果符合
     */

    private int checkBizRegion(HotelVO hotel,String bizRegion){
        if(bizRegion == hotel.getBizRegion())
            return 5;
        else
            return 0;
    }

    private int checkKeyWords(HotelVO hotel,String keyWords){
        String[] keyWord = keyWords.split(" ");
        String description = hotel.getDescription();
        int l = keyWord.length;
        int score = 0;
        for(int i=0;i<l;i++){
            if(description.contains(keyWord[i]))
                score++;
        }
        return score;
    }

    private int checkHotelStar(HotelVO hotel,String hotelStar){
        String star = hotel.getHotelStar();
        char[] stars = hotelStar.toCharArray();
        int score = 0;
        if(stars[0]=='1'){
            if(star=="三星级")
                score += 2;
        }else if(stars[1]=='1'){
            if(star=="四星级")
                score += 2;
        }else if(stars[2]=='1'){
            if(star=="五星级")
                score += 2;
        }
        return score;
    }

}
