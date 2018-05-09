package com.mark.app.mkpaysample;

import android.app.Application;

import com.mark.app.mkpay.wechat.MkWechatPay;

import timber.log.Timber;

/**
 * Created by zhuwh on 2017/9/28.
 */

public class App extends Application {

    private static App s_app;

    @Override
    public void onCreate() {
        super.onCreate();
        s_app = this;
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            //        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        }
        MkWechatPay.WX_APPID = "123456";

    }

    public static App get(){
        return s_app;
    }

    PdMService mPdMService;

    public PdMService getPdmService() {
        if (mPdMService==null){
            mPdMService = PdMService.Creator.newService();
        }
        return mPdMService;
    }
}
