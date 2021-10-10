package com.example.hotel.enums;

public enum RoomType {
    BigBed("大床房"),
    DoubleBed("双床房"),
    Family("家庭房"),
    Luxury("豪华套房");

    private final String value;

    RoomType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static String getType(String value){
        switch (value) {
            case "大床房":
                return "BigBed";
            case "双床房":
                return "DoubleBed";
            case "家庭房":
                return "Family";
            case "豪华套房":
                return "Luxury";
        }
        return null;
    }

}
