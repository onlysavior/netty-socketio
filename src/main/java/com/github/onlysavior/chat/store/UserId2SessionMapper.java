package com.github.onlysavior.chat.store;

import com.corundumstudio.socketio.Configuration;

import java.util.UUID;

/**
 * Created by yanye.lj on 14-1-21.
 */
public class UserId2SessionMapper {
    private LoginUserCache cache;

    public UserId2SessionMapper(Configuration config) {
        if (config.isUseMemoryStoreFactory()) {
            cache = new MemoryLoginUserCache();
        } else {
            cache = new RedisLoginUserCache(config.getRedisHost(), config.getPort());
        }
    }

    public void set(String key, UUID id) {
        cache.set(key, id);
    }

    public UUID get(String key) {
        return cache.get(key);
    }

    public void handleDisconnect(UUID uuid) {
        cache.handleDisconnect(uuid);
    }

    public void renew(UUID uuid) {
        cache.renew(uuid);
    }
}
