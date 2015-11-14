package com.fansen.phr.entity;

/**
 * Created by 310078142 on 2015/11/14.
 */
public enum ObservationType {
    CURRENT_PROBLEM("CURRENT_PROBLEM"), HISTORICAL_PROBLEM("HISTORICAL_PROBLEM"), PHYSICAL_EXAM("PHYSICAL_EXAM");

    private String name;

    private ObservationType(String name){this.name = name;}

    public String getName(){
        return name;
    }
}
