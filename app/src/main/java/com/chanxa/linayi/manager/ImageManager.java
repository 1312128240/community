
package com.chanxa.linayi.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;

import com.chanxa.linayi.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


import java.io.File;
import java.io.IOException;

public class ImageManager {

    private static ImageManager instance;

    public interface UploadCompleteListener {
        void onComplete(String key);

        void onFailure();
    }

    public static ImageManager getInstance(Context context) {
        if (instance == null) {
            instance = new ImageManager(context.getApplicationContext());
        }
        return instance;
    }

    public ImageManager(Context context) {

    }

    public void loadImage(Context context, String url, ImageView image) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (!url.startsWith("http://")) {
            url = CallHttpManager.BASE_URL_IMAGE + url;
        }
        Glide.with(context).load(url + "?imageView2/2/w/600").into(image);
    }

    public String getAbsoluteImgUrl (String url){
        if (!url.startsWith("http://")) {
            url = CallHttpManager.BASE_URL_IMAGE + url;
        }

        return url;
    }

    public void loadImage(Context context, String url, ImageView image, int defaut) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        if (!url.startsWith("http://")) {
            url = CallHttpManager.BASE_URL_IMAGE + url;
        }
        Log.e("ImageManager", "loadImage: " + url );
        Glide.with(context).load(url).asBitmap().centerCrop().placeholder(defaut).into(image);
    }



    public void loadStepLargeImage(final Context context, String url, final ImageView image, int defaut) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        if (!url.startsWith("http://")) {
            url = CallHttpManager.BASE_URL_IMAGE + url;
        }

        Glide.with(context).load(url)
                .override(1080, 650).fitCenter()
                .placeholder(R.mipmap.default_image).into(image);

    }

    public void loadOfficalImage(Context context, String url, ImageView image, int w, int h) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        if (!url.startsWith("http://")) {
            url = CallHttpManager.BASE_URL_IMAGE + url;
        }

        Picasso.get().load(url).resize(w, h).centerCrop().placeholder(R.mipmap.default_image).into(image);
    }

    public void loadImageRecipes(Context context, String url, ImageView image, int defaut) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        if (!url.startsWith("http://")) {
            url = CallHttpManager.BASE_URL_IMAGE + url;
        }
        //Glide.with(context).load(url).override(500, 250).centerCrop().placeholder(R.mipmap.default_image).thumbnail(0.5f).into(image);
        Picasso.get().load(url).resize(500, 250).centerCrop().placeholder(defaut).into(image);
    }

    public void loadImageMyRecipes(Context context, String url, ImageView image, int defaut) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        if (!url.startsWith("http://")) {
            url = CallHttpManager.BASE_URL_IMAGE + url;
        }
        //Glide.with(context).load(url).centerCrop().override(500, 250).thumbnail(0.5f).placeholder(R.mipmap.default_image).into(image);
        Picasso.get().load(url).centerCrop().resize(500, 250).placeholder(defaut).into(image);
    }

    public void loadImageWork(Context context, String url, ImageView image) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        if (!url.startsWith("http://")) {
            url = CallHttpManager.BASE_URL_IMAGE + url;
        }
        Picasso.get().load(url).resize(200, 200).placeholder(R.mipmap.default_image).into(image);
    }

    public void loadImageRecipes(Context context, String url, ImageView image, int w, int h) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        if (!url.startsWith("http://")) {
            url = CallHttpManager.BASE_URL_IMAGE + url;
        }

        Picasso.get().load(url).resize(w, h).centerCrop().placeholder(R.mipmap.default_image).into(image);
    }

    public void loadDynamicImage(Context context, String url, ImageView image, int defaut) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        if (!url.startsWith("http://")) {
            url = CallHttpManager.BASE_URL_IMAGE + url;
        }
        Picasso.get().load(url + "?imageView2/2/w/200").resize(200, 200).centerCrop().placeholder(defaut).into(image);
    }

    public void loadDynamicImage(Context context, String url, ImageView image, int size, int defaut) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        if (!url.startsWith("http://")) {
            url = CallHttpManager.BASE_URL_IMAGE + url;
        }

        Picasso.get().load(url + "?imageView2/2/w/" + size).resize(size, size).centerCrop().placeholder(defaut).into(image);
    }

    public void loadChatImage(Context context, Uri uri, ImageView image) {
        if (uri == null) {
            return;
        }
        Picasso.get().load(uri).into(image);
    }

    public void loadChatLocationImage(Context context, double latitude, double longitude, ImageView image) {
        String string = "http://restapi.amap.com/v3/staticmap?location=" + latitude + "," + longitude + "&zoom=15&size=450*300&markers=mid,,A:" +
                latitude + "," + longitude + "&key=ee95e52bf08006f63fd29bcfbcf21df0";
        Uri uri = Uri.parse(string);
        if (uri == null) {
            return;
        }
        Glide.with(context).load(uri).into(image);
    }

    public void loadChatImage(Context context, String url, ImageView image) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        if (!url.startsWith("http://")) {
            url = CallHttpManager.BASE_URL_IMAGE + url;
        }

        Glide.with(context).load(url).into(image);
    }


    public void loadImage(Context context, File file, ImageView image) {
        if (file == null) {
            return;
        }
        Glide.with(context).load(file).into(image);
    }

    public void loadNoImage(Context context, String url, ImageView image) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        if (!url.startsWith("http://")) {
            url = CallHttpManager.BASE_URL_IMAGE + url;
        }

        Glide.with(context).load(url).into(image);
    }

    public void loadCenterImage(Context context, File file, ImageView image, int w, int h) {
        if (file == null) {
            return;
        }
        Picasso.get().load(file).resize(w, h).centerCrop().into(image);
    }

    public void loadTypeImage(Context context, String url, ImageView image, int w, int h) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Picasso.get().load(CallHttpManager.BASE_URL_IMAGE + url).resize(w, h).placeholder(R.mipmap.default_image).centerCrop().into(image);
    }

    public void loadCenterImage(Context context, String url, ImageView image, int w, int h) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        if (!url.startsWith("http://")) {
            url = CallHttpManager.BASE_URL_IMAGE + url;
        }

        Picasso.get().load(url).resize(w, h).centerCrop().into(image);
    }

    public void loadCenterImage(Context context, String url, ImageView image, int defaut, int w, int h) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        if (!url.startsWith("http://")) {
            url = CallHttpManager.BASE_URL_IMAGE + url;
        }

        Picasso.get().load(url).resize(w, h).centerCrop().placeholder(defaut).into(image);
    }

    //    http://78re52.com1.z0.glb.clouddn.com/resource/gogopher.jpg?imageView2/2/w/200
    public void loadThumbleImage(Context context, String url, ImageView image) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        if (!url.startsWith("http://")) {
            url = CallHttpManager.BASE_URL_IMAGE + url;
        }

        Glide.with(context).load(url + "?imageView2/2/w/200").into(image);
    }

    public void loadThumbleImage(Context context, String url, ImageView image, int defaut) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        if (!url.startsWith("http://")) {
            url = CallHttpManager.BASE_URL_IMAGE + url;
        }

        Picasso.get().load(url).centerCrop().resize(300, 300).placeholder(defaut).into(image);
    }


    public void loadAvatarImage(Context context, String url, ImageView image, int de) {
        if (TextUtils.isEmpty(url)) {
            image.setImageResource(de);
            return;
        }

        if (!url.startsWith("http://")) {
            url = CallHttpManager.BASE_URL_IMAGE + url;
        }

        Picasso.get().load(url + "?imageView2/2/w/100").centerCrop().resize(100, 100).placeholder(de).into(image);
    }

    public void loadTopImage(Context context, String url, ImageView image, int de) {
        if (TextUtils.isEmpty(url)) {
            image.setImageResource(de);
            return;
        }

        if (!url.startsWith("http://")) {
            url = CallHttpManager.BASE_URL_IMAGE + url;
        }

        Picasso.get().load(url).centerCrop().resize(100, 100).placeholder(de).into(image);
    }


    public void loadAvatarImage(Context context, Uri uri, ImageView image, int de) {
        if (uri == null) {
            image.setImageResource(de);
            return;
        }
        Picasso.get().load(uri).error(de).placeholder(de).into(image);
    }

    public void loadBitmap(Context context, String url, Target target) {
        if (!url.startsWith("http://")) {
            url = CallHttpManager.BASE_URL_IMAGE + url;
        }
        Picasso.get().load(url + "?imageView2/2/w/100").into(target);
    }

    public void loadBigerBitmap(Context context, String url, Target target) {
        if (!url.startsWith("http://")) {
            url = CallHttpManager.BASE_URL_IMAGE + url;
        }
        Picasso.get().load(url + "?imageView2/2/w/600").into(target);
    }

    public void loadPushBitmap(Context context, String url, Target target) {
        if (!url.startsWith("http://")) {
            url = CallHttpManager.BASE_URL_IMAGE + url;
        }
        Picasso.get().load(url).into(target);
    }

    public Bitmap getLoadBitmap(Context context, String url) throws IOException {

        if (!url.startsWith("http://")) {
            url = CallHttpManager.BASE_URL_IMAGE + url;
        }
        return Picasso.get().load(url).get();
    }

    public Bitmap getLoadThumbBitmap(Context context, String url) throws IOException {
        if (!url.startsWith("http://")) {
            url = CallHttpManager.BASE_URL_IMAGE + url;
        }
        return Picasso.get().load(url + "?imageView2/2/w/30").get();
    }

    public void cancelTarget(Context context, Target target) {
        Picasso.get().cancelRequest(target);
    }

    public static String parseUrl(String url) {
        if (!url.startsWith("http://")) {
            url = CallHttpManager.BASE_URL_IMAGE + url;
        }
        return url;
    }

    /**
     * 自适应宽度加载图片。保持图片的长宽比例不变，通过修改imageView的高度来完全显示图片。
     */
    public static void loadIntoUseFitWidth(Context context, final String imageUrl, int errorImageId, final ImageView imageView) {
        Glide.with(context)
                .load(CallHttpManager.BASE_URL_IMAGE + imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, com.bumptech.glide.request.target.Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, com.bumptech.glide.request.target.Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (imageView == null) {
                            return false;
                        }
                        if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                        float scale = (float) vw / (float) resource.getIntrinsicWidth();
                        int vh = Math.round(resource.getIntrinsicHeight() * scale);
                        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                        imageView.setLayoutParams(params);
                        return false;
                    }

                })
                .placeholder(errorImageId)
                .error(errorImageId)
                .into(imageView);
    }
}
