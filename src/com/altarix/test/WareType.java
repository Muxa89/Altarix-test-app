package com.altarix.test;

/**
 * Created by mikhail on 22.07.14.
 */
public enum WareType {
    APPLIANCES("Бытовая техника"), PHONE("Телефон"), CAR("Автомобиль");

    private String name;

    WareType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
