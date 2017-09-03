package com.example.huolongluo.sample.huolongluo.net;

/**
 * UrlConstants
 * <p>
 * Created by 火龙裸先生 on 2017/08/11.
 */

public class UrlConstants
{
    //        public final static String DOMAIN = "https://120.76.237.235:8080/sjapi/";          // 线上开发服务器1
//    public final static String DOMAIN = "http://testapi.qiyu88.com/";          // 测试预发服务器
    public final static String DOMAIN = "https://api.qiyu88.com/";                     // 线上正式服务器1

    public final static String SEND_SMS = "login/sendSms"; // 获取验证码
    public final static String REGISTER_SEND_SMS = "login/registerSendSms"; // 注册验证码
    public final static String MODIFY_PASSWORD = "login/modifyPassword"; // 修改密码
    public final static String UP_PASSWORD = "login/upPassword"; // 忘记密码
    public final static String REGISTER = "login/register"; // 注册
}
