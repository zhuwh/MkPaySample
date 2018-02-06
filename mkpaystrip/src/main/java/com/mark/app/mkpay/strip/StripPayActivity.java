package com.mark.app.mkpay.strip;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mark.app.mkpay.R;
import com.mark.app.mkpay.core.MKDialogFactory;
import com.mark.app.mkpay.strip.util.MetaDataUtil;
import com.stripe.android.Stripe;
import com.stripe.android.StripeTextUtils;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardNumberEditText;
import com.stripe.android.view.ExpiryDateEditText;
import com.stripe.android.view.StripeEditText;

import java.io.Serializable;

/**
 * strip支付填写信息界面
 * Created by lenovo on 2018/2/5.
 */

public class StripPayActivity extends AppCompatActivity {
    private static int REQUEST_CODE = 123;//请求code
    private static final String KEY_DATA = "data";

    private final String METADATA_STRIP_PK = "MKPAY_METADATA_KEY_STRIP";

    /**
     * 前往strip支付界面
     *
     * @param activity
     */
    public static void gotoHere(AppCompatActivity activity, int requestCode) {
        REQUEST_CODE = requestCode;
        if (activity != null) {
            Intent intent = new Intent(activity, StripPayActivity.class);
            activity.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * Activity请求回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     * @param callBack
     */
    public static void onActivityResult(int requestCode, int resultCode, Intent data, CallBack<Result> callBack) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && callBack != null) {
            Result result = (Result) data.getSerializableExtra(KEY_DATA);
            callBack.call(result);
        }
    }

    CardNumberEditText mCardNum;//卡号View
    ExpiryDateEditText mExpiryDate;//到期时间View
    StripeEditText mStripEdit;//CVCView

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.strip_activity_strip_pay);
        initView();
    }

    private void initView() {
        TextView tv = findViewById(R.id.titlebar_tv_title);
        tv.setText("填写卡信息");
        mCardNum = findViewById(R.id.et_card_number);
        mExpiryDate = findViewById(R.id.et_expiry_date);
        mStripEdit = findViewById(R.id.et_cvc_number);

        mCardNum.setErrorColor(0xffff0000);
        mExpiryDate.setErrorColor(0xffff0000);
        mStripEdit.setErrorColor(0xffff0000);
    }

    private Card getCard() {
        String cardNumber = mCardNum.getCardNumber();
        int[] cardDate = mExpiryDate.getValidDateFields();
        if (cardNumber == null || cardDate == null || cardDate.length != 2) {
            return null;
        }
        String cvcValue = mStripEdit.getText().toString();
        if (StripeTextUtils.isBlank(cvcValue)) {
            return null;
        }
        return new Card(cardNumber, cardDate[0], cardDate[1], cvcValue);
    }

    /**
     * 提交
     *
     * @param v
     */
    public void commit(View v) {
        try {
            Card card = getCard();
            if (card == null) {
                toast("请检查您填写的信息");
            } else {
                if (card.validateCard()) {
                    String key = MetaDataUtil.getApplicationInfo(this, METADATA_STRIP_PK);
                    if (TextUtils.isEmpty(key)) {
                        toast("未设置key");
                        log("未配置key");
                    } else {
                        log("配置的key:" + key);
                        showLoadingDialog();
                        Stripe stripe = new Stripe(this, key);
                        stripe.createToken(card, new TokenCallback() {
                                    public void onSuccess(Token token) {
                                        // Send token to your server
                                        Result result = new Result();
                                        result.setToken(token.getId())
                                                .setCode(Result.CODE_SUCCESS)
                                                .setMessage("Token获取成功");
                                        setCallback(result);
                                        hideLoadingDialog();
                                    }

                                    public void onError(Exception error) {
                                        // Show localized error message
                                        toast(error.getMessage());
                                        error.printStackTrace();
                                        hideLoadingDialog();
                                    }
                                }
                        );
                    }
                } else {
                    toast("卡信息校验不通过");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 回退
     *
     * @param view
     */
    public void onClickReturn(View view) {
        Result result = new Result();
        result.setCode(Result.CODE_CANCEL)
                .setMessage("取消支付");
        setCallback(result);
    }

    /**
     * 回调
     *
     * @param result
     */
    private void setCallback(Result result) {
        if (result != null) {
            Intent intent = new Intent();
            intent.putExtra(KEY_DATA, result);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void log(String msg) {
        Log.i("MkPay", "[className]StripPayActivity---" + msg);
    }

    private void showLoadingDialog() {
        MKDialogFactory.MKLoadingDialogFactory.showLoadingDialog(this);
    }

    private void hideLoadingDialog() {
        MKDialogFactory.MKLoadingDialogFactory.hideLoadingDialog();
    }

    /**
     * 返回数据模型
     */
    public static class Result implements Serializable {
        public static final int CODE_SUCCESS = 1;//成功
        public static final int CODE_ERROR = 2;//报错
        public static final int CODE_CANCEL = 3;//取消

        String token;
        String message;
        int code;

        @Override
        public String toString() {
            return "token:" + token + ",message:" + message + ",code:" + code;
        }

        public String getToken() {
            return token;
        }

        public Result setToken(String token) {
            this.token = token;
            return this;
        }

        public String getMessage() {
            return message;
        }

        public Result setMessage(String message) {
            this.message = message;
            return this;
        }

        public int getCode() {
            return code;
        }

        public Result setCode(int code) {
            this.code = code;
            return this;
        }
    }

    /**
     * 回调
     *
     * @param <T>
     */
    public interface CallBack<T> {
        void call(T data);
    }
}
