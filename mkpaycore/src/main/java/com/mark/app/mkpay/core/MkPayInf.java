package com.mark.app.mkpay.core;

import android.app.Activity;
import android.content.Context;

/**
 * Created by zhuwh on 2017/9/28.
 */

public interface MkPayInf<T> {

    public void setContext(Context context);
    public void pay(Activity activity, T payInfo, final MkPayCallback callback);
}
