package com.fansen.phr.utils;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 310078142 on 2015/9/22.
 */
public class TimeFormat {
    public static String DATEFORMAT = "yyyyMMdd";
    public static String TIMEFORMAT = "HH:mm:ss";

    public static String parseTime(Date date){
        if(date == null){
            return "";
        }

        SimpleDateFormat df = new SimpleDateFormat(TIMEFORMAT);
        return df.format(date);
    }

    public static Date formatTime(String time){
        SimpleDateFormat df = new SimpleDateFormat(TIMEFORMAT);
        Date result = null;

        try{
            result = df.parse(time);
        } catch (ParseException pe){
            pe.printStackTrace();
        }

        return result;
    }

    public static String parseDate(Date date){
        if(date == null || date.equals("")){
            return "";
        }

        SimpleDateFormat df = new SimpleDateFormat(DATEFORMAT);
        return df.format(date);
    }

    public static Date format(String date){
        SimpleDateFormat df = new SimpleDateFormat(DATEFORMAT);
        Date result = null;

        try{
            result = df.parse(date);
        } catch (ParseException pe){
            pe.printStackTrace();
        }

        return result;
    }

    public static String getYear(Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy");

        return df.format(date);
    }

    public static String getMonth(Date date){
        SimpleDateFormat df = new SimpleDateFormat("MM");

        return df.format(date);
    }

    public static String getDay(Date date){
        SimpleDateFormat df = new SimpleDateFormat("dd");

        return df.format(date);
    }
}
