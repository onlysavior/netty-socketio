package com.github.onlysavior.chat.store;

import com.github.onlysavior.chat.constant.Cons;
import redis.clients.jedis.Jedis;

import java.util.UUID;

/**
 * Created by yanye.lj on 14-1-22.
 */
public class RedisLoginUserCache implements LoginUserCache{
    private Jedis jedis = new Jedis("localhost", 6379);    //for test

    public RedisLoginUserCache() {}

    public RedisLoginUserCache(String host, int port) {
        jedis = new Jedis(host, port);
    }

    @Override
    public void set(String key, UUID uuid) {
        jedis.set(key, uuid.toString(), "XX", "EX", Cons.TIME_OUT);
        jedis.set(uuid.toString(), key, "XX", "EX", Cons.TIME_OUT);
    }

    @Override
    public UUID get(String key) {
        return UUID.fromString(jedis.get(key));
    }

    @Override
    public void handleDisconnect(UUID uuid) {
        String key = jedis.get(uuid.toString());
        if(key != null) {
            jedis.del(key);
            jedis.del(uuid.toString());
        }
    }

    @Override
    public void renew(UUID uuid) {
        String key = jedis.get(uuid.toString());
        if(key != null) {
            jedis.set(key, uuid.toString(), "XX", "EX", Cons.TIME_OUT);
        }
        jedis.set(uuid.toString(), key, "XX", "EX", Cons.TIME_OUT);
    }
}
