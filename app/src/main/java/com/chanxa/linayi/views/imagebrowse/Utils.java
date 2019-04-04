package com.chanxa.linayi.views.imagebrowse;

import android.app.Activity;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;


public class Utils {


    public static String getThumbnailImageUrl(Activity activity, String imgUrl, int width, int height) {
        String url = "http://imgsize.ph.126.net/?imgurl=data1_data2xdata3x0x85.jpg&enlarge=true";
        width = (int) (activity.getResources().getDisplayMetrics().density * 150);
        height = (int) (activity.getResources().getDisplayMetrics().density * 150);
        url = url.replaceAll("data1", imgUrl).replaceAll("data2", width + "").replaceAll("data3", height + "");
        return url;
    }

    public static boolean isImageCacheAvailable(String url){
       return DiskCacheUtils.findInCache(url,ImageLoader.getInstance().getDiskCache()) != null
               && MemoryCacheUtils.findCachedBitmapsForImageUri(url,ImageLoader.getInstance().getMemoryCache()) != null;
    }

    public static void displayImageWithCache(String url, ImageView imageView, ImageLoadingListener loadingListener){
        ImageLoader.getInstance().displayImage(url,imageView,new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true).build(),loadingListener);
    }

}
