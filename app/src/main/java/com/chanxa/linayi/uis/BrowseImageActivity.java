package com.chanxa.linayi.uis;


import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chanxa.linayi.R;

import butterknife.BindView;
import butterknife.OnClick;

public class BrowseImageActivity extends BaseActivity {

    @BindView(R.id.iv_browse)
    ImageView iv_browse;

    @Override
    public int getLayoutId() {
        return R.layout.activity_browse_image;
    }

    @Override
    public void initView() {
        initIv();
    }

    private void initIv() {
        String imgUrl=getIntent().getStringExtra("imgUrl");
        Glide.with(this).load(imgUrl).error(R.mipmap.default_image).into(iv_browse);
    }



    @OnClick(R.id.parent)
    public void back(){
        onBackPressed();
    }


}
