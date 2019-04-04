package com.chanxa.linayi;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.chanxa.autoupdatelibrary.utils.AutoUpDateUtils;
import com.chanxa.linayi.config.HttpUrl;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LargestLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import java.util.Stack;

public class App extends Application {

   public Stack<Activity> store;

    public App() {
        mInstance = this;
    }

    public static App mInstance;

    public static App getInstance() {
        if (mInstance == null) {
            mInstance = new App();
        }
        return mInstance;
    }

    private boolean isLocal;

    @Override
    public void onCreate() {
        super.onCreate();
        store = new Stack<>();
        Bugly.init(this, "6b56cd2c10", false);
        initImageLoaderConfig();
        autoUpDate();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
        Beta.installTinker();
    }



    /**
     * 版本更新
     */
    private void autoUpDate(){
        AutoUpDateUtils.Builder builder = new AutoUpDateUtils.Builder()
                .setBaseUrl(HttpUrl.CHECK_VERSION)
                //设置是否打印log日志
                .showLog(true)
                //设置请求方式
                .setRequestMethod(AutoUpDateUtils.Builder.METHOD_GET)
                .build();
        AutoUpDateUtils.init(builder);
    }

    /**
     * ImageLoader配置
     */
    private void initImageLoaderConfig() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(3) //线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) //将保存的时候的URI名称用MD5 加密
                .memoryCache(new LargestLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024) // 内存缓存的最大值
                .diskCacheSize(50 * 1024 * 1024)  // 50 Mb sd卡(本地)缓存的最大值
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                // 由原先的discCache -> diskCache
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();
        ImageLoader.getInstance().init(config);

    }


    public boolean getIsLocal() {
        return isLocal;
    }
}
