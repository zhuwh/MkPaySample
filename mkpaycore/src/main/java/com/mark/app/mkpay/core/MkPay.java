package com.mark.app.mkpay.core;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IntDef;
import android.util.SparseArray;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import timber.log.Timber;

/**
 * Created by zhuwh on 2017/9/28.
 */

public abstract class MkPay {

    //支付类型

    /**
     * @hide
     */
    @IntDef({PAY_TYPE_ALIPAY, PAY_TYPE_WXPAY,PAY_TYPE_IPAYNOW})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MkPayType {
    }

    /**
     * 支付宝
     */
    public static final int PAY_TYPE_ALIPAY = 1;

    /**
     * 微信支付
     */
    public static final int PAY_TYPE_WXPAY = 2;

    /**
     * 现在支付
     */
    public static final int PAY_TYPE_IPAYNOW = 3;


    SparseArray<MkPayInf> mPayImpArray = new SparseArray<>();

    private Context mContext;  //应用上下文。生命周期为怎么应用的生命周期

    private static MkPay s_instance;

    private boolean isInit;

    private MkPay(Context context) {
        mContext = context.getApplicationContext();
    }

    public static MkPay getInstance(Context context){
        if (s_instance==null){
            synchronized (MkPay.class){
                if (s_instance==null){
                    s_instance = new MkPay(context) {};
                }
            }
        }
        return s_instance;
    }

    /**
     * 初始化支付通道，只能初始化一次
     * @param payTypes
     */
    public void init(@MkPayType int[] payTypes) {
        if (!isInit){
            if (payTypes != null) {
                for (int payType :
                        payTypes) {
                    MkPayInf payImp = create(payType);
                    if (payImp != null) {
                        payImp.setContext(mContext);
                        mPayImpArray.put(payType, payImp);
                    }
                }
            }
            isInit = true;
        }
    }

    private MkPayInf create(@MkPayType int payType) {
        switch (payType) {
            case PAY_TYPE_ALIPAY: {
                return getObj("com.mark.app.mkpay.alipay.MkAlipay");
            }
            case PAY_TYPE_WXPAY: {
                return getObj("com.mark.app.mkpay.wechat.MkWechatPay");
            }
            case PAY_TYPE_IPAYNOW: {
                return getObj("com.mark.app.mkpay.ipaynow.MKIpnPay");
            }
            default:
                return null;
        }
    }

    private MkPayInf getObj(String className) {
        try {
            Class clazz = Class.forName(className);
            return (MkPayInf) clazz.newInstance();
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            Timber.e(e);
            return null;
        } catch (Exception e) {
//            Timber.e(e);
//            e.printStackTrace();
            return null;
        }
    }

    /**
     * 支付方法
     * 当type 为 PAY_TYPE_ALIPAY 时，payInfo 数据类型为String
     * 当type 为 PAY_TYPE_WXPAY 时,payInfo 为 JSON String
     *
     * @param activity
     * @param payInfo
     * @param type
     * @param callback
     */
    public void pay(Activity activity, String payInfo, @MkPayType int type, final MkPayCallback callback) {
        MkPayInf payImp = mPayImpArray.get(type);
        if (payImp != null) {
            payImp.pay(activity, payInfo, callback);
        } else {
            Timber.e("支付渠道未初始化或初始化失败！请检查配置正确后重试！");
        }
    }

}
