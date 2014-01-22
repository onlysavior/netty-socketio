/**
 * Copyright 2012 Nikita Koksharov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.corundumstudio.socketio.handler;

import java.util.concurrent.TimeUnit;

import com.github.onlysavior.chat.store.UserId2SessionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.Disconnectable;
import com.corundumstudio.socketio.parser.Packet;
import com.corundumstudio.socketio.parser.PacketType;
import com.corundumstudio.socketio.scheduler.CancelableScheduler;
import com.corundumstudio.socketio.scheduler.SchedulerKey;
import com.corundumstudio.socketio.scheduler.SchedulerKey.Type;
import com.corundumstudio.socketio.transport.MainBaseClient;

public class HeartbeatHandler implements Disconnectable {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final CancelableScheduler scheduler;
    private final Configuration configuration;
    private UserId2SessionMapper userId2SessionMapper;

    public HeartbeatHandler(Configuration configuration, CancelableScheduler scheduler,
                            UserId2SessionMapper mapper) {
        this.configuration = configuration;
        this.scheduler = scheduler;
        this.userId2SessionMapper = mapper;
    }

    public void onHeartbeat(final MainBaseClient client) {
        if (!configuration.isHeartbeatsEnabled()) {
            return;
        }

        final SchedulerKey key = new SchedulerKey(Type.HEARBEAT_TIMEOUT, client.getSessionId());
        // cancel heartbeat check because the client answered
        scheduler.cancel(key);
        userId2SessionMapper.renew(client.getSessionId());
        scheduler.schedule(new Runnable() {
            public void run() {
                client.send(new Packet(PacketType.HEARTBEAT));
                scheduleClientHeartbeatCheck(client, key);
            }
        }, configuration.getHeartbeatInterval(), TimeUnit.SECONDS);
    }

    private void scheduleClientHeartbeatCheck(final MainBaseClient client, SchedulerKey key) {
        // cancel previous heartbeat check
        scheduler.cancel(key);
        scheduler.schedule(key, new Runnable() {
            public void run() {
                client.disconnect();
                log.debug("Client with sessionId: {} disconnected due to heartbeat timeout", client.getSessionId());
            }
        }, configuration.getHeartbeatTimeout(), TimeUnit.SECONDS);
    }

    @Override
    public void onDisconnect(MainBaseClient client) {
        scheduler.cancel(new SchedulerKey(Type.HEARBEAT_TIMEOUT, client.getSessionId()));
    }

}
