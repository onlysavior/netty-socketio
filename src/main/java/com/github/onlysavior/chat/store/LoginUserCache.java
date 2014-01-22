package com.github.onlysavior.chat.store;

import java.util.UUID;

/**
 * Created by yanye.lj on 14-1-22.
 */
public interface LoginUserCache {
    public void set(String key, UUID uuid);
    public UUID get(String key);
    public void handleDisconnect(UUID uuid);
    public void renew(UUID uuid);
}
