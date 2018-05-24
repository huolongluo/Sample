package com.example.huolongluo.sample.util;

import android.text.InputFilter;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;

/**
 * Created by 火龙裸 on 2016/12/14.
 */

public class NumberFormat
{
    public static String dTs(Double number)
    {
        return new java.text.DecimalFormat("#0.00").format(number);
    }

    public static String dTs2(Double number)
    {
        return new java.text.DecimalFormat("#0.0").format(number);
    }

    /**
     * 设置字符过滤只允许输入小数点后两位
     *
     * @param editText editText
     */
    public static void filteredTwoDecimal(EditText editText)
    {
        //  设置字符过滤
        editText.setFilters(new InputFilter[]{(source, start, end, dest, dstart, dend) ->
        {
            if (source.equals(".") && dest.toString().length() == 0)
            {
                return "0.";
            }
            
            return null;
        }});


        RxTextView.afterTextChangeEvents(editText).subscribe(event -> {
            String temp = event.view().getText().toString();
            int posDot = temp.indexOf(".");
            if (posDot <= 0) return;
            if (temp.length() - posDot - 1 > 2)
            {
                event.editable().delete(posDot + 3, posDot + 4);
            }
        });
    }

    public static boolean checkInputCorrect(String input)
    {
        return (input.equals(".") || input.equals(".0") || input.equals(".00") || input.equals("0") || input.equals("0.") || input.equals("0.0") || input
                .equals("0.00"));
    }
}
