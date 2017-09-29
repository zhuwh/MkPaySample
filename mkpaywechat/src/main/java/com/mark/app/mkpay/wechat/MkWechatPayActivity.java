package com.mark.app.mkpay.wechat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.mark.app.mkpay.core.MkPayResult;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by zhuwh on 2017/9/29.
 */

public class MkWechatPayActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApi = WXAPIFactory.createWXAPI(this, null);
        mApi.registerApp(MkWechatPay.WX_APPID);
        mApi.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mApi.handleIntent(intent, this);
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
            MkPayResult result = null;
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    result = new MkPayResult(MkPayResult.PAY_STATE_SUCCESS, resp.errStr);
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    result = new MkPayResult(MkPayResult.PAY_STATE_CANCEL, resp.errStr);
                    break;
                default:
                    result = new MkPayResult(MkPayResult.PAY_STATE_FAIL, resp.errStr);
                    break;
            }
            Intent intent = new Intent();
            intent.setAction(MkWechatPay.WX_PAY_RESULT_ACTION);        //设置Action
            intent.putExtra(MkWechatPay.WX_PAY_RESULT_KEY, result);
            localBroadcastManager.sendBroadcast(intent);               //发送Intent
            finish();
        }
    }
}
