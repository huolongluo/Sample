package com.example.huolongluo.sample.huolongluo.net.okhttp;

import com.example.huolongluo.sample.huolongluo.net.BaseResponse;
import com.example.huolongluo.sample.huolongluo.net.UrlConstants;

import java.util.Map;

import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * ApiService
 * <p/>
 * Created by 火龙裸先生 on 2017/08/11.
 */
public interface ApiService {
    /**
     * 注册
     *
     * @param options
     * @return
     */
    @POST(UrlConstants.REGISTER)
    Observable<BaseResponse> register(@QueryMap Map<String, String> options, @Query("deviceId") String deviceId);

    /**
     * 忘记密码
     *
     * @param newPassword 新密码
     * @param smsCode     短信验证码
     * @param userName    用户名，在登录接口中返回
     * @return
     */
    @POST(UrlConstants.UP_PASSWORD)
    Observable<BaseResponse> upPassword(@Query("newPassword") String newPassword, @Query("smsCode") String smsCode, @Query("userName") String userName);

    /**
     * 修改密码
     *
     * @param confirmPwd 确认密码
     * @param newPwd     新密码
     * @param oldPwd     旧密码
     * @param userName   用户名，在登录接口中返回
     * @return
     */
    @POST(UrlConstants.MODIFY_PASSWORD)
    Observable<BaseResponse> modifyPassword(@Query("confirmPwd") String confirmPwd, @Query("newPwd") String newPwd, @Query("oldPwd") String oldPwd, @Query
            ("userName") String userName);

    /**
     * 忘记密码发送短信
     *
     * @param userName
     * @return
     */
    @POST(UrlConstants.SEND_SMS)
    Observable<BaseResponse> sendSms(@Query("userName") String userName);

    /**
     * 注册发送短信
     *
     * @param phone
     * @return
     */
    @POST(UrlConstants.REGISTER_SEND_SMS)
    Observable<BaseResponse> registerSendSms(@Query("phone") String phone);

}
