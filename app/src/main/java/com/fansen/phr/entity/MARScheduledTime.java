package com.fansen.phr.entity;

import android.text.format.Time;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by faen on 2016/3/9.
 */
public class MARScheduledTime implements Serializable{
    private int _id;
    private String name;
    private Date scheduledTime;

    public Date getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

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


}
