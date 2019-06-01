package com.chanxa.linayi.uis;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;

import com.chanxa.linayi.R;
import com.chanxa.linayi.tools.CreatFileUtils;
import com.chanxa.linayi.tools.DateTools;
import com.kevin.crop.UCrop;
import com.kevin.crop.util.BitmapLoadUtils;
import com.kevin.crop.view.GestureCropImageView;
import com.kevin.crop.view.OverlayView;
import com.kevin.crop.view.UCropView;

import java.io.File;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.OnClick;

public class ZoomActivity extends BaseActivity{

    @BindView(R.id.cropimage)
    UCropView mUCropView;

    GestureCropImageView mGestureCropImageView;
    OverlayView mOverlayView;

    public static final String ZOOM_URL = "zoom_url";

    private String url;
    private Uri mOutputUri;

    public static void startZoomActivity(Context context, String url, float ratioX, float ratioY, int maxSizeX, int maxSizeY){
        Intent intent = new Intent(context, ZoomActivity.class);
        intent.putExtra(ZOOM_URL, url);
        intent.putExtra(UCrop.EXTRA_ASPECT_RATIO_X, ratioX);
        intent.putExtra(UCrop.EXTRA_ASPECT_RATIO_Y, ratioY);
        intent.putExtra(UCrop.EXTRA_MAX_SIZE_SET, true);
        intent.putExtra(UCrop.EXTRA_ASPECT_RATIO_SET, true);
        intent.putExtra(UCrop.EXTRA_MAX_SIZE_X, maxSizeX);
        intent.putExtra(UCrop.EXTRA_MAX_SIZE_Y, maxSizeY);
        context.startActivity(intent);
    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_zoom;
    }

    @Override
    public void initView() {
        initCropView();
    }

    /**
     * 初始化裁剪View
     */
    private void initCropView() {
        mGestureCropImageView = mUCropView.getCropImageView();
        mOverlayView = mUCropView.getOverlayView();

        // 设置允许缩放
        mGestureCropImageView.setScaleEnabled(true);
        // 设置禁止旋转
        mGestureCropImageView.setRotateEnabled(false);
        // 设置外部阴影颜色
        mOverlayView.setDimmedColor(Color.parseColor("#AA000000"));
        // 设置周围阴影是否为椭圆(如果false则为矩形)
        mOverlayView.setOvalDimmedLayer(false);
        // 设置显示裁剪边框
        mOverlayView.setShowCropFrame(true);
        // 设置不显示裁剪网格
        mOverlayView.setShowCropGrid(false);
        mOverlayView.setPadding(0,0,0,0);
//        mOverlayView.setTargetAspectRatio(1);
        mGestureCropImageView.setPadding(0, 0, 0, 0);

        final Intent intent = getIntent();
        setImageData(intent);
    }

    private void setImageData(Intent intent) {



       // mOutputUri = Uri.fromFile(CreatFileUtils.changeFile(CreatFileUtils.getPhotoPath(), DataUtils.getCurrentDate() + ".jpg"));
        mOutputUri = Uri.fromFile(CreatFileUtils.changeFile(CreatFileUtils.getPhotoPath(),  DateTools.getCurrentDate("yyyy_MM_dd_HHmmss") + ".jpg"));
        if (intent != null) {
            url = intent.getStringExtra(ZOOM_URL);
        }
        if (url != null && mOutputUri != null) {
            try {
                mGestureCropImageView.setImageUri(Uri.fromFile(new File(url)));
            } catch (Exception e) {
                finish();
            }
        } else {
            finish();
        }

        // 设置裁剪宽高比
        if (intent.getBooleanExtra(UCrop.EXTRA_ASPECT_RATIO_SET, false)) {
            float aspectRatioX = intent.getFloatExtra(UCrop.EXTRA_ASPECT_RATIO_X, 1);
            float aspectRatioY = intent.getFloatExtra(UCrop.EXTRA_ASPECT_RATIO_Y, 1);

            if (aspectRatioX > 0 && aspectRatioY > 0) {
                mGestureCropImageView.setTargetAspectRatio(aspectRatioX / aspectRatioY);
            } else {
                mGestureCropImageView.setTargetAspectRatio(com.kevin.crop.view.CropImageView.SOURCE_IMAGE_ASPECT_RATIO);
            }
        }

        // 设置裁剪的最大宽高
        if (intent.getBooleanExtra(UCrop.EXTRA_MAX_SIZE_SET, false)) {
            int maxSizeX = intent.getIntExtra(UCrop.EXTRA_MAX_SIZE_X, 0);
            int maxSizeY = intent.getIntExtra(UCrop.EXTRA_MAX_SIZE_Y, 0);

            if (maxSizeX > 0 && maxSizeY > 0) {
                mGestureCropImageView.setMaxResultImageSizeX(maxSizeX);
                mGestureCropImageView.setMaxResultImageSizeY(maxSizeY);
            } else {
                Log.w("ZoomActivity", "EXTRA_MAX_SIZE_X and EXTRA_MAX_SIZE_Y must be greater than 0");
            }
        }
    }

    @OnClick(R.id.btn_ok)
    public void ok(){
        try {
            cropAndSaveImage();
        } catch (IllegalArgumentException e) {
            Log.e("错误","方框不能移动到屏幕外！");
        }
    }

    @OnClick(R.id.btn_close)
    public void close(){
        finish();
    }

    private void cropAndSaveImage() {
        OutputStream outputStream = null;
        try {
            final Bitmap croppedBitmap = mGestureCropImageView.cropImage();
            if (croppedBitmap != null) {
                outputStream = getContentResolver().openOutputStream(mOutputUri);
                croppedBitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream);
                croppedBitmap.recycle();
                //发送广播
                Intent intent=new Intent();
                intent.setAction("Pic");
                intent.putExtra("uri",mOutputUri.getPath());
                sendBroadcast(intent);
                finish();
            } else {
                finish();
            }
        } catch (Exception e) {
            finish();
        } finally {
            BitmapLoadUtils.close(outputStream);
        }
    }
}

























