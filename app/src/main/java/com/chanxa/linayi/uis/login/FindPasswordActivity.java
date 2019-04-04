package com.chanxa.linayi.uis.login;

import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.chanxa.linayi.App;
import com.chanxa.linayi.Contact.FindPasswordContact;
import com.chanxa.linayi.Presenter.FindPasswordPresenter;
import com.chanxa.linayi.R;
import com.chanxa.linayi.tools.AppUtils;
import com.chanxa.linayi.tools.SPUtils;
import com.chanxa.linayi.uis.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by chanxa on 2017/12/19.
 */

public class FindPasswordActivity extends BaseActivity implements FindPasswordContact.View {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.btn_get_code)
    TextView btnGetCode;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_password_confirm)
    EditText etPasswordConfirm;
    @BindView(R.id.tv_post)
    TextView tvPost;
    @BindView(R.id.phone_clear)
    ImageView phoneClear;
    @BindView(R.id.code_clear)
    ImageView codeClear;
    @BindView(R.id.password_clear)
    ImageView passwordClear;
    @BindView(R.id.password_confirm_clear)
    ImageView passwordConfirmClear;

    boolean canPost = true;

    FindPasswordPresenter presenter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_find_password;
    }

    @Override
    public void initView() {
        presenter = new FindPasswordPresenter(this, this);


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

        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                codeClear.setVisibility(TextUtils.isEmpty(s.toString().trim()) ? View.GONE : View.VISIBLE);
            }
        });

        etPasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                passwordConfirmClear.setVisibility(TextUtils.isEmpty(s.toString().trim()) ? View.GONE : View.VISIBLE);
            }
        });
    }


    @OnClick({R.id.iv_back, R.id.tv_back, R.id.btn_get_code, R.id.tv_post,
            R.id.phone_clear, R.id.code_clear, R.id.password_clear, R.id.password_confirm_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
            case R.id.tv_back:
                SPUtils.put(App.getInstance(), "isNotFindPassword", true);
                finish();
                break;
            case R.id.btn_get_code:
                String account = etPhone.getText().toString();
                presenter.getValidateCode(account);
                break;
            case R.id.tv_post:
                if (checkMessage()) {
                    canPost = false;
                    presenter.resetPassword(
                            etPhone.getText().toString(),
                            etCode.getText().toString(),
                            etPassword.getText().toString());
                }
                break;
            case R.id.phone_clear:
                etPhone.setText("");
                break;
            case R.id.code_clear:
                etCode.setText("");
                break;
            case R.id.password_clear:
                etPassword.setText("");
                break;
            case R.id.password_confirm_clear:
                etPasswordConfirm.setText("");
                break;
        }
    }

    /**
     * 倒计时
     */
    private Handler handler;
    private int time = 60;
    private Runnable mRunnable;

    private void countdownTime() {
        handler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (time == 0) {
                    time = 60;
                    btnGetCode.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                    //设置按钮可点
                    btnGetCode.setClickable(true);
                    //设置按钮名称
                    btnGetCode.setText("获取验证码");
                    //移除线程
                    handler.removeCallbacks(this);
                    return;
                }
                btnGetCode.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                //设置按钮不可点击
                btnGetCode.setClickable(false);
                //设置倒计时间
                btnGetCode.setText(String.valueOf(time) + "S");
                time--;
                //设置1秒执行一次
                handler.postDelayed(mRunnable, 1000);
            }
        };
        handler.post(mRunnable);
    }

    @Override
    public void onFindPasswordSuccess() {
        showToast("修改密码成功");
        canPost = true;
        finish();
    }

    @Override
    public void onGetVCodeSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast(getString(R.string.send_vcode_success));
                //获取验证成功,倒计时
                countdownTime();
            }
        });
    }

    @Override
    public void onNullAccount(String tips) {
        showToast(tips);
    }

    @Override
    public void showProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //显示加载进度
                showProgressDialog();
            }
        });
    }

    @Override
    public void dismissProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //关闭加载进度
                dismissProgressDialog();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacks(mRunnable);
        }
    }

    public boolean checkMessage() {
        //如果未输入手机号码或邮箱
        if (TextUtils.isEmpty(etPhone.getText().toString())) {
            showToast("请输入手机号码");
            return false;
        }

        //如果输入的既不是邮箱也不是手机号码
        if (!AppUtils.isMobileNO(etPhone.getText().toString())) {
            showToast("请输入正确的手机号码");
            return false;
        }

        //如果未输入验证码
        if (TextUtils.isEmpty(etCode.getText().toString())) {
            showToast("请输入验证码");
            return false;
        }

        //如果未输入密码
        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            showToast("请输入密码");
            return false;
        }

        //如果密碼小于6位或大于32位
        if (etPassword.getText().length() < 6 || etPassword.getText().length() > 32 || !AppUtils.isLetterAndNum(etPassword.getText().toString())) {
            showToast("密码必须为6-32位数字字母组合");
            return false;
        }

        return true;
    }
}
