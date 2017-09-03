package com.example.huolongluo.sample.huolongluo.share;

/**
 * Created by 火龙裸先生 on 2017/8/11 0011.
 */

public class Event {

    public static class CardPayCallBack {
        public String payType;
        public String data;
        public boolean isCallBackToService;

        public CardPayCallBack(String payType, String data, boolean isCallBackToService) {
            this.payType = payType;
            this.data = data;
            this.isCallBackToService = isCallBackToService;
        }
    }

    public static class RefreshOrderNotPay {
    }
}
