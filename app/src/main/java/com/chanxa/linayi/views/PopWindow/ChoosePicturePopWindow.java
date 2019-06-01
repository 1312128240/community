
package com.chanxa.linayi.views.PopWindow;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chanxa.linayi.R;
import com.chanxa.linayi.uis.ZoomActivity;


import java.io.File;
import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;


public class ChoosePicturePopWindow extends PopupWindow implements View.OnClickListener, PreferenceManager.OnActivityResultListener {
    /**单张*/
    public static final int MODE_SINGLE = 0;
    /**多张*/
    public static final int MODE_MULTI = 1;

    /**上下文*/
    private Context mContext;
    /**是否是单张，0单张，1多张*/
    private int isSingle = 0;
    /**是否需要裁剪,false不裁剪，true需要裁剪*/
    private boolean isNeedCrop = false;
    /**最多照片的张数*/
    private int picNum;
    /**返回本地地址监听*/
    private ReturnLocalPathListener listener;
    /**保存剪切图片的路径*/
    private ArrayList<String> localPaths;
    /**保存本地的图片路径*/
    private String path;

    public ChoosePicturePopWindow(Context mContext) {
        super(mContext);
        this.mContext = mContext;

        this.picNum = 1;
        this.isSingle = MODE_SINGLE;
        this.isNeedCrop = true;
        localPaths = new ArrayList<String>();
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/linayicommunity" + System.currentTimeMillis() + ".jpg";

        initView();
    }


    private void initView() {
        View view= LayoutInflater.from(mContext).inflate(R.layout.choose_picture_layout,null);
        setContentView(view);
        setHeight(FrameLayout.LayoutParams.MATCH_PARENT);
        setWidth(FrameLayout.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(0x70000000));
        setAnimationStyle(R.style.ActionSheetDialogAnimation);


        TextView tv_takephoto=view.findViewById(R.id.tv_take_photo);
        TextView tv_picture=view.findViewById(R.id.tv_picture);
        TextView tv_cancel=view.findViewById(R.id.tv_cancel);

        tv_takephoto.setOnClickListener(this);
        tv_picture.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_take_photo://拍照
                dismiss();
                intentCamera();
                break;

            case R.id.tv_picture://我的相册选择
                dismiss();
                startLocalImageActivity((Activity) mContext,102, picNum, isSingle, localPaths);
                break;

            case R.id.tv_cancel://取消
                dismiss();
                if(listener!=null) {
                    listener.onDialogCancel();
                }
                break;
        }
    }


    /**
     * 照相
     */
    private void intentCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri mImageCaptureUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//如果是7.0android系统
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, new File(path).getAbsolutePath());
            mImageCaptureUri= mContext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
        }else{
            mImageCaptureUri = Uri.fromFile(new File(path));
        }
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        ((Activity) mContext).startActivityForResult(intent,101);
    }


    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        return true;
    }

    public void setActivityResult(int requestCode, int resultCode, final Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 101://照相返回
                    //如果需要裁剪
                    if (isNeedCrop) {
                        ZoomActivity.startZoomActivity(mContext, path , 1f, 1f, 500, 500);
                    } else {
                        localPaths.add(path);
                        if (listener != null) {
                            listener.returnTakePictureLocalPath(path);
                        }
                    }

                    break;

                case 102://从相册中选择
                    if (data != null) {
                        ArrayList<String> strings = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                        if (strings == null || strings.size() <= 0) {
                            return;
                        }
                        //如果需要裁剪
                        if (isNeedCrop) {
                            ZoomActivity.startZoomActivity(mContext, strings.get(0), 1f, 1f, 500, 500);
                        } else {
                            if (listener != null) {
                                listener.returnSelectPictureLocalPath(strings);
                            }
                        }
                    }

                    break;
            }
        }
    }

    /**
     * 进入本地相册选择照片
     * @param activity
     * @param requestCode 请求码
     * @param picNum 照片数量
     * @param select_mode 选择的模式
     * @param mSelectPath 照片本地地址集合
     */
    public static void startLocalImageActivity(Activity activity, int requestCode, int picNum, int select_mode, ArrayList<String> mSelectPath) {
        Intent intent = new Intent(activity, MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, false);
        // 最大可选择图片数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, picNum <= 0 ? 1 : picNum);
        // 选择模式
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, select_mode == MODE_SINGLE ? MODE_SINGLE : MODE_MULTI);
        // 默认选择
        if (mSelectPath != null && mSelectPath.size() > 0) {
            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
        }
        activity.startActivityForResult(intent, requestCode);
    }


    /**
     * 返回本地地址接口,不需要裁剪才回调，需要裁剪不用写回调
     */
    public interface ReturnLocalPathListener {
        //拍照不裁剪放回
        void returnTakePictureLocalPath(String localPath);

        //相册选择返回
        void returnSelectPictureLocalPath(ArrayList<String> localPathList);

        void onDialogCancel();
    }



}
