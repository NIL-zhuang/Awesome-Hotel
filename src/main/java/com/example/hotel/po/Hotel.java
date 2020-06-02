package com.example.hotel.po;

import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;

public class Hotel {

    private Integer id;
    private String hotelName;
    private String address;
    /**
     * 商圈
     */
    private BizRegion bizRegion;
    private HotelStar hotelStar;
    private Double rate;
    private String description;
    private String phoneNum;
    /**
     * 酒店评价
     */
    private Integer commentTime;
    private double points;
    private double sanitation;
    private double environment;
    private double service;
    private double equipment;
    private String url;

    public void setCommentTime(Integer commentTime) {
        this.commentTime = commentTime;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public void setSanitation(double sanitation) {
        this.sanitation = sanitation;
    }

    public void setEnvironment(double environment) {
        this.environment = environment;
    }

    public void setService(double service) {
        this.service = service;
    }

    public void setEquipment(double equipment) {
        this.equipment = equipment;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 酒店管理员id
     */
    private Integer managerId;

    public Integer getCommentTime() {
        return commentTime;
    }

    public double getPoints() {
        return points;
    }

    public double getSanitation() {
        return sanitation;
    }

    public double getEnvironment() {
        return environment;
    }

    public double getService() {
        return service;
    }

    public double getEquipment() {
        return equipment;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BizRegion getBizRegion() {
        // TODO change here
        return bizRegion;
    }

    public void setBizRegion(BizRegion bizRegion) {
        this.bizRegion = bizRegion;
    }

    public HotelStar getHotelStar() {
        // todo CHANGE HERE
        return hotelStar;
    }

    public void setHotelStar(HotelStar hotelStar) {
        this.hotelStar = hotelStar;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }
}

