
package com.chanxa.linayi.Presenter;

import android.content.Context;
import android.text.TextUtils;


import com.chanxa.linayi.Contact.FindPasswordContact;
import com.chanxa.linayi.Interface.SystemCommonDataSource;
import com.chanxa.linayi.Interface.UserDataSource;
import com.chanxa.linayi.Repository.SystemCommonRepository;
import com.chanxa.linayi.Repository.UserRepository;
import com.chanxa.linayi.tools.AppUtils;
import com.chanxa.linayi.tools.MD5Utils;

import java.util.HashMap;
import java.util.Map;



public class FindPasswordPresenter extends BaseImlPresenter implements FindPasswordContact.Presenter{

    public static final String TAG = "OrderListPresenter";

    public FindPasswordContact.View mView;
    public UserDataSource mUserDataSource;
    private SystemCommonDataSource systemCommonDataSource;


    public FindPasswordPresenter(Context context, FindPasswordContact.View view){
        mUserDataSource = new UserRepository(context);
        systemCommonDataSource = new SystemCommonRepository(context);
        mView = view;
    }


    @Override
    public void getValidateCode(String account) {
        if (TextUtils.isEmpty(account)) {
            mView.onNullAccount("请输入手机号码");
            return;
        }

        //如果输入的既不是邮箱也不是手机号码
        if (!AppUtils.isMobileNO(account)) {
            mView.onNullAccount("请输入正确的手机号码");
            return;
        }


        Map<String, Object> node = new HashMap<>();
        //传手机号码
        node.put("account", account);
        //20：修改密码或者找回密码
        node.put("type", 20);
        //用户类型,10：用户，20：网点账号，30：司机
        node.put("userType", 20);
        //显示加载进度
        mView.showProgress();

        systemCommonDataSource.getValidateCode(node, new SystemCommonDataSource.UserRequestListener() {
            @Override
            public void onComplete(Object entity) {
                //关闭加载进度
                mView.dismissProgress();
                mView.onGetVCodeSuccess();
            }

            @Override
            public void onFail() {
                //关闭加载进度
                mView.dismissProgress();
            }
        });
    }

    @Override
    public void resetPassword(String account, String validateCode, String password) {

        Map<String, Object> node = new HashMap<>();
        //传手机号码
        node.put("account", account);
        //验证码
        node.put("validateCode", validateCode);
        //新密码
        node.put("password", MD5Utils.MD5(password));
        //用户类型,10：用户，20：网点账号，30：司机
        node.put("userType", 20);
        mView.showProgress();
        mUserDataSource.resetPassword(node, new UserDataSource.UserRequestListener() {
            @Override
            public void onComplete(Object entity) {
                //关闭加载进度
                mView.dismissProgress();
                mView.onFindPasswordSuccess();
            }

            @Override
            public void onFail() {
                //关闭加载进度
                mView.dismissProgress();
            }
        });
    }
}
