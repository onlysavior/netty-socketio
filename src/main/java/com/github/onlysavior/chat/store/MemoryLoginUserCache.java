package com.github.onlysavior.chat.store;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yanye.lj on 14-1-22.
 */
public class MemoryLoginUserCache implements LoginUserCache {
    private final ConcurrentHashMap<String, UUID> userId2UUID = new ConcurrentHashMap<String, UUID>();
    private final ConcurrentHashMap<UUID, String> UUID2userId = new ConcurrentHashMap<UUID, String>();

    @Override
    public void set(String key, UUID id) {
        userId2UUID.put(key, id);
        UUID2userId.put(id, key);
    }

    @Override
    public UUID get(String key) {
        return userId2UUID.get(key);
    }

    @Override
    public void handleDisconnect(UUID uuid) {
        String key = UUID2userId.get(uuid);
        if(key != null) {
            UUID2userId.remove(uuid);
            userId2UUID.remove(key);
        }
    }

    @Override
    public void renew(UUID uuid) {
        //not support
    }
}
