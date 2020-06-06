package com.example.hotel.vo.coupon;

public class CouponVO {
    private Integer id;
    private String description;
    private Integer status;
    private String name;
    private Integer type;
    private Integer srcId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    /**
     * 1生日特惠 2多间特惠 3满减优惠 4限时优惠
     */
    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSrcId() {
        return srcId;
    }

    public void setSrcId(Integer id) {
        this.srcId = id;
    }
}
