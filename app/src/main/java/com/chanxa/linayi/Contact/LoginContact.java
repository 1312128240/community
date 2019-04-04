
package com.chanxa.linayi.Contact;


import com.chanxa.linayi.Interface.BasePresenter;
import com.chanxa.linayi.Interface.BaseView;
import com.chanxa.linayi.bean.UserEntity;

/**
 * Created by user1 on 2017/1/9.
 */

public class LoginContact {

    public interface View extends BaseView {
        //登录成功
        void onLoginSuccess(UserEntity entity);

        //显示进度条
        void showProgress();

        //关闭进度条
        void dismissProgress();

    }

    public interface Presenter extends BasePresenter {

        /**
         * 账号登录
         * @param account 账号
         * @param password 密码
         */
        void login(String account, String password/*, String mobileToken*/);


    }

}
