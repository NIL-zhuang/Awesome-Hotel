package com.example.hotel.vo;

public class LevelVO {
    private int level;
    private int request;
    private double reduction;

    public double getReduction() {
        return reduction;
    }

    public void setReduction(double reduction) {
        this.reduction = reduction;
    }

    public int getRequest() {
        return request;
    }

    public void setRequest(int request) {
        this.request = request;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public LevelVO(){}

    public LevelVO(int level, int request, double reduction){
        this.level = level;
        this.request = request;
        this.reduction = reduction;
    }
}
