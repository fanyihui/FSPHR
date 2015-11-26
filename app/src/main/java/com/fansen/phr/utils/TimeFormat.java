package com.fansen.phr.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 310078142 on 2015/9/22.
 */
public class TimeFormat {
    public static String parseDate(Date date, String format){
        if(date == null){
            return "";
        }

        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static Date format(String format, String date){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
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
