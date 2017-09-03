package com.example.huolongluo.sample.huolongluo.share;

import com.cocosw.favor.FavorAdapter;
import com.example.huolongluo.sample.base.s;

/**
 * Created by 火龙裸先生 on 2017/8/11 0011.
 */

public class Share {
    public static ShareData get() {
        return new FavorAdapter.Builder(s.app()).build().create(ShareData.class);
    }
}
