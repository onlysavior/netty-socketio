package com.github.onlysavior.chat.dataobject;

/**
 * Created by yanye.lj on 14-1-21.
 */
public class SyncKey {
    private String userId;
    private String syncKey;     //TODO remove ?

    public SyncKey(){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSyncKey() {
        return syncKey;
    }

    public void setSyncKey(String syncKey) {
        this.syncKey = syncKey;
    }
}
