package com.example.huolongluo.sample.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.example.huolongluo.sample.base.s;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Desction:Apk工具
 * Date:15/9/17 下午5:06
 */
public class ApkUtils
{

    /**
     * 安装一个apk文件
     *
     * @param context
     * @param uriFile
     */
    public static void install(Context context, File uriFile)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(uriFile), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 卸载一个app
     *
     * @param context
     * @param packageName
     */
    public static void uninstall(Context context, String packageName)
    {
        //通过程序的包名创建URI
        Uri packageURI = Uri.parse("package:" + packageName);
        //创建Intent意图
        Intent intent = new Intent(Intent.ACTION_DELETE, packageURI);
        //执行卸载程序
        context.startActivity(intent);
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName 应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName)
    {
        // 获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        // 用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null)
        {
            for (int i = 0; i < packageInfos.size(); i++)
            {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 从apk中获取版本信息
     *
     * @param context
     * @param channelPrefix
     * @return
     */
    public static String getChannelFromApk(Context context, String channelPrefix)
    {
        //从apk包中获取
        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        //默认放在meta-inf/里， 所以需要再拼接一下
        String key = "META-INF/" + channelPrefix;
        String ret = "";
        ZipFile zipfile = null;
        try
        {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements())
            {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.startsWith(key))
                {
                    ret = entryName;
                    break;
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (zipfile != null)
            {
                try
                {
                    zipfile.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        String[] split = ret.split(channelPrefix);
        String channel = "";
        if (split != null && split.length >= 2)
        {
            channel = ret.substring(key.length());
        }
        return channel;
    }

    public static String getVersionName()
    {
        PackageInfo pi = getPackageInfo();
        if (null == pi)
        {
            return "";
        }

        String versionName = pi.versionName;
        if (versionName == null || versionName.length() <= 0)
        {
            return "";
        }
        return versionName;
    }

    public static String getVersionCode()
    {
        PackageInfo pi = getPackageInfo();
        if (null == pi)
        {
            return "";
        }

        return String.valueOf(pi.versionCode);
    }

    // ---get the package info---
    private static PackageInfo getPackageInfo()
    {
        PackageInfo pi = null;
        try
        {
            PackageManager pm = s.app().getPackageManager();
            pi = pm.getPackageInfo(s.app().getPackageName(), 0);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return pi;
    }
}