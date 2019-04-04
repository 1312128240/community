
package com.chanxa.linayi.uis;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.chanxa.linayi.App;
import com.chanxa.linayi.HttpClient.okhttp.OkhttpUtil;
import com.chanxa.linayi.R;
import com.chanxa.linayi.tools.ActivityManagerUtils;
import com.chanxa.linayi.tools.DialogUtils;
import com.chanxa.linayi.tools.SPUtils;
import com.chanxa.linayi.tools.ToastUtil;
import com.chanxa.linayi.uis.login.LoginActivity;

import butterknife.ButterKnife;




public abstract class BaseActivity extends FragmentActivity {

    private Dialog mDialog;
    private Context mContext;
    public boolean isFirst;
    private AlertDialog.Builder logoutDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        hyalinize();
        setContentView(getLayoutId());
        ActivityManagerUtils.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initView();
        initData();
    }


    public abstract int getLayoutId();

    public abstract void initView();

    public void initData(){}
    

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(logoutDialog!=null){
           logoutDialog=null;
        }
        OkhttpUtil.getmIntance().cancelTag(this);

        ActivityManagerUtils.getInstance().finishActivity(this);
    }


    /**
     * 沉浸式
     **/
    private void hyalinize() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    


    public void startActivitys(Class cls){
        if(cls!=null){
            startActivity(new Intent(this,cls));
        }
    }

    public void showToast(String msg){
        ToastUtil.showLong(this, msg);
    }

    public void hideSortInput(View view){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }



    public void showProgressDialog() {
        if (mDialog == null) {
            dismissProgressDialog();
            mDialog = null;
            mDialog = DialogUtils.showProgressDialog(this);
        }
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
    }

    public void dismissProgressDialog() {
        if (mDialog == null || !mDialog.isShowing()) {
            return;
        }
        mDialog.dismiss();
    }


    public void showLogOutDialog(){
        if(!isFirst){
            logoutDialog = new AlertDialog.Builder(this);
            logoutDialog.setTitle("提示");
            logoutDialog.setCancelable(false);
            logoutDialog.setIcon(R.mipmap.logo);
            logoutDialog.setMessage("您的accessToken已失效,请重新登录!");
            logoutDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    //清空偏好设置
                    SPUtils.setPassword(App.getInstance(), "");
                    SPUtils.setMobile(App.getInstance(), "");
                    SPUtils.setAccessToken(App.getInstance(), "");
                    SPUtils.setCommunityName(App.getInstance(), "");
                    SPUtils.put(App.getInstance(), SPUtils.IS_LOGIN, false);
                    //清空原先的task栈,再生成一个新的task栈
                    Intent intent = new Intent(mContext,LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                    isFirst=false;
                }
            }).create().show();
            isFirst=true;

        }
    }


   }
