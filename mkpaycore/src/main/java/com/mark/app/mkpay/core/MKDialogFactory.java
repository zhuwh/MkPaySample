package com.mark.app.mkpay.core;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * 对话框工厂
 * Created by lenovo on 2018/2/6.
 */

public class MKDialogFactory {
    public static class MKLoadingDialogFactory {
        private static MKLoadingDialog mLoadingDialog;


        /**
         * 显示加载中对话框
         *
         * @param activity
         */
        public static synchronized void showLoadingDialog(AppCompatActivity activity) {
            if (activity != null) {
                if (mLoadingDialog == null) {
                    mLoadingDialog = createLoadingDialog();
                }
                mLoadingDialog.showNow(activity, "LoadingDialog");
            }
        }

        /**
         * 显示加载中对话框
         *
         * @param fragment
         */
        public static synchronized void showLoadingDialog(Fragment fragment) {
            if (fragment != null) {
                if (mLoadingDialog == null) {
                    mLoadingDialog = createLoadingDialog();
                }
                mLoadingDialog.showNow(fragment, "LoadingDialog");
            }
        }

        /**
         * 隐藏加载中对话框
         */
        public static synchronized void hideLoadingDialog() {
            if (mLoadingDialog != null) {
                mLoadingDialog.dismissNow();
            }
        }

        /**
         * 创建加载中对话框
         *
         * @return
         */
        public static MKLoadingDialog createLoadingDialog() {
            return new MKLoadingDialog();
        }
    }
}
