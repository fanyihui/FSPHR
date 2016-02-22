package com.fansen.phr.entity;

/**
 * Created by faen on 2016/2/5.
 */
public enum OrderStatus {
    ACTIVE("ACTIVE"), INACTIVE("INACTIVE"), SUSPENDED("SUSPENDED"), STOPPED("STOPPED");

    private String name;

    private OrderStatus(String status){
        this.name = status;
    }

    public String getName(){
        return name;
    }
}
