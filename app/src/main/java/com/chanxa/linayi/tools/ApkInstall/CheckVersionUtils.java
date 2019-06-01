package com.chanxa.linayi.tools.ApkInstall;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.chanxa.linayi.App;
import com.chanxa.linayi.HttpClient.OkhttpUtil;
import com.chanxa.linayi.HttpClient.ResultCallback;
import com.chanxa.linayi.bean.UpdateBean;
import com.chanxa.linayi.tools.PermissionUtil;
import com.chanxa.linayi.views.dialogfragments.AlterDialogFragment;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;



public class CheckVersionUtils{

    public AppCompatActivity appCompatActivity;

    public CheckVersionUtils(AppCompatActivity appCompatActivity) {
        this.appCompatActivity=appCompatActivity;
    }

    public void getVersion(){
        OkhttpUtil
                .getmIntance()
                .seTag(this)
                .GetAsyncData("getCommunityAppUpdateInfo.do", null, new ResultCallback<UpdateBean>() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                         Log.e("获取带参数版本错误",e.toString());
                    }

                    @Override
                    public void onResponse(Call call, UpdateBean response) {
                        if(response!=null&&response.getData()!=null){
                            if (response.getData().getVersionCode()>getVersionCode(appCompatActivity)) {
                                showAlertDialog(response);
                            }else {
                                Log.e("升级检测","是最新版本,不用升级");
                            }
                        }
                    }
                });

    }

    /**
     * 弹出提示窗
     * @param bean
     */
    private void showAlertDialog(final UpdateBean  bean) {

        final AlterDialogFragment dialogFragment = new AlterDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", bean.getData().getUpdate_content());
        dialogFragment.setArguments(bundle);
        if("true".equals(bean.getData().getIsForceUpdate())){
            dialogFragment.setCancelable(false);
        }else {
            dialogFragment.setCancelable(true);
        }
        dialogFragment.show(appCompatActivity.getSupportFragmentManager(), "Update");
        dialogFragment.setOnClick(new AlterDialogFragment.CustAlterDialgoInterface() {
            @Override
            public void cancle() {
                if("false".equals(bean.getData().getIsForceUpdate())){
                    dialogFragment.dismiss();
                }
            }

            @Override
            public void sure() {
                //注意这个是8.0新API,是否有安装apk权限
                if(Build.VERSION.SDK_INT >=26){
                    boolean haveInstallPermission =appCompatActivity.getPackageManager().canRequestPackageInstalls();
                    if(!haveInstallPermission){
                        Toast.makeText(appCompatActivity,"请允许安装应用",Toast.LENGTH_LONG).show();
                        Uri packageURI = Uri.parse("package:" + appCompatActivity.getPackageName());
                        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                        appCompatActivity.startActivity(intent);
                        return;
                    }
                }

                //是否开启存储权限，
                boolean b = PermissionUtil.hasPermissons(appCompatActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if(Build.VERSION.SDK_INT >= 23){
                    if(!b){
                        //没有开启权限，分2种情况，
                        //(1),isNoAsk为true表示用户只是拒绝开启权限，则弹出提示框
                        //(2),isNoAse为false表示用户选择不再询问并拒绝，则系统将不再弹出提示框，为了交互友好跳转到设置界面，手动开启权限
                        //选择不再询问，就不会走这一步了
                       PermissionUtil.getExternalStoragePermissions(appCompatActivity, 101);

                        boolean isNoAsk = appCompatActivity.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        if(!isNoAsk){
                            Toast.makeText(appCompatActivity,"请开启存储权限",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        //开启了权限
                        dialogFragment.dismiss();
                        DownLoadApk downLoadApk=new DownLoadApk();
                        downLoadApk.download(App.getInstance(), bean.getData().getDownload_url(), "正在下载邻阿姨社区端", "layCommunity");
                    }
                }else {
                    //6.0以下系统不用开启权限
                    dialogFragment.dismiss();
                    DownLoadApk downLoadApk=new DownLoadApk();
                    downLoadApk.download(App.getInstance(), bean.getData().getDownload_url(), "正在下载邻阿姨社区端", "layCommunity");
                }

            }
        });

    }


    /**
     * 获取当前本地apk的版本
     *
     * @param mContext
     * @return
     */
    public  int getVersionCode(Context mContext) {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }



     class  TestBean {

         /**
          * msg : 查询成功
          * status : 1
          * data : [{"id":"8427","type":"1","car_pp":"125718","car_cx":"126049","car_pl":"126088","car_nf":"126094","a":"2014款 2.0T 手自一体 28T 四驱 豪华版","s":"https://wx.aiucar.com/api/package_car/img/125718.png","q":"别克","w":"昂科威","e":"2.0T","r":"2015"},{"id":"8175","type":"0","car_pp":"112145","car_cx":"112146","car_pl":"112147","car_nf":"112148","a":"2014款 2.0T 手自一体 28T 四驱 豪华版","s":"https://wx.aiucar.com/api/package_car/img/112145.png","q":"阿尔法·罗米欧","w":"Giulia-","e":"2.0T","r":"2017"}]
          */

         private String msg;
         private String status;
         private List<DataBean> data;

         public String getMsg() {
             return msg;
         }

         public void setMsg(String msg) {
             this.msg = msg;
         }

         public String getStatus() {
             return status;
         }

         public void setStatus(String status) {
             this.status = status;
         }

         public List<DataBean> getData() {
             return data;
         }

         public void setData(List<DataBean> data) {
             this.data = data;
         }

         public class DataBean {
             /**
              * id : 8427
              * type : 1
              * car_pp : 125718
              * car_cx : 126049
              * car_pl : 126088
              * car_nf : 126094
              * a : 2014款 2.0T 手自一体 28T 四驱 豪华版
              * s : https://wx.aiucar.com/api/package_car/img/125718.png
              * q : 别克
              * w : 昂科威
              * e : 2.0T
              * r : 2015
              */

             private String id;
             private String type;
             private String car_pp;
             private String car_cx;
             private String car_pl;
             private String car_nf;
             private String a;
             private String s;
             private String q;
             private String w;
             private String e;
             private String r;

             public String getId() {
                 return id;
             }

             public void setId(String id) {
                 this.id = id;
             }

             public String getType() {
                 return type;
             }

             public void setType(String type) {
                 this.type = type;
             }

             public String getCar_pp() {
                 return car_pp;
             }

             public void setCar_pp(String car_pp) {
                 this.car_pp = car_pp;
             }

             public String getCar_cx() {
                 return car_cx;
             }

             public void setCar_cx(String car_cx) {
                 this.car_cx = car_cx;
             }

             public String getCar_pl() {
                 return car_pl;
             }

             public void setCar_pl(String car_pl) {
                 this.car_pl = car_pl;
             }

             public String getCar_nf() {
                 return car_nf;
             }

             public void setCar_nf(String car_nf) {
                 this.car_nf = car_nf;
             }

             public String getA() {
                 return a;
             }

             public void setA(String a) {
                 this.a = a;
             }

             public String getS() {
                 return s;
             }

             public void setS(String s) {
                 this.s = s;
             }

             public String getQ() {
                 return q;
             }

             public void setQ(String q) {
                 this.q = q;
             }

             public String getW() {
                 return w;
             }

             public void setW(String w) {
                 this.w = w;
             }

             public String getE() {
                 return e;
             }

             public void setE(String e) {
                 this.e = e;
             }

             public String getR() {
                 return r;
             }

             public void setR(String r) {
                 this.r = r;
             }
         }
     }


}
