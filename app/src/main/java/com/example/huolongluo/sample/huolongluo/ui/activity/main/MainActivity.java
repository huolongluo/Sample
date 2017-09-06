package com.example.huolongluo.sample.huolongluo.ui.activity.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.huolongluo.sample.R;
import com.example.huolongluo.sample.huolongluo.base.BaseActivity;
import com.example.huolongluo.sample.huolongluo.bean.ItemBean;
import com.example.huolongluo.sample.huolongluo.ui.adapter.MyAdapter;
import com.example.huolongluo.sample.util.L;
import com.example.huolongluo.sample.util.ToastSimple;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * <p>
 * Created by 火龙裸 on 2017/8/10.
 */

public class MainActivity extends BaseActivity implements MainContract.View
{

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @Inject
    MainPresent mainPresent;

    private List<ItemBean> datas;
    private MyAdapter myAdapter;

    private void initData()
    {
        if (datas == null)
        {
            datas = new ArrayList<>();
            for (int i = 0; i < 30; i++)
            {
                datas.add(new ItemBean("标题" + i, "内容" + i));
            }
        }
    }

    private void initView()
    {
        myAdapter = new MyAdapter(this, datas, R.layout.item);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener((itemView, viewType, position) ->
        {
            ToastSimple.show("点击了" + datas.get(position).getTitle(), 2);
            L.e("==================================点击了=" + datas.get(position).getTitle());
            EventBus.getDefault().post("hello");
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(String a)
    {
        ToastSimple.show(a, 3);
    }


    /******************************************************************************/
    @Override
    protected int getContentViewId()
    {
        return R.layout.activity_main;
    }

    @Override
    protected void injectDagger()
    {
        activityComponent().inject(this);
        mainPresent.attachView(this);
    }

    @Override
    protected void initViewsAndEvents()
    {
        initData();
        initView();
        Map<String, String> map  = new HashMap<>();
        mainPresent.getTime(map);
    }

    @Override
    public void setTime(String dateData)
    {

    }
}
