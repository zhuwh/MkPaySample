package com.mark.app.mkpaysample;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by zhuwh on 2017/9/11.
 */

public class MKBaseResultEntity<T> implements Serializable {

    @SerializedName("resultCode")
    private int resultCode; //错误码
    @SerializedName("reason")
    private String reason; //错误说明(文案)
    @SerializedName("result")
    private T data; //结果数据
    @SerializedName("nowTime")
    private long nowTime;

    public int getResultCode() {
        return resultCode;
    }

    public MKBaseResultEntity setResultCode(int resultCode) {
        this.resultCode = resultCode;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public MKBaseResultEntity setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public T getData() {
        return data;
    }

    public MKBaseResultEntity setData(T data) {
        this.data = data;
        return this;
    }

    public long getNowTime() {
        return nowTime;
    }

    public MKBaseResultEntity setNowTime(long nowTime) {
        this.nowTime = nowTime;
        return this;
    }
}
