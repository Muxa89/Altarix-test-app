package com.altarix.test;

/**
 * Created by mikhail on 22.07.14.
 */
public class Ware {

    private String name;
    private WareType type;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WareType getType() {
        return type;
    }

    public void setType(WareType type) {
        this.type = type;
    }

    private int count;

    public Ware(String name, WareType type, int count) {
        this.name = name;
        this.type = type;
        this.count = count;
    }


}
