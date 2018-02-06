package com.mark.app.mkpay.alipay;

import android.app.Activity;
import android.content.Context;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.mark.app.mkpay.core.MkPayCallback;
import com.mark.app.mkpay.core.MkPayInf;
import com.mark.app.mkpay.core.MkPayResult;

import java.util.Map;

import timber.log.Timber;

/**
 * Created by zhuwh on 2017/9/28.
 */

public class MkAlipay implements MkPayInf<String> {

    MkPayCallback mCallback;

    @Override
    public void setContext(Context context) {

    }

    @Override
    public void pay(final Activity activity, final String payInfo, final MkPayCallback callback) {
        Timber.d("<MkAlipay:pay>");
        mCallback = callback;
        new HandlerThread("alipay") {
            @Override
            protected void onLooperPrepared() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(payInfo, true);
                if (result == null) {
                    if (mCallback != null) {
                        MkPayResult payResult = new MkPayResult(MkPayResult.PAY_STATE_FAIL, "无返回内容");
                        mCallback.onPayResult(payResult);
                        mCallback = null;
                    }
                    return;
                }
                Log.i("msp", result.toString());

                String resultInfo = ""; //本次操作返回的结果数据
                int resultStatus = -1;  //状态代码
                for (String key : result.keySet()) {
                    if (TextUtils.equals(key, "resultStatus")) {
                        String rs = result.get(key);
                        try {
                            resultStatus = Integer.valueOf(rs).intValue();
                        } catch (Exception e) {
                        }

                    } else if (TextUtils.equals(key, "result")) {
                        resultInfo = result.get(key);
                    }
                }
                /**
                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                switch (resultStatus) {
                    case 9000: {
                        if (mCallback != null) {
                            MkPayResult payResult = new MkPayResult(MkPayResult.PAY_STATE_SUCCESS, resultInfo);
                            mCallback.onPayResult(payResult);
                            mCallback = null;
                        }
                    }
                    break;
                    case 6001: {
                        if (mCallback != null) {
                            MkPayResult payResult = new MkPayResult(MkPayResult.PAY_STATE_CANCEL, resultInfo);
                            mCallback.onPayResult(payResult);
                            mCallback = null;
                        }
                    }
                    break;
                    default: {
                        if (mCallback != null) {
                            MkPayResult payResult = new MkPayResult(MkPayResult.PAY_STATE_FAIL, resultInfo);
                            mCallback.onPayResult(payResult);
                        }
                    }
                }
            }
        }.start();
    }
}
