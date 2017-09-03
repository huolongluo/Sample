package com.example.huolongluo.sample.util;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by SLAN on 2016/11/10.
 */

public class DateUtils
{
    public static Date date = null;
    public static DateFormat dateFormat = null;
    public static Calendar calendar = null;

    /**
     * 功能描述：格式化日期
     *
     * @param dateStr String 字符型日期
     * @param format  String 格式
     * @return Date 日期
     */
    public static Date parseDate(String dateStr, String format)
    {
        try
        {
            dateFormat = new SimpleDateFormat(format);
            String dt = dateStr.replaceAll("-", "/");
            if ((!dt.equals("")) && (dt.length() < format.length()))
            {
                dt += format.substring(dt.length()).replaceAll("[YyMmDdHhSs]", "0");
            }
            date = (Date) dateFormat.parse(dt);
        }
        catch (Exception e)
        {
        }
        return date;
    }

    /**
     * 功能描述：格式化日期
     *
     * @param dateStr String 字符型日期：YYYY-MM-DD 格式
     * @return Date
     */
    public static Date parseDate(String dateStr)
    {
        return parseDate(dateStr, "yyyy/MM/dd");
    }


    /**
     * 将日期以yyyy-MM-dd HH:mm:ss格式化
     *
     * @param dateL 日期
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatDateTime(long dateL, String formater)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(formater);
        return sdf.format(new Date(dateL));
    }

    /**
     * 功能描述：格式化输出日期
     *
     * @param date   Date 日期
     * @param format String 格式
     * @return 返回字符型日期
     */
    public static String format(Date date, String format)
    {
        String result = "";
        try
        {
            if (date != null)
            {
                dateFormat = new SimpleDateFormat(format);
                result = dateFormat.format(date);
            }
        }
        catch (Exception e)
        {
        }
        return result;
    }

    /**
     * 功能描述：
     *
     * @param date Date 日期
     * @return
     */
    public static String formatToDay(Date date)
    {
        return format(date, "yyyy/MM/dd");
    }

    /**
     * 功能描述：
     *
     * @param date Date 日期
     * @return
     */
    public static String formatToMillisecond(Date date)
    {
        return format(date, "yyyy/MM/dd HH:mm:ss");
    }


    public static Date getDate(String DateString, String format) throws ParseException
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(DateString);
    }

    /**
     * @param time   long 时间
     * @param format 格式
     * @return 格式化时间
     */
    public static String format(Long time, String format)
    {
        return format(new Date(time), format);
    }


    /*获取系统时间 格式为："yyyy/MM/dd "*/
    public static String getCurrentDate(long time, String formater)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(formater);
        return sdf.format(new Date(time));
    }
}
