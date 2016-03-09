package com.fansen.phr.entity;

import android.text.format.Time;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by faen on 2016/3/9.
 */
public class TimeSlot implements Serializable{
    private int _id;
    private String name;
    private Date startTime;
    private Date endTime;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
