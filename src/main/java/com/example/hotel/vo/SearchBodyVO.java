package com.example.hotel.vo;

public class SearchBodyVO {


    private String chechInDate = null;
    private String checkOutDate = null;
    private String address = null;
    private String bizRegion = null;
    private String hotelStar[] = null;  //酒店星级种类为长度为3的bitmap , 类型为String
    private String keyWords[];
    private double maxPrice = -1;
    private double minScore = -1;

    public void setChechInDate(String chechInDate) {
        this.chechInDate = chechInDate;
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

    public String getChechInDate() {
        return chechInDate;
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
