package com.Jogoo.fornote.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeClass  {

    public static String getCurrentTimeStamp(){
        // Must BE lower case to Support Lower Version
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-yyyy",Locale.getDefault());
            return simpleDateFormat.format(new Date());

        }catch (Exception e){
            return null;
        }
    }
    public static String getMonthToAbbreviate(String monthNumber){
        switch(monthNumber){
            case "01":
            return "Jan";
            case "02":
                return "Feb";
            case "03":
                return "Mar";
            case "04":
                return "Apr";
            case "05":
                return "May";
            case "06":
                return "Jun";
            case "07" :
                return "Jul";
            case "08":
                return "Aug";
            case "09":
                return "Sep";
            case "10":
                return "Oct";
            case "11":
                return "Nov";
            case "12":
                return "Dec";
            default:
                return "Error";
        }
    }


    public static String giveDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault());
        return sdf.format(cal.getTime());
    }
}
