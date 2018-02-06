package com.mark.app.mkpay.core;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

/**
 * 加载中对话框
 * Created by lenovo on 2018/1/25.
 */

public class MKLoadingDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.getWindow().getAttributes().windowAnimations = R.style.MKPay_DialogAnim;
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.mkpay_core_dialog_loading, container, false);
        ProgressBar pb = root.findViewById(R.id.dialog_pb);
        if (Build.VERSION.SDK_INT > 22 && pb != null) {
            //6.0以上
            Drawable drawable = getActivity().getDrawable(R.drawable.mkpay_core_pb_loading_after6);
            pb.setIndeterminateDrawable(drawable);
        }
        setCanceledOnTouchOutside(false);
        return root;
    }

    /**
     * 设置点击黑框是否消失
     *
     * @param canceledOnTouchOutside
     */
    protected void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        }
    }


    /**
     * 马上显示
     *
     * @param activity
     * @param tag
     */
    public void showNow(AppCompatActivity activity, String tag) {
        if (!isAdded() && activity != null) {
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.add(this, tag).commitNowAllowingStateLoss();
        }
    }

    /**
     * 马上显示
     *
     * @param fragment
     * @param tag
     */
    public void showNow(Fragment fragment, String tag) {
        if (!isAdded() && fragment != null) {
            FragmentManager manager = fragment.getFragmentManager();
            if (manager != null) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(this, tag).commitNowAllowingStateLoss();
            }
        }
    }

    /**
     * 马上消失
     */
    public void dismissNow() {
        if (isAdded() && getFragmentManager() != null) {
            FragmentManager manager = getFragmentManager();
            if (manager != null) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(this).commitNowAllowingStateLoss();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Window win = getDialog().getWindow();
        // 一定要设置Background，如果不设置，window属性设置无效
        win.setBackgroundDrawable(new ColorDrawable(0x00ffffff));

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        WindowManager.LayoutParams params = win.getAttributes();
        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        win.setAttributes(params);
    }
}
