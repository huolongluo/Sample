package com.example.huolongluo.sample.huolongluo.share;

import com.cocosw.favor.AllFavor;
import com.cocosw.favor.Commit;
import com.cocosw.favor.Default;
import com.cocosw.favor.Favor;

/**
 * Created by 火龙裸先生 on 2017/8/11 0011.
 * <p>
 * 数据保存在sharepreference中
 */

@AllFavor
public interface ShareData {

    String USER_NAME = "userName";
    String PASS_WORD = "passWord";

    @Commit
    @Favor(USER_NAME)
    void setUserName(String userName);

    @Default("")
    @Favor(USER_NAME)
    String getUserName();

    @Commit
    @Favor
    void setPassWord(String passWord);

    @Default("")
    @Favor(PASS_WORD)
    String getPassWord();
}
