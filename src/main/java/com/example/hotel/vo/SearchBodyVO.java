package com.example.hotel.vo;

public class SearchBodyVO {


    private String checkInDate;
    private String checkOutDate;
    private String address;
    private String bizRegion;
    private String[] keyWords;
    private double maxPrice;
    private double minScore;
    private String[] hotelStar;

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBizRegion(String bizRegion) {
        this.bizRegion = bizRegion;
    }

    public void setHotelStar(String[] hotelStar) {
        this.hotelStar = hotelStar;
    }

    public void setKeyWords(String[] keyWords) {
        this.keyWords = keyWords;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setMinScore(double minScore) {
        this.minScore = minScore;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }


    public String getBizRegion() {
        return bizRegion;
    }

    public String getAddress() {
        return address;
    }

    public String[] getHotelStar() {
        return hotelStar;
    }

    public String[] getKeyWords() {
        return keyWords;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public double getMinScore() {
        return minScore;
    }


}
