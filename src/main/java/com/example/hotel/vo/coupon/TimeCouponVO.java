package com.example.hotel.vo.coupon;

public class TimeCouponVO extends CouponVO {

    private String startTime;

    private String endTime;

    private double discount;

    private Integer targetMoney;

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Integer getTargetMoney() {
        return targetMoney;
    }

    public void setTargetMoney(Integer targetMoney) {
        this.targetMoney = targetMoney;
    }
}
