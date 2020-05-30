package com.example.hotel.enums;

/**
 * @Author: hx
 * @Date: 2020-05-24
 */

public enum HotelTag {
    Spa("温泉"),
    Clean("干净整洁"),
    Cheap("便宜"),
    Breakfast("早餐"),
    Excellent("体验很棒"),
    GoodNightService("晚安服务"),
    Wine("酒"),
    Cola("可乐"),
    Quiet("安静"),
    Lover("情侣酒店"),
    Romantic("浪漫"),
    Safe("安全");

    private String value;

    HotelTag(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
