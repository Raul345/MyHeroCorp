package com.herocorp.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by JitainSharma on 17/06/16.
 */
public class AuthenticateUserModel extends BaseEntity {

    @JsonProperty("Message")
    private String message;

    @JsonProperty("userId")
    private int userId;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("userImage")
    private String userImage;

    @JsonProperty("appValiditiDate")
    private String appValiditiDate;

    public AuthenticateUserModel(){}
    public AuthenticateUserModel(int _ID, String message, int userId,
                                 String userName, String userImage, String appValiditiDate){

        this.message = message;
        this.userId = userId;
        this.userName = userName;
        this.userImage = userImage;
        this.appValiditiDate = appValiditiDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getAppValiditiDate() {
        return appValiditiDate;
    }

    public void setAppValiditiDate(String appValiditiDate) {
        this.appValiditiDate = appValiditiDate;
    }
}
