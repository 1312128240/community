
package com.chanxa.linayi.Contact;

import com.chanxa.linayi.Interface.BasePresenter;
import com.chanxa.linayi.Interface.BaseView;

/**
 * Created by user1 on 2017/1/9.
 */

public class FindPasswordContact {

    public interface View extends BaseView {
        void onFindPasswordSuccess();
        //获取验证码成功
        void onGetVCodeSuccess();

        //Toast提示
        void onNullAccount(String tips);

        //显示进度条
        void showProgress();

        //关闭进度条
        void dismissProgress();
    }

    public interface Presenter extends BasePresenter {

        void getValidateCode(String account);

        void resetPassword(String account, String validateCode, String password);
    }

}
