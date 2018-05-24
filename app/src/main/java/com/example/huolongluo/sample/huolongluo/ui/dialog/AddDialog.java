package com.example.huolongluo.sample.huolongluo.ui.dialog;

import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.huolongluo.sample.R;
import com.example.huolongluo.sample.huolongluo.base.BaseDialog;
import com.example.huolongluo.sample.util.DateUtils;
import com.example.huolongluo.sample.widget.FilterEditText;
import com.example.huolongluo.sample.widget.RoundTextView;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 添加商品
 * <p>
 * Created by SLAN on 2016/11/3.
 */

public class AddDialog extends BaseDialog
{
    private long goodsNums = 1; // 默认添加一件商品

    @Override
    protected int getContentViewId()
    {
        if (currentStyle == ADD_GOODS)
        {
            return R.layout.dialog_add;
        }
        else if (currentStyle == SHOW_TIME)
        {
            return R.layout.dialog_date_pick;
        }
        else
        {
            return 0;
        }
    }

    @Override
    protected void initViewsAndEvents(View rootView)
    {

        if (currentStyle == ADD_GOODS)
        {
            addGoods();
        }
        else if (currentStyle == SHOW_TIME)
        {
            showTimeSelect();
        }
        else
        {
            // do nothing
        }
    }

    /**
     * 登录前提示
     */
    private void loginBeforeTips()
    {
        initShowStyle(mContext.getResources().getDimensionPixelSize(R.dimen.x640), WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER, R.style
                .dialog_animation2);
        RoundTextView rtv_ok = (RoundTextView) findViewById(R.id.rtv_ok);

        eventClick(rtv_ok, 3000).subscribe(o ->
        {
            dismiss();
        });
    }

    /**
     * 注册时，核对SN号 信息提示
     */
    private void checkSnTips()
    {
        initShowStyle(mContext.getResources().getDimensionPixelSize(R.dimen.x640), WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER, R.style
                .dialog_animation2);
        TextView fet_name = (TextView) findViewById(R.id.fet_name);
        RoundTextView rtv_cancel = (RoundTextView) findViewById(R.id.rtv_cancel);
        RoundTextView rtv_ok = (RoundTextView) findViewById(R.id.rtv_ok);

        String contentTips = getArguments().getString("tips");
        fet_name.setText(contentTips);

        eventClick(rtv_cancel, 3000).subscribe(o ->
        {
            dismiss();
        });

        eventClick(rtv_ok, 3000).subscribe(o ->
        {
            if (TextUtils.equals(getArguments().getString("checkType"), "10005"))
            {
                dismiss();
            }
            else if (TextUtils.equals(getArguments().getString("checkType"), "10006"))
            {
                dismiss();
            }
        });
    }

    /**
     * 注册成功提示
     */
    private void registerTips()
    {
        initShowStyle(mContext.getResources().getDimensionPixelSize(R.dimen.x640), WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER, R.style
                .dialog_animation2);

        new Handler().postDelayed(() ->
        {
            dismiss();
        }, 3000);

//        TextView fet_name = (TextView) findViewById(R.id.fet_name);
//
//        String contentTips = getArguments().getString("tips");
//        fet_name.setText(contentTips);
    }

    /**
     * 温馨提示
     */
    private void clickTips()
    {
        initShowStyle(mContext.getResources().getDimensionPixelSize(R.dimen.x640), WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER, R.style
                .dialog_animation2);
        TextView fet_name = (TextView) findViewById(R.id.fet_name);
        RoundTextView rtv_cancel = (RoundTextView) findViewById(R.id.rtv_cancel);
        RoundTextView rtv_ok = (RoundTextView) findViewById(R.id.rtv_ok);

        String contentTips = getArguments().getString("tips");
        fet_name.setText(contentTips);

        rtv_cancel.setVisibility(View.GONE);//隐藏取消按钮

        eventClick(rtv_ok, 3000).subscribe(o ->
        {
            dismiss();
        });
    }


