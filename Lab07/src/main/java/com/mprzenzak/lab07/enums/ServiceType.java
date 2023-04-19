package com.mprzenzak.lab07.enums;

public enum ServiceType {
    OPTICAL_FIBER_100MB, OPTICAL_FIBER_500MB, OPTICAL_FIBER_1GBIT, INTERNET_WITH_TV, INTERNET_WITH_NETFLIX;

    public String getLabel() {
        return switch (this) {
            case OPTICAL_FIBER_100MB -> "Światłowód 100Mb/s";
            case OPTICAL_FIBER_500MB -> "Światłowód 500Mb/s";
            case OPTICAL_FIBER_1GBIT -> "Światłowód 1 Gb/s";
            case INTERNET_WITH_TV -> "Internet z telewizją";
            case INTERNET_WITH_NETFLIX -> "Internet z Netflix";
        };
    }
}
