package com.example.hotel.po;

import com.example.hotel.enums.BedType;
import com.example.hotel.enums.BreakFast;
import com.example.hotel.enums.RoomType;
import com.example.hotel.vo.RoomVO;

public class HotelRoom {
    private Integer id;
    private RoomType roomType;
    private BedType bedType;
    private BreakFast breakfast;
    private Integer hotelId;
    private double price;
    /**
     * 当前剩余可预定房间数
     */
    private int curNum;
    /**
     * 某类型房间总数
     */
    private int total;

    private Integer peopleNum;

    public HotelRoom(){}

    public HotelRoom(RoomVO room){
        this.roomType = RoomType.valueOf(room.getRoomType());
        this.bedType = BedType.valueOf(room.getBedType());
        this.breakfast = BreakFast.valueOf(room.getBreakfast());
        this.hotelId = room.getId();
        this.price = room.getPrice();
        this.curNum = room.getCurNum();
        this.peopleNum = room.getPeopleNum();
        this.total = room.getTotal();
    }

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

    public BreakFast getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(BreakFast breakfast) {
        this.breakfast = breakfast;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getPeopleNum() { return peopleNum; }

    public void setPeopleNum(Integer peopleNum) { this.peopleNum = peopleNum; }

    public int getCurNum() {
        return curNum;
    }

    public void setCurNum(int curNum) {
        this.curNum = curNum;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