    /**
     * 添加商品
     */
    @SuppressWarnings("unchecked")
    private void addGoods()
    {
        initShowStyle(mContext.getResources().getDimensionPixelSize(R.dimen.x640), WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER, R.style
                .dialog_animation2);

        FilterEditText fet_name = (FilterEditText) findViewById(R.id.fet_name);
        EditText et_price = (EditText) findViewById(R.id.et_price);
        RoundTextView rtv_cancel = (RoundTextView) findViewById(R.id.rtv_cancel);
        RoundTextView rtv_ok = (RoundTextView) findViewById(R.id.rtv_ok);

        fet_name.setOnlySupportCharAndNum(true).isSupportChinese(true).setAllow("/*-_#|$￥");

        et_price.addTextChangedListener(new TextWatcher()
        {
            public void afterTextChanged(Editable edt)
            {
                String temp = edt.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0)
                {
                    return;
                }
                if (temp.length() - posDot - 1 > 2)
                {
                    edt.delete(posDot + 3, posDot + 4);
                }
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3)
            {
            }

            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3)
            {
            }
        });

        eventClick(rtv_cancel, 2000).subscribe(o -> dismiss());


        eventClick(rtv_ok, 3000).subscribe(o ->
        {

            String name = fet_name.getText().toString().trim();
            String price = et_price.getText().toString().trim();

            if (TextUtils.isEmpty(name))
            {
                showMessage("请输入商品名称", 2);
                return;
            }
            if (TextUtils.isEmpty(price) || price.equals("."))
            {
                showMessage("请输入商品价格", 2);
                return;
            }

            dismiss();

        });
    }

    /**
     * 显示时间选择
     */
    private void showTimeSelect()
    {
        initShowStyle(mContext.getResources().getDimensionPixelSize(R.dimen.x640), WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER, R.style
                .dialog_animation2);

        TextView head_time_TV = (TextView) findViewById(R.id.head_time_TV);
        DatePicker dpPicker = (DatePicker) findViewById(R.id.dpPicker);
        TimePicker tp_time = (TimePicker) findViewById(R.id.tp_time);
        tp_time.setVisibility(View.GONE);
        RoundTextView rtv_cancel = (RoundTextView) findViewById(R.id.rtv_cancel);
        RoundTextView rtv_ok = (RoundTextView) findViewById(R.id.rtv_ok);

        //是否使用24小时制  
        tp_time.setIs24HourView(true);
        TimeListener times = new TimeListener();
        tp_time.setOnTimeChangedListener(times);

        if (null == date)
        {
            date = new Date();
        }
        dateBack = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        monthLabel = DateUtils.format(new Date(calendar.getTimeInMillis()), "yyyy-MM-dd"); // 设置默认值
        timeLabel = DateUtils.format(new Date(calendar.getTimeInMillis()), "HH:mm"); // 设置默认值
        head_time_TV.setText(DateUtils.format(date, "yyyy年MM月dd日 EEEE"));

        dpPicker.setMinDate(date.getTime() - 3 * 30 * 24 * 60 * 60 * 1000L);
        dpPicker.setMaxDate(date.getTime());

        dpPicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), (view, year, monthOfYear, dayOfMonth) ->
        {
            // 返回的month范围是: 0-11
            calendar.set(year, monthOfYear, dayOfMonth);
            head_time_TV.setText(DateUtils.format(new Date(calendar.getTimeInMillis()), "yyyy年MM月dd日 EEEE"));
            monthLabel = DateUtils.format(new Date(calendar.getTimeInMillis()), "yyyy-MM-dd"); // 改变后的年月日
        });

        eventClick(rtv_cancel, 2000).subscribe(o -> dismiss());

        eventClick(rtv_ok, 3000).subscribe(o ->
        {

            try
            {
                dateBack = DateUtils.getDate(monthLabel + " " + timeLabel, "yyyy-MM-dd");
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }

            if (null != minTime && dateBack.before(minTime))
            {
                showMessage("截止日期不能小于开始日期", 2);
                return;
            }
            if (null != maxTime && dateBack.after(maxTime))
            {
                showMessage("开始日期不能大于截止日期", 2);
                return;
            }


            dismiss();

        });
    }

    class TimeListener implements TimePicker.OnTimeChangedListener
    {

        /**
         * view 当前选中TimePicker控件
         * hourOfDay 当前控件选中TimePicker 的小时
         * minute 当前选中控件TimePicker  的分钟
         */
        @Override
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
        {
            timeLabel = hourOfDay + ":" + minute;
        }
    }


    //<!-- 时间选择 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<-->
    private Date date;
    private String monthLabel;
    private String timeLabel;
    private Date dateBack;
    private Date maxTime;// 最大开始日期为选中的截止日期
    private Date minTime;// 最小截止日期为选中的开始日期


    public void setMaxTime(Date maxTime)
    {
        this.maxTime = maxTime;
    }

    public void setMinTime(Date minTime)
    {
        this.minTime = minTime;
    }

    /**
     * 进入dialog datePicker上显示的时间
     */
    public void setShowTime(Date date)
    {
        this.date = date;
    }


    //<!-- 退出确定 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>-->

    /**
     * Dialog设置
     */
    private void initShowStyle(int width, int height, int gravity, int style)
    {
        Window window = getDialog().getWindow();
        assert window != null;
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = width;
        lp.height = height;
        lp.gravity = gravity;

        window.setWindowAnimations(style); // 添加动画
        window.setAttributes(lp);
    }

}
