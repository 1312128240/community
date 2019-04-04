
package com.chanxa.linayi.Repository;

import android.content.Context;
import android.util.Log;

import com.chanxa.linayi.HttpClient.api.ApiResponse;
import com.chanxa.linayi.Interface.RequestListener;
import com.chanxa.linayi.Interface.UserDataSource;
import com.chanxa.linayi.bean.AmountInfo;
import com.chanxa.linayi.bean.UserEntity;

import java.util.Map;



public class UserRepository extends BaseRepository implements UserDataSource {

    public UserRepository(Context context) {
        super(context);
    }

    @Override
    public void login(Map<String, Object> param, final UserRequestListener listener) {
        //2.1.3用户登录
        setUrl("account/account/communityLogin.do");
        post(param, UserEntity.class, new RequestListener<UserEntity>() {
            @Override
            public void onComplete(UserEntity result) {
                listener.onComplete(result);

            }

            @Override
            public void onFailure(ApiResponse result) {
                listener.onFail();

            }
        });
    }

    @Override
    public void resetPassword(Map<String, Object> param, final UserRequestListener listener) {
        setUrl("user/user/");
        post(param, UserEntity.class, new RequestListener<UserEntity>() {
            @Override
            public void onComplete(UserEntity result) {
                listener.onComplete(result);
            }

            @Override
            public void onFailure(ApiResponse result) {
                listener.onFail();
            }
        });
    }

    @Override
    public void userInfo(Map<String, Object> param, final UserRequestListener listener) {
        setUrl("user/user/");
        post(param, UserEntity.class, new RequestListener<UserEntity>() {
            @Override
            public void onComplete(UserEntity result) {
                listener.onComplete(result);
            }

            @Override
            public void onFailure(ApiResponse result) {
                listener.onFail();
            }
        });
    }

    @Override
    public void saveUserInfo(Map<String, Object> param, final UserRequestListener listener) {
        setUrl("user/user/");
        post(param, UserEntity.class, new RequestListener<UserEntity>() {
            @Override
            public void onComplete(UserEntity result) {
                listener.onComplete(result);
            }

            @Override
            public void onFailure(ApiResponse result) {
                listener.onFail();
            }
        });
    }

    @Override
    public void loginOut(Map<String, Object> param, final UserRequestListener listener) {
        setUrl("user/user/");
        post(param, UserEntity.class, new RequestListener<UserEntity>() {
            @Override
            public void onComplete(UserEntity result) {
                listener.onComplete(result);
            }

            @Override
            public void onFailure(ApiResponse result) {
                listener.onFail();
            }
        });
    }

    @Override
    public void getAmountInfo(Map<String, Object> param, final UserRequestListener listener) {
        setUrl("user/user/");
        post(param, AmountInfo.class, new RequestListener<AmountInfo>() {
            @Override
            public void onComplete(AmountInfo result) {
                listener.onComplete(result);
            }

            @Override
            public void onFailure(ApiResponse result) {
                listener.onFail();
            }
        });
    }

    @Override
    public void listBankCardInfo(Map<String, Object> param, final UserRequestListener listener) {
        setUrl("user/bankCard/");
        post(param, AmountInfo.class, new RequestListener<AmountInfo>() {
            @Override
            public void onComplete(AmountInfo result) {
                listener.onComplete(result);
            }

            @Override
            public void onFailure(ApiResponse result) {
                listener.onFail();
            }
        });
    }

    @Override
    public void addRechargeInfo(Map<String, Object> param, UserRequestListener listener) {
        setUrl("user/user/");
    }

    @Override
    public void listRechargeInfo(Map<String, Object> param, UserRequestListener listener) {
        setUrl("user/user/");

    }

    @Override
    public void listBankInfo(Map<String, Object> param, final UserRequestListener listener) {
        setUrl("user/bankCard/");
        post(param, ApiResponse.class, new RequestListener<ApiResponse>() {
            @Override
            public void onComplete(ApiResponse result) {
                listener.onComplete(result);
            }

            @Override
            public void onFailure(ApiResponse result) {
                listener.onFail();
            }
        });
    }

//    @Override
//    public void locationInfo(Map<String, Object> param, final UserRequestListener listener) {
//        post(param, LocationInfoBean.class, new RequestListener<LocationInfoBean>() {
//            @Override
//            public void onComplete(LocationInfoBean result) {
//                listener.onComplete(result);
//            }
//
//            @Override
//            public void onFailure(ApiResponse result) {
//                listener.onFail();
//            }
//        });
//    }

}
