
package com.chanxa.linayi.Interface;

import java.util.Map;


public interface SystemCommonDataSource {

    interface UserRequestListener<T> {

        void onComplete(T entity);

        void onFail();
    }


    /**
     * 2.1.3用户登录
     * @param param
     * @param listener
     */
    void getValidateCode(Map<String, Object> param, UserRequestListener listener);

}
