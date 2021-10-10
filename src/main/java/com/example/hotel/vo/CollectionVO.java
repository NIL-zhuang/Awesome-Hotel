package com.example.hotel.vo;

import com.example.hotel.po.Hotel;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/5/24 9:30
 */
public class CollectionVO {
    private Integer id;
    private Integer userID;
    private Integer hotelID;
    private String hotelName;
    private String bizRegion;
    private String address;
    private double rate;
    private String hotelStar;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getHotelID() {
        return this.hotelID;
    }

    public void setHotelID(Integer hotelID) {
        this.hotelID = hotelID;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBizRegion() {
        return bizRegion;
    }

    public void setBizRegion(String bizRegion) {
        this.bizRegion = bizRegion;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelStar() {
        return hotelStar;
    }

    public void setHotelStar(String hotelStar) {
        this.hotelStar = hotelStar;
    }

    public void setHotelInfo(Hotel hotel) {
        hotelName = hotel.getHotelName();
        bizRegion = hotel.getBizRegion().toString();
        address = hotel.getAddress();
        rate = hotel.getPoints();
        hotelStar = hotel.getHotelStar().toString();
    }
}
