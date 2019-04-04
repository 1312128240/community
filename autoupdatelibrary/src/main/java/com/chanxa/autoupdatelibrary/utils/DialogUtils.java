package com.chanxa.autoupdatelibrary.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chanxa.autoupdatelibrary.R;


public class DialogUtils {


    /**
     * 显示提示框点击确定触发的事件
     * @param context
     * @param ok_btn_text     确认按钮文字
     * @param titleStr        标题      不需要标题，可以传入（NUll）或者 （空双引号“”）
     * @param content             文字提示
     */
    public static void showIsOkDialog(final Context context, String ok_btn_text, String close_btn_text, String titleStr, String content, boolean isForceUpdate, final DialogListener listener) {
        //判断如果activity已经关闭，不运行dialog
        if (((Activity) context).isFinishing())
            return;
        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog_layout);
        //标题
        TextView dialog_title = (TextView) dialog.findViewById(R.id.dialog_title);
        //内容
        TextView dialog_content = (TextView) dialog.findViewById(R.id.dialog_content);
        //取消按钮
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        //确定按钮
        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
        //如果标题为空
        if (TextUtils.isEmpty(titleStr)) {
            dialog_title.setVisibility(View.GONE);
        } else {
            dialog_title.setText(titleStr);
        }
        //如果内容为空
        if (TextUtils.isEmpty(content)) {
            dialog_content.setVisibility(View.GONE);
        } else {
            dialog_content.setText(content);
        }
        //设置确定按钮名称
        btn_ok.setText(ok_btn_text);
        //设置取消按钮名称
        btn_cancel.setText(close_btn_text);
        //设置确定按钮点击事件
        btn_ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listener.onComplete();
                dialog.cancel();
            }
        });
        //设置取消按钮点击事件
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listener.onFail();
                dialog.cancel();
            }
        });
        //如果返回键不能退出，也就是强制更新
        if (isForceUpdate) {
            dialog.setCanceledOnTouchOutside(false);
        }
        dialog.show();
    }


    public static void showIsOkDialog(Context context, String ok, String cancel, String title, String message, DialogListener listener) {
        showIsOkDialog(context, ok, cancel, title, message, false, listener);
    }


    /**
     * 显示安装弹窗
     * @param context
     * @param appName
     * @param versionName
     * @param listener
     */
    public static void showInstallApkDialog(Context context, String appName, String versionName, final DialogListener listener) {

        //判断如果activity已经关闭，不运行dialog
        if (((Activity) context).isFinishing())
            return;
        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog_install_apk);
        TextView tv_content = (TextView) dialog.findViewById(R.id.tv_content);
        //取消按钮
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        //立即安装按钮
        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
        //设置内容
        if (!TextUtils.isEmpty(appName)) {
            tv_content.setText(appName + "V" + versionName + context.getString(R.string.update_version_download_complete));
        }
        //设置立即安装按钮点击事件
        btn_ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listener.onComplete();
                dialog.cancel();
            }
        });
        //设置取消按钮点击事件
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listener.onFail();
                dialog.cancel();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


}
