
package com.chanxa.linayi.bean;


import com.chanxa.linayi.HttpClient.ApiResponse;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class UserEntity extends ApiResponse implements Serializable {

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String CommunityName;
        @SerializedName("accessToken")
        private String accessTokenX;

        public String getCommunityName() {
            return CommunityName;
        }

        public void setCommunityName(String CommunityName) {
            this.CommunityName = CommunityName;
        }

        public String getAccessTokenX() {
            return accessTokenX;
        }

        public void setAccessTokenX(String accessTokenX) {
            this.accessTokenX = accessTokenX;
        }
    }

    /**token*/
    private String accessToken;
    /**用户id*/
    private String communityAccountId;
    /**社区id*/
    private String communityId;
    /**社区名称*/
    private String communityName;
    /**头像*/
    private String headImage;
    /**昵称*/
    private String nickname;
    /**性别code,1男，2女*/
    private String sex;
    /**性别名称*/
    private String sexName;
    /**地区code*/
    private String areaCode;
    /**地区名称*/
    private String areaCodeName;
    /**个性签名*/
    private String signature;
    /**账号*/
    private String account;
    /**食谱总数*/
    private String totalRecipe;
    /**作品总数*/
    private String totalProduction;
    /**帖子总数*/
    private String totalInvitation;
    /**草稿箱总数*/
    private String totalDrafts;
    /**是否设置了密码,0未设置，1设置了*/
    private String isExistPassword;

    /**评论相关推送状态,0：推送；1：不推送*/
    private String commentPushStatus;
    /**官方通知推送状态,0：推送；1：不推送*/
    private String officialPushStatus;

    /**是否绑定手机,0未绑定，1已绑定*/
    private String isBindMobile;
    /**是否绑定QQ,0未绑定，1已绑定*/
    private String isExistQQAuth;
    /**是否绑定微信,0未绑定，1已绑定*/
    private String isExistWeixinAuth;
    /**是否绑定新浪微博,0未绑定，1已绑定*/
    private String isExistWeiboAuth;

    private String locationId;

    private String address;

    private String areaName;
    private DataBean data;

    public String getAddress() {
        if (areaName!=null){
            return areaName.replaceAll(" ", "") + address;
        }
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getCommunityAccountId() {
        return communityAccountId;
    }

    public void setCommunityAccountId(String communityAccountId) {
        this.communityAccountId = communityAccountId;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTotalRecipe() {
        return totalRecipe;
    }

    public void setTotalRecipe(String totalRecipe) {
        this.totalRecipe = totalRecipe;
    }

    public String getTotalProduction() {
        return totalProduction;
    }

    public void setTotalProduction(String totalProduction) {
        this.totalProduction = totalProduction;
    }

    public String getTotalInvitation() {
        return totalInvitation;
    }

    public void setTotalInvitation(String totalInvitation) {
        this.totalInvitation = totalInvitation;
    }

    public String getTotalDrafts() {
        return totalDrafts;
    }

    public void setTotalDrafts(String totalDrafts) {
        this.totalDrafts = totalDrafts;
    }

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    public String getAreaCodeName() {
        return areaCodeName;
    }

    public void setAreaCodeName(String areaCodeName) {
        this.areaCodeName = areaCodeName;
    }

    public String getIsExistPassword() {
        return isExistPassword;
    }

    public void setIsExistPassword(String isExistPassword) {
        this.isExistPassword = isExistPassword;
    }

    public String getCommentPushStatus() {
        return commentPushStatus;
    }

    public void setCommentPushStatus(String commentPushStatus) {
        this.commentPushStatus = commentPushStatus;
    }

    public String getOfficialPushStatus() {
        return officialPushStatus;
    }

    public void setOfficialPushStatus(String officialPushStatus) {
        this.officialPushStatus = officialPushStatus;
    }

    public String getIsBindMobile() {
        return isBindMobile;
    }

    public void setIsBindMobile(String isBindMobile) {
        this.isBindMobile = isBindMobile;
    }

    public String getIsExistQQAuth() {
        return isExistQQAuth;
    }

    public void setIsExistQQAuth(String isExistQQAuth) {
        this.isExistQQAuth = isExistQQAuth;
    }

    public String getIsExistWeixinAuth() {
        return isExistWeixinAuth;
    }

    public void setIsExistWeixinAuth(String isExistWeixinAuth) {
        this.isExistWeixinAuth = isExistWeixinAuth;
    }

    public String getIsExistWeiboAuth() {
        return isExistWeiboAuth;
    }

    public void setIsExistWeiboAuth(String isExistWeiboAuth) {
        this.isExistWeiboAuth = isExistWeiboAuth;
    }
}
