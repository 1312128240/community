package com.chanxa.linayi.uis.Task;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chanxa.linayi.R;
import com.chanxa.linayi.fragments.TaskOverFragment;
import com.chanxa.linayi.fragments.TaskingFragment;
import com.chanxa.linayi.uis.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class TaskHomeActivity extends BaseActivity{

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.rb_tasking)
    RadioButton rb_tasking;
    @BindView(R.id.rb_taskover)
    RadioButton rb_taskover;
    @BindView(R.id.vp_task)
    ViewPager vp;

    @Override
    public int getLayoutId() {
        return R.layout.activity_task_home;
    }

    @Override
    public void initView() {
        tv_title.setText("任务空间");
        initRg();

        initViewPager();
    }

    private void initViewPager() {
        final List<Fragment> fragments=new ArrayList<>();

        fragments.add(new TaskingFragment());
        fragments.add(new TaskOverFragment());

        final FragmentPagerAdapter fragmentAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };

        vp.setAdapter(fragmentAdapter);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                 if(position==0){
                     rb_tasking.setChecked(true);
                 }else {
                     rb_taskover.setChecked(true);
                 }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initRg() {
       rb_tasking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    vp.setCurrentItem(0);
                    rb_taskover.setChecked(false);
                }else {
                    rb_taskover.setChecked(true);
                }
           }
       });

        rb_taskover.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    vp.setCurrentItem(1);
                    rb_tasking.setChecked(false);
                }else {
                    rb_tasking.setChecked(true);
                }
            }
        });

        rb_tasking.setChecked(true);
    }

    @OnClick(R.id.tv_actionbar_back)
    public void back(){
        onBackPressed();
    }

}
