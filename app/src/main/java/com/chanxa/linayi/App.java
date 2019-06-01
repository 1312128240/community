package com.chanxa.linayi;

import android.app.Activity;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import java.util.Stack;

public class App extends TinkerApplication {

    public Stack<Activity> store;
    private boolean isLocal;
    public static App mInstance;

    public App() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.chanxa.linayi.BuglyApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
        mInstance = this;
    }


    public static App getInstance() {
        if (mInstance == null) {
            mInstance = new App();
        }
        return mInstance;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        store = new Stack<>();
    }


    public boolean getIsLocal() {
        return isLocal;
    }

}
