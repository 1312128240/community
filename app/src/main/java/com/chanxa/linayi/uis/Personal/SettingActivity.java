package com.chanxa.linayi.uis.Personal;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.widget.TextView;

import com.chanxa.linayi.App;
import com.chanxa.linayi.R;
import com.chanxa.linayi.tools.ActivityManagerUtils;
import com.chanxa.linayi.tools.SPUtils;
import com.chanxa.linayi.uis.BaseActivity;
import com.chanxa.linayi.uis.MainActivity;
import com.chanxa.linayi.uis.login.LoginActivity;
import com.chanxa.linayi.views.dialogfragments.ExitDialogFragment;

import butterknife.BindView;
import butterknife.OnClick;


public class SettingActivity extends BaseActivity{

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.parent)
    ConstraintLayout parent;

    private ExitDialogFragment dialogFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        tv_title.setText("账户设置");
    }

    @OnClick(R.id.tv_actionbar_back)
    public void back(){
        onBackPressed();
    }

    @OnClick(R.id.btn_exit)
    public void exit(){

        dialogFragment = new ExitDialogFragment();
        dialogFragment.show(getSupportFragmentManager(),"Exit");

        dialogFragment.setClickListener(new ExitDialogFragment.ExitDialogClickListener() {
            @Override
            public void sure() {
                //清空偏好设置
                SPUtils.setPassword(App.getInstance(), "");
                SPUtils.setMobile(App.getInstance(), "");
                SPUtils.setAccessToken(App.getInstance(), "");
                SPUtils.setCommunityName(App.getInstance(), "");
                SPUtils.put(App.getInstance(), SPUtils.IS_LOGIN, false);
                //清空原先的task栈,再生成一个新的task栈
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                //关闭界面
                ActivityManagerUtils.getInstance().finishActivityclass(MainActivity.class);
                finish();
            }

            @Override
            public void cancle() {
                if (dialogFragment != null) {
                    dialogFragment.dismiss();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Tokent",SPUtils.getAccessToken(this));
    }
}
