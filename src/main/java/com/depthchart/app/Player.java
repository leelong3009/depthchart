package com.depthchart.app;

import com.depthchart.enums.BasePositionEnum;

public class Player<T extends BasePositionEnum> {
    private int id;

    private String name;

    private T position;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getPosition() {
        return position;
    }

    public void setPosition(T position) {
        this.position = position;
    }

    public Player(int id, String name, T position) {
        this.id = id;
        this.name = name;
        this.position = position;
    }
}
