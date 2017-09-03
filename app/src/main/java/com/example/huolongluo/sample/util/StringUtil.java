package com.example.huolongluo.sample.util;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by SLAN on 2016/11/28.
 */

public class StringUtil
{
    /**
     * 判断手机号码是否合理
     *
     * @param phoneNums
     */
    public static boolean judgePhoneNums(String phoneNums)
    {
        return isMatchLength(phoneNums, 11) && isMobileNO(phoneNums);
    }

    /**
     * 判断一个字符串的位数
     *
     * @param str
     * @param length
     * @return
     */
    private static boolean isMatchLength(String str, int length)
    {
        return !str.isEmpty() && str.length() == length;
    }

    /**
     * 验证手机格式
     */
    private static boolean isMobileNO(String mobileNums)
    {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
         * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         */
        String telRegex = "[1][34578]\\d{9}";// 
        // "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return !TextUtils.isEmpty(mobileNums) && mobileNums.matches(telRegex);
    }

    public static int getLengthForInputStr(String inputStr)
    {

        int orignLen = inputStr.length();

        int resultLen = 0;

        String temp = null;

        for (int i = 0; i < orignLen; i++)
        {

            temp = inputStr.substring(i, i + 1);

            try
            {
                // 3 bytes to indicate chinese word,1 byte to indicate english word ,in utf-8 encode
                if (temp.getBytes("utf-8").length == 3)
                {
                    resultLen += 2;
                }
                else
                {
                    resultLen++;
                }
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
        }
        return resultLen;
    }
}

