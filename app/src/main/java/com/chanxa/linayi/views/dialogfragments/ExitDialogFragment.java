package com.chanxa.linayi.views.dialogfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chanxa.linayi.R;



public class ExitDialogFragment extends DialogFragment {

    ExitDialogClickListener listener;
    public void setClickListener(ExitDialogClickListener exitDialogClickListener){
        this.listener=exitDialogClickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE,R.style.mystyle);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=LayoutInflater.from(getContext()).inflate(R.layout.dialog_ask_logout,null);
        TextView tv_sure=view.findViewById(R.id.tv_sure);
        TextView tv_cancle=view.findViewById(R.id.tv_cancel);


        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.sure();
            }
        });

        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.cancle();
            }
        });
        return view;
    }


    public interface ExitDialogClickListener{
        void sure();
        void cancle();
    }

}
