package com.example.aidanci.Date;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GetNowDate {
    static public String DoIt(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        return time;
    }
}
