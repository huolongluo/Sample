/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.example.huolongluo.sample.util;


import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 封装各种生成唯一性ID算法的工具类.
 *
 * @author Dean
 */
public class Identities
{

    private static SecureRandom random = new SecureRandom();

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间有-分割.
     */
    public static String uuid()
    {
        return UUID.randomUUID().toString();
    }

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid2()
    {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 使用SecureRandom随机生成Long.
     */
    public static long randomLong()
    {
        return Math.abs(random.nextLong());
    }

    /**
     * 使用SecureRandom随机生成int.
     */
    public static int randomInteger(int min, int max)
    {
        return random.nextInt(max) % (max - min + 1) + min;
    }

    public static String randomString()
    {
        return RandomStringUtils.random(12, true, true);
    }

    public static String randomFourNumberString()
    {
        return RandomStringUtils.random(4, false, true);
    }

    public static String getSku_id()
    {
        return System.currentTimeMillis() + Identities.randomString();
    }

    /**
     * 生成日期 yyyymmddhhmmss + 4位随机数字的 id
     *
     * @return
     */
    public static String getMain_id()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddhhmmss");
        return sdf.format(new Date()) + randomFourNumberString();
    }


    /**
     * 获取时间戳的后9位
     *
     * @return
     */
    public static String getTimeStamp()
    {
        Calendar calendar = Calendar.getInstance();
        String time = (calendar.getTimeInMillis() / 1000 + "");
        String lastNine = time.substring(time.length() - 9, time.length());
        return lastNine;
    }

    /**
     * 字典
     */
    private static String[] chars =
            new String[]{"F", "E", "H", "G", "I", "J", "K", "L", "N", "M", "P", "O", "Q", "R", "S", "T", "2", "1", "4", "3", "5", "7", "6", "9", "8", "A", "B",
                    "C", "D", "V", "U", "W", "Y", "X", "Z"};

    public static AtomicInteger atomicInteger;

    static
    {
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(Calendar.MILLISECOND);
        atomicInteger = new AtomicInteger(i);
    }

    /**
     * 获取int类型的值
     *
     * @return
     */
    private static synchronized int getId()
    {
        if (atomicInteger.get() > 1024)
        {
            atomicInteger.set(0);
        }

        int value = atomicInteger.getAndIncrement();
        return value;
    }

    /**
     * 获取房源的序号
     *
     * @return
     */
    public static String getAptId()
    {
        Long idx = 0x3FFFFFFF & Long.valueOf(getTimeStamp());
        String outChars = "";
        for (int k = 0; k < 6; k++)
        {
            int index = (int) (0x1F & idx);
            outChars += chars[index];
            idx = idx >> 5;
        }

        int value = getId();
        outChars += chars[0x1F & value];
        value = value >> 5;
        outChars += chars[0x1F & value];
        return outChars;
    }

    public static void main(String[] args)
    {

        Thread thread = new Thread(runnable);
        Thread thread2 = new Thread(runnable2);

        thread.start();
        thread2.start();

        System.out.println(getMain_id());
    }

    static Runnable runnable = new Runnable()
    {
        public void run()
        {
            for (int i = 0; i < 1000; i++)
            {

                getAptId();
                try
                {
                    Thread.sleep(100);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    };

    static Runnable runnable2 = new Runnable()
    {
        public void run()
        {
            for (int i = 0; i < 1000; i++)
            {

                getAptId();
                try
                {
                    Thread.sleep(100);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    };
}
