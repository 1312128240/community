
package com.chanxa.linayi.tools;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chanxa.linayi.Interface.DialogListener;
import com.chanxa.linayi.R;


public class DialogUtils {


    public static void showDialog(final Context context, String str, boolean isCloseActivity) {
        //判断如果activity已经关闭，不运行dialog
        if (((Activity) context).isFinishing())
            return;
        final Dialog dialog = new Dialog(context, R.style.MyDialog);

        dialog.setContentView(R.layout.dialog_text);
        TextView t = (TextView) dialog.findViewById(R.id.text);
        t.setText(str);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        if (isCloseActivity) {
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    ((Activity) context).finish();
                }
            });
        }

    }

    /**
     * 显示提示框点击确定触发的事件
     * @param context
     * @param ok_btn_text     确认按钮文字
     * @param titleStr        标题      不需要标题，可以传入（NUll）或者 （空双引号“”）
     * @param str             文字提示
     */
    public static void showIsOkDialog(final Context context, String ok_btn_text, String close_btn_text, String titleStr, String str, final DialogListener listener) {
        //判断如果activity已经关闭，不运行dialog
        if (((Activity) context).isFinishing())
            return;
        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog_is_confirm);

        TextView title =dialog.findViewById(R.id.title);
        TextView text =dialog.findViewById(R.id.text);
        Button close_btn =dialog.findViewById(R.id.close_btn);
        Button ok_btn =dialog.findViewById(R.id.ok_btn);
        if (TextUtils.isEmpty(titleStr)) {
            title.setVisibility(View.GONE);
        } else {
            title.setText(titleStr);
        }
        if (TextUtils.isEmpty(str)) {
            text.setVisibility(View.GONE);
        } else {
            text.setText(str);
        }
        ok_btn.setText(ok_btn_text);
        close_btn.setText(close_btn_text);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listener.onComplete();
                dialog.cancel();
            }
        });

        close_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listener.onFail();
                dialog.cancel();
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }





    public static void showIsOkDialog(Context context, String ok, String cancel, String message, DialogListener listener){
        showIsOkDialog(context, ok, cancel,message,"", listener);
    }

    /**
     * 显示提示框点击确定触发的事件
     * @param context
     */
    public static Dialog showProgressDialog(Context context) {
        if (((Activity) context).isFinishing())
            return null;
        Dialog dialog = new Dialog(context, R.style.progress_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }


}
