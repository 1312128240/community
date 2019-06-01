package com.chanxa.linayi.views.dialogfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chanxa.linayi.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class AlterDialogFragment  extends DialogFragment implements View.OnClickListener {

    private String title;

    public CustAlterDialgoInterface custAlterDialgoInterface;

    public void setOnClick(CustAlterDialgoInterface click){
        this.custAlterDialgoInterface=click;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            title = bundle.getString("title");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.alter_dialog_fragement,null);
        TextView tv_cancle=view.findViewById(R.id.tv_alter_dialog_cancle);
        TextView tv_sure=view.findViewById(R.id.tv_alter_dialog_sure);
        TextView tv_title=  view.findViewById(R.id.tv_alter_dialog_title);
        tv_title.setText(title);
        tv_cancle.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        return view;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
//        super.show(manager, tag);
        try {
            Class c = Class.forName("android.support.v4.app.DialogFragment");
            Constructor con = c.getConstructor();
            Object obj = con.newInstance();
            Field dismissed = c.getDeclaredField(" mDismissed");
            dismissed.setAccessible(true);
            dismissed.set(obj, false);
            Field shownByMe = c.getDeclaredField("mShownByMe");
            shownByMe.setAccessible(true);
            shownByMe.set(obj, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_alter_dialog_cancle:
                if(custAlterDialgoInterface!=null){
                    custAlterDialgoInterface.cancle();
                }
                break;
            case R.id.tv_alter_dialog_sure:
                if(custAlterDialgoInterface!=null){
                    custAlterDialgoInterface.sure();
                }

                break;
        }
    }

    public interface CustAlterDialgoInterface{
        void cancle();
        void sure();
    }
}
