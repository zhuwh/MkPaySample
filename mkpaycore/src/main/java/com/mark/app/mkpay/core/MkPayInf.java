package com.mark.app.mkpay.core;

import android.app.Activity;

/**
 * Created by zhuwh on 2017/9/28.
 */

public interface MkPayInf<T> {
    public void pay(Activity activity, T payInfo, final MkPayCallback callback);
}
