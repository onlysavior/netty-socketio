package com.github.onlysavior.chat.linstener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.github.onlysavior.chat.constant.Cons;
import com.github.onlysavior.chat.dataobject.SyncKey;
import com.github.onlysavior.chat.store.UserId2SessionMapper;

/**
 * Created by yanye.lj on 14-1-21.
 */
public class ConnectDataLinstener implements DataListener<SyncKey> {
    private final SocketIOServer server;

    public ConnectDataLinstener(SocketIOServer s) {
        this.server = s;
    }

    @Override
    public void onData(SocketIOClient client, SyncKey data, AckRequest ackSender) {
        client.set(Cons.USER_ID, data.getUserId());
        server.getPipelineFactory().getUserMapper().set(data.getUserId(), client.getSessionId());
    }
}
