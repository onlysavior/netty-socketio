package com.github.onlysavior.chat.store;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yanye.lj on 14-1-21.
 */
public class UserId2SessionMapper {
    //TODO redis & LRU
    private final ConcurrentHashMap<String, UUID> userId2UUID = new ConcurrentHashMap<String, UUID>();
    public static final UserId2SessionMapper INSTANCE = new UserId2SessionMapper();

    private UserId2SessionMapper() {
    }

    public void set(String key, UUID id) {
        userId2UUID.put(key, id);
    }

    public UUID get(String key) {
        return userId2UUID.get(key);
    }
}
