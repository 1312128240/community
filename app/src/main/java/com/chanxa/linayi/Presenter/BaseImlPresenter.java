package com.chanxa.linayi.Presenter;

import com.chanxa.linayi.App;
import com.chanxa.linayi.tools.SPUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2019/3/21.
 */

public class BaseImlPresenter {

    protected Map<String, Object> getBaseMap(){
        Map<String, Object> node = new HashMap<>();
        node.put("communityId", SPUtils.getCommunityId(App.getInstance()));
        node.put("accessToken", SPUtils.getAccessToken(App.getInstance()));
        return node;
    }

    protected Map<String, Object> getAccountMap(){
        Map<String, Object> node = new HashMap<>();
        node.put("userId", SPUtils.getCommunityAccountId(App.getInstance()));
        node.put("accessToken", SPUtils.getAccessToken(App.getInstance()));
        return node;
    }
//
//    protected Map<String, Object> getBaseMap(boolean hasUserId){
//        Map<String, Object> node = new HashMap<>();
//        node.put("communityId", SPUtils.getCommunityId(App.getInstance()));
//        node.put("accessToken", SPUtils.getAccessToken(App.getInstance()));
//        if (hasUserId){
//            node.put("userId", SPUtils.getCommunityAccountId(App.getInstance()));
//        }
////        node.put("communityId", AccountManager.getInstance().getUserId());
////        node.put("accessToken", AccountManager.getInstance().getToken());
//        return node;
//    }

    protected Map<String, Object> getBaseMap(int page, int currentPage){
        Map<String, Object> node = getBaseMap();
        node.put("pageSize", page);
        node.put("currentPage", currentPage);
        return node;
    }

    protected Map<String, Object> getUserTokenMap(){
        return getBaseMap();
    }

    protected Map<String, Object> getPageSizeMap(int page, int currentPage){
        Map<String, Object> node = new HashMap<>();
        node.put("pageSize", page);
        node.put("currentPage", currentPage);
        return node;
    }
}
