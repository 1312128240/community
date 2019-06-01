package com.chanxa.linayi.uis.login;

import android.os.Process;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chanxa.linayi.App;
import com.chanxa.linayi.HttpClient.OkhttpUtil;
import com.chanxa.linayi.HttpClient.ResultCallback;
import com.chanxa.linayi.R;

import com.chanxa.linayi.bean.LoginBean;
import com.chanxa.linayi.tools.ActivityManagerUtils;
import com.chanxa.linayi.tools.AppUtils;
import com.chanxa.linayi.tools.SPUtils;
import com.chanxa.linayi.uis.BaseActivity;
import com.chanxa.linayi.uis.MainActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;


public class LoginActivity extends BaseActivity{

    @BindView(R.id.tv_login)
    TextView tvLogin;
   // UserRepository userRepository;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.iv_click)
    ImageView ivClick;
    @BindView(R.id.tv_find_password)
    TextView tvFindPassword;
    @BindView(R.id.phone_clear)
    ImageView phoneClear;
    @BindView(R.id.password_clear)
    ImageView passwordClear;

    private boolean isClick;
    private long exitTime = 0;


    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
     //   userRepository = new UserRepository(LoginActivity.this);
        if (SPUtils.isLogin(this)) {
            startActivitys(MainActivity.class);
            finish();
        }
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                phoneClear.setVisibility(TextUtils.isEmpty(s.toString().trim()) ? View.GONE : View.VISIBLE);
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                passwordClear.setVisibility(TextUtils.isEmpty(s.toString().trim()) ? View.GONE : View.VISIBLE);
            }
        });

        if (!SPUtils.getMobile(App.getInstance()).equals("0")) {
            etPhone.setText(SPUtils.getMobile(App.getInstance()));
        }

        if (!SPUtils.getPassword(App.getInstance()).equals("0")) {
            isClick = true;
            ivClick.setImageResource(R.drawable.xuanze_icon);
            etPassword.setText(SPUtils.getPassword(App.getInstance()));
        }
    }

    /**
     * 登录
     */
   @OnClick(R.id.tv_login)
    public void onViewClicked() {
        SPUtils.setPassword(App.getInstance(), etPassword.getText().toString());
        SPUtils.setMobile(App.getInstance(), etPhone.getText().toString());

       if(checkMessage()){
           showProgressDialog();
           Map<String,String> map=new HashMap<>();
           map.put("mobile",etPhone.getText().toString());
           map.put("password",etPassword.getText().toString());
           OkhttpUtil
                   .getmIntance()
                   .seTag(this)
                   .PostAsync("account/account/communityLogin.do", map, new ResultCallback<LoginBean>() {
                       @Override
                       public void onFailure(Call call, IOException e) {
                           dismissProgressDialog();
                       }

                       @Override
                       public void onResponse(Call call,LoginBean loginBean) {
                           dismissProgressDialog();
                           if("S".equals(loginBean.getRespCode())) {
                               SPUtils.put(App.getInstance(), SPUtils.ACCESSTOKEN, loginBean.getData().getAccessToken());
                               SPUtils.setCommunityName(App.getInstance(), loginBean.getData().getCommunityName());
                               SPUtils.setIsLogin(LoginActivity.this, true);
                               startActivitys(MainActivity.class);
                               finish();
                           }else {
                               showToast(loginBean.getErrorMsg(),0);
                           }
                       }
                   });

       }

    }

    public boolean checkMessage() {
        if (etPhone.getText().length() == 0) {
            showToast("请输入手机号码",0);
            return false;
        }
        if (etPassword.getText().length() == 0) {
            showToast("请输入密码",0);
            return false;
        }


        if (!AppUtils.isMobileNO(etPhone.getText().toString())) {
            showToast("请输入正确的手机号码",0);
            return false;
        }
        return true;
    }

    @OnClick({R.id.iv_click, R.id.tv_find_password, R.id.phone_clear, R.id.password_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_click:
                isClick = !isClick;
                ivClick.setImageResource(isClick ? R.drawable.xuanze_icon : R.drawable.weixuanze_icon);
                break;
            case R.id.tv_find_password:
                startActivitys(FindPasswordActivity.class);
                break;
            case R.id.phone_clear:
                etPhone.setText("");
                break;
            case R.id.password_clear:
                etPassword.setText("");
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ((boolean) SPUtils.get(App.getInstance(), "isNotFindPassword", false)) {
            SPUtils.put(App.getInstance(), "isNotFindPassword", false);
            showToast("找回密码失败",0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SPUtils.put(App.getInstance(), "isNotFindPassword", false);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 退出
     */
    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            showToast("再按一次退出程序",0);
            exitTime = System.currentTimeMillis();
        } else {
            ActivityManagerUtils.getInstance().exit();
            Process.killProcess(Process.myPid());
            System.exit(0);
        }
    }

}
