
package com.chanxa.linayi.Repository;

import android.content.Context;

import com.chanxa.linayi.HttpClient.ApiResponse;
import com.chanxa.linayi.Interface.RequestListener;
import com.chanxa.linayi.Interface.UserDataSource;
import com.chanxa.linayi.bean.UserEntity;

import java.util.Map;



public class UserRepository extends BaseRepository implements UserDataSource {

    public UserRepository(Context context) {
        super(context);
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

}
