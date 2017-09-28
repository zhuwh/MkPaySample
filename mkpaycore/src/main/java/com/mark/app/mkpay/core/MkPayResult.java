package com.mark.app.mkpay.core;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by zhuwh on 2017/9/28.
 */

public final class MkPayResult implements Parcelable {

    //支付结果状态

    /**
     * @hide
     */
    @IntDef({PAY_STATE_SUCCESS, PAY_STATE_FAIL, PAY_STATE_CANCEL, PAY_STATE_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PayResultStatus {
    }

    /**
     * 支付成功
     */
    public static final int PAY_STATE_SUCCESS = 1;
    /**
     * 支付失败
     */
    public static final int PAY_STATE_FAIL = 2;
    /**
     * 支付取消
     */
    public static final int PAY_STATE_CANCEL = 3;
    /**
     * 支付异常
     */
    public static final int PAY_STATE_ERROR = 4;

    private int resultStatus;
    private String result;

    public MkPayResult(@PayResultStatus int status, String result) {
        this.resultStatus = status;
        this.result = result;
    }

    public MkPayResult(Parcel source) {
        this.resultStatus = source.readInt();
        this.result = source.readString();
    }

    public int getResultStatus() {
        return resultStatus;
    }

    public String getResult() {
        return result;
    }

    public static final Parcelable.Creator<MkPayResult> CREATOR = new Parcelable.Creator<MkPayResult>() {
        public MkPayResult createFromParcel(Parcel in) {
            return new MkPayResult(in);
        }

        public MkPayResult[] newArray(int size) {
            return new MkPayResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(resultStatus);
        dest.writeString(result);
    }
}
