
package com.chanxa.linayi.Repository;

import android.content.Context;

import com.chanxa.linayi.HttpClient.api.ApiResponse;
import com.chanxa.linayi.Interface.RequestListener;
import com.chanxa.linayi.Interface.SystemCommonDataSource;
import com.chanxa.linayi.bean.UserEntity;

import java.util.Map;



public class SystemCommonRepository extends BaseRepository implements SystemCommonDataSource {

    public SystemCommonRepository(Context context) {
        super(context);
        setUrl("system/common/");
    }


    @Override
    public void getValidateCode(Map<String, Object> param, final UserRequestListener listener) {
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
