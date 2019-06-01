package com.chanxa.linayi.uis;

import android.content.Intent;
import android.os.Process;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.widget.TextView;

import com.chanxa.linayi.Adapters.HomeRcvAdapter;
import com.chanxa.linayi.App;
import com.chanxa.linayi.R;
import com.chanxa.linayi.tools.ActivityManagerUtils;
import com.chanxa.linayi.tools.ApkInstall.CheckVersionUtils;
import com.chanxa.linayi.tools.AppUtils;
import com.chanxa.linayi.tools.GrildSpaceItemDecoration;
import com.chanxa.linayi.tools.SPUtils;
import com.chanxa.linayi.uis.login.LoginActivity;
import com.rscja.deviceapi.RFIDWithUHF;
import com.rscja.deviceapi.exception.ConfigurationException;

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
        initRecy();
        //自己写的检测安装apk，暂时用bugly替代，如需要用，通知后台即可
        if (!(boolean)SPUtils.get(this, SPUtils.IS_LOGIN, false)){
            CheckVersionUtils checkVersionUtils=new CheckVersionUtils(this);
            checkVersionUtils.getVersion();
        }

    }



    private void initRecy() {

        String [] names = new String[0];
        int [] drawableArrays=new int[0];

        try {
            RFIDWithUHF mReader = RFIDWithUHF.getInstance();
            boolean result= mReader.init();

            if(!result){
               names= new String[]{"订单", "任务", "收货", "装箱", "配送", "账号设置"};
               drawableArrays= new int[]{R.drawable.icon_home_order, R.drawable.icon_home_task, R.drawable.icon_home_goods_receiving,
                       R.drawable.icon_home_packing, R.drawable.icon_home_goods_receipt, R.drawable.icon_home_account_number,
               };

            }else {
                names= new String[]{"订单", "任务", "收货", "装箱", "配送", "账号设置", "UHF"};
                drawableArrays= new int[]{R.drawable.icon_home_order, R.drawable.icon_home_task, R.drawable.icon_home_goods_receiving,
                        R.drawable.icon_home_packing, R.drawable.icon_home_goods_receipt, R.drawable.icon_home_account_number,
                        R.mipmap.ic_launcher
                };
            }
        } catch (ConfigurationException e) {
           e.printStackTrace();
        }

        HomeRcvAdapter adapter = new HomeRcvAdapter(this,names,drawableArrays);
        rv.setLayoutManager(new GridLayoutManager(this,3));
        rv.addItemDecoration(new GrildSpaceItemDecoration(2));
        rv.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!(boolean)SPUtils.get(this, SPUtils.IS_LOGIN, false)){
             startActivity(new Intent(this, LoginActivity.class));
             ActivityManagerUtils.getInstance().finishActivity(this);
        }else {
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
            showToast("再按一次退出程序",0);
            exitTime = System.currentTimeMillis();
        } else {
            ActivityManagerUtils.getInstance().exit();
            Process.killProcess(Process.myPid());
            System.exit(0);
        }
    }


}
