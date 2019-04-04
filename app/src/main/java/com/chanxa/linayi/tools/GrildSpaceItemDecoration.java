package com.chanxa.linayi.tools;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2019/3/30.
 */

public class GrildSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public GrildSpaceItemDecoration(int space) {
        this.space=space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left=space;
        //outRect.right=space/2;
        outRect.bottom=space;

    }
}
