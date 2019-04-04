package com.chanxa.linayi.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chanxa.linayi.Interface.BasePresenter;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/3/27.
 */

public abstract class BaseFragments extends Fragment {
    public Context mContext;
    protected final String TAG = this.getClass().getSimpleName();
    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private  final int MIN_CLICK_DELAY_TIME = 1000;
    private  long lastClickTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        View view = View.inflate(mContext, getLayoutResId(), null);
        ButterKnife.bind(this,view);
        initData();
        return view;
    }

    /**
     * 返回资源的布局
     *
     * @return
     */
    public abstract int getLayoutResId();


    /**
     * 页面初始化页面数据，在initView之后调用
     */
    public abstract void initData();


    /**
     * 防止快速点击
     */

    public  boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    /**
     * fragment可见的时候操作，取代onResume，且在可见状态切换到可见的时候调用
     */
    protected void onVisible() {

    }

    /**
     * fragment不可见的时候操作,onPause的时候,以及不可见的时候调用
     */
    protected void onHidden() {

    }


    @Override
    public void onResume() {//和activity的onResume绑定，Fragment初始化的时候必调用，但切换fragment的hide和visible的时候可能不会调用！
        super.onResume();
        if (isAdded() && !isHidden()) {//用isVisible此时为false，因为mView.getWindowToken为null
            onVisible();
        }
    }

    @Override
    public void onPause() {
        if (isVisible())
            onHidden();
        super.onPause();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {//默认fragment创建的时候是可见的，但是不会调用该方法！切换可见状态的时候会调用，但是调用onResume，onPause的时候却不会调用
        super.onHiddenChanged(hidden);
        if (!hidden) {
            onVisible();
        } else {
            onHidden();
        }
    }

}
