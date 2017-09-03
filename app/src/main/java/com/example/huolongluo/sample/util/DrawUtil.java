package com.example.huolongluo.sample.util;

/**
 * 绘制工具类
 */
public class DrawUtil
{
    public static float sDensity = 1.0f;
    public static int sWidthPixels;
    public static int sHeightPixels;


    /**
     * dip/dp转像素
     * <p/>
     * dip或 dp大小
     *
     * @return 像素值
     */
    public static int dip2px(float dipVlue)
    {
        return (int) (dipVlue * sDensity + 0.5f);
    }

    /**
     * 像素转dip/dp
     *
     * @param pxValue 像素大小
     * @return dip值
     */
    public static int px2dip(float pxValue)
    {
        final float scale = sDensity;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp 转 px
     *
     * @param spValue sp大小
     * @return 像素值
     */
    public static int sp2px(float spValue)
    {
        final float scale = sDensity;
        return (int) (scale * spValue);
    }

    /**
     * px转sp
     *
     * @param pxValue 像素大小
     * @return sp值
     */
    public static int px2sp(float pxValue)
    {
        final float scale = sDensity;
        return (int) (pxValue / scale);
    }
}
