package com.mark.app.mkpay.wechat;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v4.content.LocalBroadcastManager;

import com.mark.app.mkpay.core.MkPayCallback;
import com.mark.app.mkpay.core.MkPayInf;
import com.mark.app.mkpay.core.MkPayResult;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import timber.log.Timber;

/**
 * Created by zhuwh on 2017/9/29.
 */

public class MkWechatPay implements MkPayInf<String> {

    public static final String WX_PAY_RESULT_ACTION = "_WX_PAY_RESULT_ACTION";
    public static final String WX_PAY_RESULT_KEY = "_WX_PAY_RESULT_KEY";

    public static final String WX_APPID_KEY = "wechat_app_id";

    private BroadcastReceiver mWxPayResultReceiver;
    private IWXAPI mWxApi; //微信API
    public static String WX_APPID;

    private MkPayCallback mCallBack;

    public static final String APPID_KEY = "wechat_app_id";

    private boolean isInit = false;

    /**
     * 只能调用一次
     * @param context
     */
    @Override
    public void setContext(Context context) {
        if (!isInit) {
            try {
                ApplicationInfo appInfo = context.getPackageManager()
                        .getApplicationInfo(context.getPackageName(),
                                PackageManager.GET_META_DATA);
                WX_APPID=appInfo.metaData.getString(APPID_KEY);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            if (WX_APPID==null){
                throw new IllegalArgumentException("WX_APPID 不能为空");
            }
            mWxApi = WXAPIFactory.createWXAPI(context, null);
            mWxApi.registerApp(WX_APPID);
            mWxPayResultReceiver = new WxPayResultReceiver();

            LocalBroadcastManager localBroadcaseManager = LocalBroadcastManager.getInstance(context.getApplicationContext());
            IntentFilter intentFilter = new IntentFilter(WX_PAY_RESULT_ACTION);
            localBroadcaseManager.registerReceiver(mWxPayResultReceiver, intentFilter);
            isInit = true;
        }
    }

    /**
     * @param activity
     * @param payInfo  支付信息 Json String
     * @param callback
     */
    @Override
    public void pay(final Activity activity, final String payInfo, final MkPayCallback callback) {
        mCallBack = callback;
        try {
            JSONObject jsonObject = new JSONObject(payInfo);
            PayReq req = new PayReq();
            req.appId = WX_APPID;
            req.partnerId = jsonObject.getString("partnerid");
            req.prepayId = jsonObject.getString("prepayid");
            req.nonceStr = jsonObject.getString("noncestr");
            req.timeStamp = jsonObject.getString("timestamp");
            req.packageValue = jsonObject.getString("package");
            req.sign = jsonObject.getString("sign");
            mWxApi.sendReq(req);
        } catch (JSONException e) {
//            e.printStackTrace();
            Timber.e("payInfo 数据格式不正确");
            MkPayResult result = new MkPayResult(MkPayResult.PAY_STATE_ERROR, "数据格式不正确");
            if (mCallBack!=null){
                mCallBack.onPayResult(result);
                mCallBack = null;
            }
        }
    }

    private boolean isWXAppInstalledAndSupported(IWXAPI api) {
        return api.isWXAppInstalled()
                && api.isWXAppSupportAPI();
    }

    public class WxPayResultReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(WX_PAY_RESULT_ACTION)) {    //动作检测
                MkPayResult result = intent.getParcelableExtra(WX_PAY_RESULT_KEY);
                if (mCallBack != null) {
                    mCallBack.onPayResult(result);
                    mCallBack = null;
                }
            }
        }
    }

}
