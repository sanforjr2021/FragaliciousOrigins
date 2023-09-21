package com.github.sanforjr2021.util.time;

public enum TimeCycle {
    SUNRISE(23000),
    DAY(0),
    SUNSET(12000),
    NIGHT(13000);
    private int time;

    TimeCycle(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }
}
