package com.github.onlysavior.chat.dataobject;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-1-20
 * Time: 下午10:02
 * To change this template use File | Settings | File Templates.
 */
public class ChatObject {
    private String userName;
    private String fromUserId;
    private String toUserId;
    private String toUserName;
    private String message;

    public ChatObject() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }
}
