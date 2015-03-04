package com.devindi.vk.messenger.demo.tools;

import android.text.format.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringHelper {

    public static String format(long date)
    {
        DateFormat df;
        /*if(DateUtils.isToday(date))
            df = new SimpleDateFormat("HH:mm");
        else
            df = new SimpleDateFormat("MM/dd/yyyy HH:mm");*/
        df = new SimpleDateFormat("HH:mm");
        return df.format(new Date(date));
    }
}
