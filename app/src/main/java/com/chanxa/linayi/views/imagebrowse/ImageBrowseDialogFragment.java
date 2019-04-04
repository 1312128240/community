package com.chanxa.linayi.views.imagebrowse;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanxa.linayi.R;
import com.chanxa.linayi.views.imagebrowse.widget.ImageInfo;
import com.chanxa.linayi.views.imagebrowse.widget.MaterialProgressBar;
import com.chanxa.linayi.views.imagebrowse.widget.PhotoView;
import com.chanxa.linayi.manager.ImageManager;
import com.chanxa.linayi.tools.AppUtils;
import com.chanxa.linayi.tools.ToastUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class ImageBrowseDialogFragment extends DialogFragment {

    private ViewPager viewPager;
    private TextView tips; //viewpager indicator
    private ArrayList<String> imageUrls;
    private ImageInfo imageInfo;
    private View mask;//background view
    private ArrayList<ImageInfo> imageInfos;
    private int position;
    private boolean isExit = false;


    public static void enterZoomImageView(FragmentActivity mContent, PhotoView view, ArrayList<ImageInfo> imgImageInfos, ArrayList<String> imgList, int position) {
        if (view.isEnabled()) {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(ImageInfo.INTENT_IMAGE_URLS, imgList);
            bundle.putInt(ImageInfo.INTENT_CLICK_IMAGE_POSITION, position);
            bundle.putParcelable(ImageInfo.INTENT_CLICK_IMAGE_INFO, view.getInfo());
            bundle.putParcelableArrayList(ImageInfo.INTENT_IMAGE_INFOS, imgImageInfos);
            ImageBrowseDialogFragment.newInstance(bundle).show(mContent.getSupportFragmentManager(), ImageBrowseDialogFragment.class.getSimpleName());
        }
    }

    public static void enterZoomImageView(FragmentManager manager, PhotoView view, ArrayList<ImageInfo> imgImageInfos, ArrayList<String> imgList, int position) {
        if (view.isEnabled()) {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(ImageInfo.INTENT_IMAGE_URLS, imgList);
            bundle.putInt(ImageInfo.INTENT_CLICK_IMAGE_POSITION, position);
            bundle.putParcelable(ImageInfo.INTENT_CLICK_IMAGE_INFO, view.getInfo());
            bundle.putParcelableArrayList(ImageInfo.INTENT_IMAGE_INFOS, imgImageInfos);
            ImageBrowseDialogFragment.newInstance(bundle).show(manager, ImageBrowseDialogFragment.class.getSimpleName());
        }
    }

    public static ImageBrowseDialogFragment newInstance(Bundle imgs) {
        ImageBrowseDialogFragment fragment = new ImageBrowseDialogFragment();
        fragment.setArguments(imgs);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.DialogTheme);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /**NOTE if the anchor activity is FullScreen,the following code must be used.
         and {@link ImageInfo#correct(int[], int)} the second params must be Zero..
         */
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

    }

    View v;
    int maxHeight;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_viewpager, null);
        v.setSystemUiVisibility(View.INVISIBLE);
        WindowManager manager = getActivity().getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        int statusBarHeight = AppUtils.getStatusBarHeight(getActivity());
        maxHeight = height - statusBarHeight;
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tips = (TextView) view.findViewById(R.id.text);
        mask = view.findViewById(R.id.mask);

        runEnterAnimation();
        Bundle bundle = getArguments();
        imageUrls = bundle.getStringArrayList(ImageInfo.INTENT_IMAGE_URLS);
        imageInfos = bundle.getParcelableArrayList(ImageInfo.INTENT_IMAGE_INFOS);
        imageInfo = bundle.getParcelable(ImageInfo.INTENT_CLICK_IMAGE_INFO);
        position = bundle.getInt(ImageInfo.INTENT_CLICK_IMAGE_POSITION, 0);

        tips.setText((position + 1) + "/" + imageUrls.size());
        //设置照片间的间距
        viewPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));

        viewPager.setAdapter(new PagerAdapter() {
                                 @Override
                                 public int getCount() {
                                     return imageUrls.size();
                                 }

                                 @Override
                                 public boolean isViewFromObject(View view, Object object) {
                                     return view == object;
                                 }

                                 @Override
                                 public Object instantiateItem(ViewGroup container, final int pos) {
                                     View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_view_detail, null, false);
                                     final PhotoView photoView = (PhotoView) view.findViewById(R.id.image_detail);
                                     photoView.setMaxHeight(maxHeight);
                                     final PhotoView thumbnailView = (PhotoView) view.findViewById(R.id.image_thumbnail);
                                     final MaterialProgressBar progressBar = (MaterialProgressBar) view.findViewById(R.id.progress);
                                     //原图地址
                                     final String imgUrl =  ImageManager.getInstance(getContext()).getAbsoluteImgUrl(imageUrls.get(pos));
                                     //缩略图地址
//                                     final String thumbnailImageUrl = Utils.getThumbnailImageUrl(getActivity(), imgUrl, 0, 0);
                                     //full image cache is available
                                     if (Utils.isImageCacheAvailable(imgUrl)) {
                                         //only animation in where you click
                                         if (pos == position) {
                                             photoView.animateFrom(imageInfo);
                                         }
                                         progressBar.setVisibility(View.GONE);
                                         thumbnailView.setVisibility(View.GONE);
                                         Utils.displayImageWithCache(imgUrl, photoView, null);
                                     } else {
                                         Utils.displayImageWithCache(imgUrl, photoView, null);
                                         progressBar.setVisibility(View.GONE);
                                         //if we had thumbnail image cache available
//                                         if (Utils.isImageCacheAvailable(thumbnailImageUrl)) {
//                                             thumbnailView.setVisibility(View.VISIBLE);
//                                         }
//
//                                         //加载缩略图
//                                         Utils.displayImageWithCache(thumbnailImageUrl, thumbnailView, new SimpleImageLoadingListener() {
//                                             @Override
//                                             public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                                                 if (pos == position) {
//                                                     thumbnailView.animateFrom(imageInfo);
//                                                 }
//                                                 new Handler().postDelayed(new Runnable() {
//                                                     @Override
//                                                     public void run() {
//                                                         //加载原图
//                                                         Utils.displayImageWithCache(imgUrl, photoView, new SimpleImageLoadingListener() {
//                                                             @Override
//                                                             public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                                                                 progressBar.setVisibility(View.GONE);
//                                                                 thumbnailView.setVisibility(View.GONE);
//                                                                 if (pos == position) {
//                                                                     photoView.animateFrom(thumbnailView.getInfo());
//                                                                 }
//                                                             }
//
//                                                             @Override
//                                                             public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//                                                                 ToastUtil.showShort(getActivity(), R.string.image_load_error);
//                                                             }
//                                                         });
//                                                     }
//                                                 }, 300);
//                                             }
//
//                                             @Override
//                                             public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//                                                 ToastUtil.showShort(getActivity(), R.string.image_load_error);
//                                             }
//                                         });
//

                                     }

                                     //force to get focal point,to listen key listener
                                     photoView.setFocusableInTouchMode(true);
                                     photoView.requestFocus();
                                     //add key listener to listen back press
                                     photoView.setOnKeyListener(pressKeyListener);
                                     photoView.setOnClickListener(onClickListener);
                                     photoView.setTag(pos);
                                     photoView.enable();

                                     //长按保存图片
                                     photoView.setOnLongClickListener(new View.OnLongClickListener()

                                                                      {
                                                                          @Override
                                                                          public boolean onLongClick(View v) {
                                                                              try {
                                                                                  //保存弹窗
                                                                                  showSaveDialog(imageUrls.get(pos));
                                                                              } catch (Exception e) {
                                                                                  e.printStackTrace();
                                                                              }

                                                                              return false;
                                                                          }
                                                                      }

                                     );
//                                     photoView.setScaleType(ImageActivity.ScaleType.FIT_CENTER);

                                     container.addView(view);
                                     return view;
                                 }

                                 @Override
                                 public void destroyItem(ViewGroup container, int position, Object object) {
                                     container.removeView((View) object);
                                 }
                             }

        );

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()

                                          {
                                              @Override
                                              public void onPageSelected(int position) {
                                                  super.onPageSelected(position);
                                                  tips.setText((position + 1) + "/" + imageUrls.size());
                                              }
                                          }

        );

        //set current position
        viewPager.setCurrentItem(position);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            exitFragment(v);
        }
    };

    private void exitFragment(View v) {
        //退出时点击的位置
        int position = (int) v.getTag();
        //回到上个界面该view的位置
        if (((FrameLayout) v.getParent()).getChildAt(1).getVisibility() == View.VISIBLE) {
            dismissAllowingStateLoss();
        } else {
            runExitAnimation(v);
            ((PhotoView) v).animateTo(imageInfos.get(position), new Runnable() {
                @Override
                public void run() {
                    dismissAllowingStateLoss();
                }
            });
        }
    }

    private View.OnKeyListener pressKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {//只监听返回键
                if (event.getAction() != KeyEvent.ACTION_UP) {
                    return true;
                }
                if (!isExit) {
                    isExit = true;
                    exitFragment(v);
                }
                return true;
            }
            return false;
        }
    };

    private void runEnterAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(300);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        mask.startAnimation(alphaAnimation);
    }

    public void runExitAnimation(final View view) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(300);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mask.setVisibility(View.GONE);
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mask.startAnimation(alphaAnimation);
    }


    /**
     * 长按保存弹窗
     */
    private void showSaveDialog(final String imageUrl) {

        final AlertDialog mDialog = new AlertDialog.Builder(getActivity(), R.style.ActionSheetDialogStyle).create();
        mDialog.show();
        Window window = mDialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setContentView(R.layout.dialog_save_image);
        window.setGravity(Gravity.BOTTOM);

        //确定按钮
        TextView btn_ok = (TextView) window.findViewById(R.id.btn_ok);
        //设置确定按钮的点击
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //关闭弹窗
                    mDialog.dismiss();
                    //保存照片到本地
                    loadImageBitmap(imageUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //取消按钮
        TextView btn_cancel = (TextView) window.findViewById(R.id.btn_cancel);
        //设置取消按钮点击
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

    }


    /**
     *  * 点击获取图片
     *  
     */
    public void loadImageBitmap(final String imageUrl) {
        new AsyncTask<Object, Object, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Object... params) {
                Bitmap bitmap = null;
                try {
                    //获取照片bitmap
                    bitmap = ImageManager.getInstance(getActivity()).getLoadBitmap(getActivity(), imageUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    //保存照片
                    saveImage(bitmap);
                } else {
                    ToastUtil.showShort(getActivity(), R.string.save_fail);
                }

            }
        }.execute();
    }


    /**
     * 保存图片
     *
     * @param bmp
     */
    private void saveImage(Bitmap bmp) {
        //照片地址
        String picPath = Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory().getAbsolutePath() : "/mnt/sdcard";//保存到SD卡
        File appDir = new File(picPath, "Chookr");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        //照片名称
        String fileName = System.currentTimeMillis() + ".jpg";
        //file
        File file = new File(appDir, fileName);
        try {
            //输出流
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            //发送到系统相册
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            getActivity().sendBroadcast(intent);
            ToastUtil.showShort(getActivity(), R.string.save_success);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
