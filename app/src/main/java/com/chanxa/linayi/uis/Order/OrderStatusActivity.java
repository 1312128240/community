package com.chanxa.linayi.uis.Order;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.TextView;

import com.chanxa.linayi.R;
import com.chanxa.linayi.fragments.AllOrderFragment;
import com.chanxa.linayi.fragments.BaseFragments;
import com.chanxa.linayi.fragments.BuyingOrderFragment;
import com.chanxa.linayi.fragments.DeliveryingFragment;
import com.chanxa.linayi.fragments.ReceivedFragment;
import com.chanxa.linayi.uis.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


public class OrderStatusActivity extends BaseActivity {

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.tv1t)
    TextView tv1;
    @BindView(R.id.tv2t)
    TextView tv2;
    @BindView(R.id.tv3t)
    TextView tv3;

    private Fragment currentFragment;
    private FragmentTransaction transaction;
    private FragmentManager manager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_status;
    }

    @Override
    public void initView() {
        tv_title.setText("订单查看");
        tv1.setText("订单编号");
        tv2.setText("顾客名字");
        tv3.setText("订单金额(元)");
        initTab();
    }

    private void initTab() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index=tab.getPosition();
                switch (index){
                    case 0:
                       addFragment("all");
                        break;
                    case 1:
                       addFragment("buying");
                        break;
                    case 2:
                       addFragment("deliverying");
                        break;
                    case 3:
                       addFragment("received");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        final String[] str = {"全部订单", "采买中", "送货中", "已收货"};
        for (int i = 0; i <str.length ; i++) {
            if(i==0){
                tabLayout.addTab(tabLayout.newTab().setText(str[0]),true);
            }else {
                tabLayout.addTab(tabLayout.newTab().setText(str[i]));
            }

        }
    }



    private void addFragment(String fTag) {
        //判断这个标签是否存在Fragment对象,如果存在则返回，不存在返回null
        manager = getSupportFragmentManager();
        Fragment fragment =getSupportFragmentManager().findFragmentByTag(fTag);
        // 如果这个fragment不存于栈中
        if (fragment == null) {
            //初始化Fragment事物
            transaction = manager.beginTransaction();
            //根据RaioButton点击的Button传入的tag，实例化，添加显示不同的Fragment
            if (fTag.equals("all")) {
                fragment = new AllOrderFragment();
            } else if (fTag.equals("buying")) {
                fragment = new BuyingOrderFragment();
            } else if (fTag.equals("deliverying")) {
                fragment = new DeliveryingFragment();
            }else if(fTag.equals("received")){
                fragment=new ReceivedFragment();
            }
            //在添加之前先将上一个Fragment隐藏掉
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.framelayout, fragment, fTag);
         //   transaction.addToBackStack(null);
            transaction.commit();
            //更新可见
            currentFragment = fragment;
        } else {
            //如果添加的Fragment已经存在，则将隐藏掉的Fragment再次显示,
            transaction = manager.beginTransaction();
            transaction.show(fragment);
            transaction.hide(currentFragment);
            //更新可见
            currentFragment = fragment;
          //  transaction.addToBackStack(null);
            transaction.commit();
        }

    }


    @OnClick(R.id.tv_actionbar_back)
    public void back() {
        onBackPressed();
    }
}
