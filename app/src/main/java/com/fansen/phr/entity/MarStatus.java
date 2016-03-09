package com.fansen.phr.entity;

/**
 * Created by faen on 2016/3/9.
 */
public enum MarStatus {
    TAKEN("TAKEN"), POSTPONED("POSTPONED"), UNTAKEN("NONTAKEN");

    private String name;

    private MarStatus(String status){
        this.name = status;
    }

    public String getName(){
        return name;
    }
}
