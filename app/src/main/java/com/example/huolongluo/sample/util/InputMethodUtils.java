package com.example.huolongluo.sample.util;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class InputMethodUtils
{
    private static InputMethodManager imm;

    // 显示输入法
    public static void show(Context context, View focusView)
    {
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(focusView, InputMethodManager.SHOW_FORCED);
    }

    // 隐藏输入法
    public static void hide(Context context)
    {
        View view = ((Activity) context).getWindow().peekDecorView();
        if (view != null && view.getWindowToken() != null)
        {
            imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    // 隐藏输入法
    public static void hide(Activity context, View view)
    {
        if (view != null && view.getWindowToken() != null)
        {
            imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    // 调用该方法；键盘若显示则隐藏; 隐藏则显示
    public static void toggle(Context context)
    {
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    // 判断InputMethod的当前状态
    public static boolean isShow(Context context, View focusView)
    {
        Object obj = context.getSystemService(Context.INPUT_METHOD_SERVICE);
        System.out.println(obj);
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean bool = imm.isActive(focusView);
        List<InputMethodInfo> mInputMethodProperties = imm.getEnabledInputMethodList();

        // Configuration cfg = context.getResources().getConfiguration();
        // int ii = cfg.hardKeyboardHidden;
        // int or = cfg.orientation;
        // Log.e("xxxxxxxxxxxxxx", ii+" "+or);
        final int N = mInputMethodProperties.size();
        for (int i = 0; i < N; i++)
        {
            InputMethodInfo imi = mInputMethodProperties.get(i);
            if (imi.getId().equals(Settings.Secure.getString(context.getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD)))
            {
                // imi contains the information about the keyboard you are using
                break;
            }
        }
        return bool;
    }

    // 隐藏系统键盘  
    public static void hideSoftInputMethod(EditText ed)
    {
        ((Activity) ed.getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        int currentVersion = android.os.Build.VERSION.SDK_INT;
        String methodName = null;
        if (currentVersion >= 16)
        {
            // 4.2  
            methodName = "setShowSoftInputOnFocus";
        }
        else if (currentVersion >= 14)
        {
            // 4.0  
            methodName = "setSoftInputShownOnFocus";
        }

        if (methodName == null)
        {
            ed.setInputType(InputType.TYPE_NULL);
        }
        else
        {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            try
            {
                setShowSoftInputOnFocus = cls.getMethod(methodName, boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(ed, false);
            }
            catch (NoSuchMethodException e)
            {
                ed.setInputType(InputType.TYPE_NULL);
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                // TODO Auto-generated catch block  
                e.printStackTrace();
            }
            catch (IllegalArgumentException e)
            {
                // TODO Auto-generated catch block  
                e.printStackTrace();
            }
            catch (InvocationTargetException e)
            {
                // TODO Auto-generated catch block  
                e.printStackTrace();
            }
        }
    }
}