package com.mark.app.mkpay.ipaynow;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.ipaynow.alipay.plugin.api.CrossAliPayPlugin;
import com.ipaynow.alipay.plugin.manager.route.dto.ResponseParams;
import com.ipaynow.alipay.plugin.manager.route.impl.ReceivePayResult;
import com.mark.app.mkpay.core.MkPayCallback;
import com.mark.app.mkpay.core.MkPayInf;
import com.mark.app.mkpay.core.MkPayResult;

/**
 * Created by zhuwh on 2017/11/9.
 */

public class MKIpnPay implements MkPayInf<String>,ReceivePayResult {

    Context context;
    MkPayCallback mCallback;
    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void pay(Activity activity, String payInfo, MkPayCallback callback) {
        this.mCallback = callback;
        CrossAliPayPlugin.getInstance()
                .setCallResultReceiver(this)// 传入继承了通知接口的类
                .pay(payInfo, activity);// 传入请求数据
    }

    @Override
    public void onIpaynowTransResult(ResponseParams resp) {
        if (resp == null) {
            return;
        }
        String respCode = resp.respCode;
        String errorCode = resp.errorCode;
        String respMsg = resp.respMsg;
        StringBuilder temp = new StringBuilder();
        if (respCode.equals("00")) {
//            temp.append("交易状态:成功");
            if (mCallback != null) {
                MkPayResult payResult = new MkPayResult(MkPayResult.PAY_STATE_SUCCESS, respMsg);
                mCallback.onPayResult(payResult);
                mCallback = null;
            }
        } else if (respCode.equals("02")) {
//            temp.append("交易状态:取消");
            if (mCallback != null) {
                MkPayResult payResult = new MkPayResult(MkPayResult.PAY_STATE_CANCEL, respMsg);
                mCallback.onPayResult(payResult);
                mCallback = null;
            }
        } else if (respCode.equals("01")) {
//            temp.append("交易状态:失败").append("\n").append("错误码:").append(errorCode).append("原因:" + respMsg);
            if (mCallback != null) {
                MkPayResult payResult = new MkPayResult(MkPayResult.PAY_STATE_FAIL, respMsg);
                mCallback.onPayResult(payResult);
            }

        } else if (respCode.equals("03")) {
//            temp.append("交易状态:未知").append("\n").append("原因:" + respMsg);
            if (mCallback != null) {
                MkPayResult payResult = new MkPayResult(MkPayResult.PAY_STATE_FAIL, respMsg);
                mCallback.onPayResult(payResult);
            }
        } else {
            temp.append("respCode=").append(respCode).append("\n").append("respMsg=").append(respMsg);
            if (mCallback != null) {
                MkPayResult payResult = new MkPayResult(MkPayResult.PAY_STATE_FAIL, respMsg);
                mCallback.onPayResult(payResult);
            }
        }
        Toast.makeText(context, temp.toString(), Toast.LENGTH_LONG).show();
    }
}
