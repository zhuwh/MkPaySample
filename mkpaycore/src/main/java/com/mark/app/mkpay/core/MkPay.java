package com.mark.app.mkpay.core;

import android.app.Activity;
import android.support.annotation.IntDef;
import android.util.SparseArray;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by zhuwh on 2017/9/28.
 */

public class MkPay {

    //支付类型

    /**
     * @hide
     */
    @IntDef({PAY_TYPE_ALIPAY, PAY_TYPE_WXPAY})
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


    SparseArray<MkPayInf> mPayImpArray = new SparseArray<>();

    public void init(@MkPayType int[] payTypes){
        if (payTypes!=null){
            for (int payType:
                 payTypes) {
                MkPayInf payImp = create(payType);
                if (payImp!=null){
                    mPayImpArray.put(payType,payImp);
                }
            }
        }
    }

    private MkPayInf create(@MkPayType int payType){
        switch (payType){
            case PAY_TYPE_ALIPAY:{
                try {
                    Class clazz = Class.forName("com.mark.app.mkpay.alipay.MkAlipay");
                    return (MkPayInf) clazz.newInstance();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            case PAY_TYPE_WXPAY:{

                return null;
            }
            default:
                return null;
        }
    }

    public void pay(Activity activity, Object payInfo, @MkPayType int type, final MkPayCallback callback) {
        MkPayInf payImp = mPayImpArray.get(type);
        if (payImp!=null){
            payImp.pay(activity,payInfo,callback);
        }
    }

}
