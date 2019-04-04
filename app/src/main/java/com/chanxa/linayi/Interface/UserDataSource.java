
package com.chanxa.linayi.Interface;

import java.util.Map;


public interface UserDataSource {

    interface UserRequestListener<T> {

        void onComplete(T entity);

        void onFail();
    }


    /**
     * 2.1.3用户登录
     * @param param
     * @param listener
     */
    void login(Map<String, Object> param, UserRequestListener listener);

    /**
     * 2.1.3重置密码
     * @param param
     * @param listener
     */
    void resetPassword(Map<String, Object> param, UserRequestListener listener);


    /**
     * 我的资料
     * @param param
     * @param listener
     */
    void userInfo(Map<String, Object> param, UserRequestListener listener);

    /**
     * 保存个人资料
     * @param param
     * @param listener
     */
    void saveUserInfo(Map<String, Object> param, UserRequestListener listener);

    /**
     * 保存个人资料
     * @param param
     * @param listener
     */
    void loginOut(Map<String, Object> param, UserRequestListener listener);


    void getAmountInfo(Map<String, Object> param, UserRequestListener listener);

    void listBankCardInfo(Map<String, Object> param, UserRequestListener listener);

    void addRechargeInfo(Map<String, Object> param, UserRequestListener listener);

    void listRechargeInfo(Map<String, Object> param, UserRequestListener listener);

    void listBankInfo(Map<String, Object> param, UserRequestListener listener);

}
