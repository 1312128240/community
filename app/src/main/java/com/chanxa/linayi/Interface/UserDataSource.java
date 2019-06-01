
package com.chanxa.linayi.Interface;

import java.util.Map;


public interface UserDataSource {

    interface UserRequestListener<T> {

        void onComplete(T entity);

        void onFail();
    }

    /**
     * 2.1.3重置密码
     * @param param
     * @param listener
     */
    void resetPassword(Map<String, Object> param, UserRequestListener listener);

}
