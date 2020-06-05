package com.example.hotel.enums;

public enum BreakFast {
    None("无"),
    Porridge("粥"),
    Noodles("面条");

    private final String value;

    BreakFast(String value) { this.value = value; }

    @Override
    public String toString() { return value; }
}
