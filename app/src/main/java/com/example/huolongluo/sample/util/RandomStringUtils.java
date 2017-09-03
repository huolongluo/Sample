//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.huolongluo.sample.util;

import java.util.Random;

public class RandomStringUtils
{
    private static final Random RANDOM = new Random();

    public RandomStringUtils()
    {
    }

    public static String random(int count)
    {
        return random(count, false, false);
    }

    public static String randomAscii(int count)
    {
        return random(count, 32, 127, false, false);
    }

    public static String randomAlphabetic(int count)
    {
        return random(count, true, false);
    }

    public static String randomAlphanumeric(int count)
    {
        return random(count, true, true);
    }

    public static String randomNumeric(int count)
    {
        return random(count, false, true);
    }

    public static String random(int count, boolean letters, boolean numbers)
    {
        return random(count, 0, 0, letters, numbers);
    }

    public static String random(int count, int start, int end, boolean letters, boolean numbers)
    {
        return random(count, start, end, letters, numbers, (char[]) null);
    }

    public static String random(int count, int start, int end, boolean letters, boolean numbers, char[] set)
    {
        if (start == 0 && end == 0)
        {
            end = 122;
            start = 32;
            if (!letters && !numbers)
            {
                start = 0;
                end = 2147483647;
            }
        }

        StringBuffer buffer = new StringBuffer();
        int gap = end - start;

        while (true)
        {
            while (count-- != 0)
            {
                char ch;
                if (set == null)
                {
                    ch = (char) (RANDOM.nextInt(gap) + start);
                }
                else
                {
                    ch = set[RANDOM.nextInt(gap) + start];
                }

                if (letters && numbers && Character.isLetterOrDigit(ch) || letters && Character.isLetter(ch) || numbers && Character.isDigit(ch) || !letters 
                        && !numbers)
                {
                    buffer.append(ch);
                }
                else
                {
                    ++count;
                }
            }

            return buffer.toString();
        }
    }

    public static String random(int count, String set)
    {
        return random(count, set.toCharArray());
    }

    public static String random(int count, char[] set)
    {
        return random(count, 0, set.length - 1, false, false, set);
    }
}
