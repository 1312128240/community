package com.chanxa.linayi.uis;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.widget.TextView;

import com.chanxa.linayi.Adapters.HomeRcvAdapter;
import com.chanxa.linayi.App;
import com.chanxa.linayi.R;
import com.chanxa.linayi.tools.ActivityManagerUtils;
import com.chanxa.linayi.tools.AppUtils;
import com.chanxa.linayi.tools.GrildSpaceItemDecoration;
import com.chanxa.linayi.tools.SPUtils;
import com.chanxa.linayi.uis.login.LoginActivity;

import butterknife.BindView;


public class MainActivity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    private long exitTime = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        if (AppUtils.isLogin(App.getInstance(), false)){
            tvAddress.setText(SPUtils.getCommunityName(App.getInstance()));
        }
        initRecy();
    }

    private void initRecy() {
        HomeRcvAdapter adapter = new HomeRcvAdapter(this);
        rv.setLayoutManager(new GridLayoutManager(this,3));
        rv.addItemDecoration(new GrildSpaceItemDecoration(1));
        rv.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!AppUtils.isLogin(this, false)){
             startActivity(new Intent(this, LoginActivity.class));
             finish();
        }
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (AppUtils.isLogin(App.getInstance(), false)){
                    tvAddress.setText(SPUtils.getCommunityName(App.getInstance()));
                }
            }
        }, 1000);
        if (AppUtils.isLogin(App.getInstance(), false)){
            tvAddress.setText(SPUtils.getCommunityName(App.getInstance()));
        }
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
            showToast(getString(R.string.then_click_one_exit_procedure));
            exitTime = System.currentTimeMillis();
        } else {
            ActivityManagerUtils.getInstance().exit();
            Process.killProcess(Process.myPid());
            System.exit(0);
        }
    }


}
