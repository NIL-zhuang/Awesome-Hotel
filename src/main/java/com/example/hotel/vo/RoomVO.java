package com.example.hotel.vo;

import com.example.hotel.enums.BedType;
import com.example.hotel.enums.RoomType;

public class RoomVO {
    private Integer id;
    private RoomType roomType;
    private BedType bedType;
    private String breakfast;
    private Double price;
    private Integer curNum;   //目前有几间
    private Integer total;
    private Integer peopleNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public BedType getBedType() {
        return bedType;
    }

    public void setBedType(BedType bedType) {
        this.bedType = bedType;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCurNum() {
        return curNum;
    }

    public void setCurNum(Integer curNum) {
        this.curNum = curNum;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPeopleNum() { return peopleNum; }

    public void setPeopleNum(Integer peopleNum) { this.peopleNum = peopleNum; }

}
