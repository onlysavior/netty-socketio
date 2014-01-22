package com.github.onlysavior.chat.linstener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.github.onlysavior.chat.dataobject.ChatObject;

import java.util.UUID;

/**
 * Created by yanye.lj on 14-1-21.
 */
public class ChatDataLinstener implements DataListener<ChatObject> {
    private final SocketIOServer server;

    public ChatDataLinstener(SocketIOServer server) {
        this.server = server;
    }

    @Override
    public void onData(SocketIOClient client, ChatObject data, AckRequest ackSender) {
        String recevier = data.getToUserId();
        UUID recevierSession = server.getPipelineFactory().getUserMapper().get(recevier);
        SocketIONamespace ns = client.getNamespace();
        ns.getSinglecastOperations().sendJsonObject(data, recevierSession);
    }
}
