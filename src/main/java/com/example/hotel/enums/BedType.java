package com.example.hotel.enums;

public enum BedType {
    BigBed("单人床"),
    DoubleBed("双人床"),
    Family("三人床"),
    Luxury("奢华大床");

    private final String value;

    BedType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
