package com.example.hotel.blImpl.user;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.controller.hotel.HotelController;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.RoomVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: hx
 * @Date: 2020-05-24
 */

class Node{
    HotelVO hotel;
    int score;
    Node before = null;
    Node next = null;

    public Node(HotelVO hotel,int score){
        this.hotel = hotel;
        this.score = score;
    }

    public Node(){
        score = 0;
    }

}

public class SearchHotel {
    private final static String FIND_FAIL = "未找到匹配的酒店";

    @Autowired
    private HotelService hotelService;

    private HotelController hotelController;

    private String chechInDate = null;
    private String checkOutDate = null;
    private String address = null;
    private String bizRegion = null;
    private String hotelStar = null;  //酒店星级种类为长度为3的bitmap , 类型为String
    private String keyWords = null;
    private double maxPrice = -1;
    private double minScore = -1;

    Node head;


    public SearchHotel(HotelController hotelController) {
        this.hotelController = hotelController;
    }


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
    public void setInfo(String checkInDate,String checkOutDate,String address,String bizRegion,String hotelStar,
                        String keyWords,double maxPrice,double minScore){

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
    public void setInfo(String checkInDate,String checkOutDate,String address,String bizRegion,String hotelStar,
                        double maxPrice,double minScore){

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
    public void setInfo(String checkInDate,String checkOutDate,String address,String bizRegion,String hotelStar,
                        String keyWords,double maxPrice){

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
    public void setInfo(String checkInDate,String checkOutDate,String address,String bizRegion,String hotelStar,
                        double maxPrice){

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

    public void setInfo(String checkInDate,String checkOutDate,String address,String bizRegion,String hotelStar,
                        String keyWords){

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

        List<HotelVO> targetHotels = null;     //以关联度由高到底排序

        for(HotelVO hotel:hotelVOS){    //对所有的hotel，调取房间信息并检验
            ResponseVO response = hotelController.getAvailableRoom(hotel.getId(),chechInDate,checkOutDate);
            hotel = (HotelVO)response.getContent();

            //数据锁定的遍历，得到时间和地址都符合的hotel
            List<RoomVO> rooms = hotel.getRooms();


            /**
             * judge为Boolean变量
             * 对表单中刚性条件进行判定，如果某项不符合则为false
             * 如果符合，则酒店进入候选池,并进行相关度评分
             * 候选池中酒店信息以双链表方式存储
             *
             * 检验内容包括：
             * 1.酒店是否有符合时间条件的空房间
             * 2.酒店地址是否符合
             * 3.酒店价格是否在可接受区间内
             * 4。酒店星级是否符合要求
             * 5.酒店评分是否符合要求
             *
             */
            boolean judge = !rooms.isEmpty() && checkAddress(hotel,address) && checkPrice(hotel,maxPrice)
                            && checkHotelStar(hotel,hotelStar) && checkHotelScore(hotel, minScore);
            if(judge){
                int score = 0;
                score += checkBizRegion(hotel,bizRegion);
                score += checkKeyWords(hotel,keyWords);
                Node node = new Node(hotel,score);
                insertHotel(node);
            }
        }

        Node ptr = head.next;
        while(ptr!=null) {
            targetHotels.add(ptr.hotel);
            ptr = ptr.next;
        }

        return targetHotels;
    }



    /**
     * 刚性条件
     * 检查价格是否符合要求
     *
     */
    private boolean checkPrice(HotelVO hotel, double maxPrice){
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
     * 刚性条件
     * 检查酒店的得分是否符合顾客要求
     * @param hotel
     * @param minScore
     * @return
     */
    private boolean checkHotelScore(HotelVO hotel, double minScore){
        if(this.minScore==-1)
            return true;
        else{
            double hotelScore = hotel.getRate();
            return hotelScore >= minScore;
        }
    }


    /**
     * 刚性条件
     * 检查酒店星级,使用bitmap，从左到右三位分别表示“三星级”“四星级”“五星级”，0为false，1为true
     * ！如果顾客没有填写偏好，前端需传回 111 ！
     * @param hotel
     * @param hotelStar
     * @return
     */
    private boolean checkHotelStar(HotelVO hotel,String hotelStar){
        String star = hotel.getHotelStar();
        char[] stars = hotelStar.toCharArray();

        if(stars[0]=='1'){
            if(star=="三星级")
                return true;
        }else if(stars[1]=='1'){
            if(star=="四星级")
                return true;
        }else if(stars[2]=='1'){
            if(star=="五星级")
                return true;
        }
        return false;
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


    /**
     * 非刚性条件
     * 检查关键字，每个关键字加2分
     * 如果没有对关键字进行检索，默认返回0分
     * @param hotel
     * @param keyWords
     * @return
     */
    private int checkKeyWords(HotelVO hotel,String keyWords){
        if(keyWords==null)
            return 0;
        else{
            String[] keyWord = keyWords.split(" ");
            String description = hotel.getDescription();
            int l = keyWord.length;
            int score = 0;
            for(int i=0;i<l;i++){
                if(description.contains(keyWord[i]))
                    score += 2;
            }
            return score;
        }
    }


    /**
     * 在链表中插入酒店
     * 插入的同时按分数由高到低排序
     * @param hotel
     */
    private void insertHotel(Node hotel){
        Node ptr = head.next;
        while(true){
            if(ptr==null) {
                head.next = hotel;
                break;
            }
            else if(ptr.score < hotel.score){
                ptr.before.next = hotel;
                hotel.before = ptr.before;

                ptr.before = hotel;
                hotel.next = ptr;
                break;
            }else{
                ptr = ptr.next;
            }
        }
    }

}
