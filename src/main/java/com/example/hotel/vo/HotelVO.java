package com.example.hotel.vo;

import com.example.hotel.po.Hotel;

import java.util.List;

public class HotelVO {
    private Integer id;
    private String name;
    private String address;
    private String bizRegion;
    private String hotelStar;
    private Double rate;
    private String description;
    private String phoneNum;
    private Integer commentTime;
    private double sanitation;
    private double environment;
    private double service;
    private double equipment;
    private String url;

    public HotelVO() {
    }

    public HotelVO(Hotel hotel) {
        if (hotel != null) {
            id = hotel.getId();
            name = hotel.getHotelName();
            address = hotel.getAddress();
            bizRegion = hotel.getBizRegion().toString();
            hotelStar = hotel.getHotelStar().toString();
            rate = hotel.getPoints();
            description = hotel.getDescription();
            phoneNum = hotel.getPhoneNum();
            commentTime = hotel.getCommentTime();
            sanitation = hotel.getSanitation();
            environment = hotel.getEnvironment();
            service = hotel.getService();
            equipment = hotel.getEquipment();
            //如果需要还可以再加
        }
    }

    private List<RoomVO> rooms;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String hotelName) {
        this.name = hotelName;
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

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getHotelStar() {
        return hotelStar;
    }

    public void setHotelStar(String hotelStar) {
        this.hotelStar = hotelStar;
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

    public List<RoomVO> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomVO> rooms) {
        this.rooms = rooms;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Integer commentTime) {
        this.commentTime = commentTime;
    }

    public double getSanitation() {
        return sanitation;
    }

    public void setSanitation(double sanitation) {
        this.sanitation = sanitation;
    }

    public double getEnvironment() {
        return environment;
    }

    public void setEnvironment(double environment) {
        this.environment = environment;
    }

    public double getService() {
        return service;
    }

    public void setService(double service) {
        this.service = service;
    }

    public double getEquipment() {
        return equipment;
    }

    public void setEquipment(double equipment) {
        this.equipment = equipment;
    }

    public double getMinPrice() {
        List<RoomVO> rooms = getRooms();
        try {
            double minPrice = Double.MAX_VALUE;
            if (rooms == null || rooms.size() == 0) {
                return Double.MAX_VALUE;
            }
            for (RoomVO roomVO : rooms) {
                minPrice = Math.min(minPrice, roomVO.getPrice());
            }
            return minPrice;
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
