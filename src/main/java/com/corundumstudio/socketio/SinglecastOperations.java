package com.corundumstudio.socketio;


import com.corundumstudio.socketio.namespace.Namespace;
import com.corundumstudio.socketio.parser.Packet;
import com.corundumstudio.socketio.parser.PacketType;
import com.corundumstudio.socketio.store.StoreFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by yanye.lj on 14-1-21.
 */
public class SinglecastOperations extends ClientOperationsAdapter {
    private final Iterable<SocketIOClient> clients;
    private final Set<String> namespaceRooms = new HashSet<String>();
    private final StoreFactory storeFactory;

    public SinglecastOperations(Iterable<SocketIOClient> clients, StoreFactory storeFactory) {
        super();
        this.clients = clients;
        for (SocketIOClient socketIOClient : clients) {
            Namespace namespace = (Namespace)socketIOClient.getNamespace();
            List<String> rooms = namespace.getRooms(socketIOClient);
            namespaceRooms.addAll(rooms);
        }
        this.storeFactory = storeFactory;
    }

    @Override
    public void sendJsonObject(Object object, UUID toUser) {
        Packet packet = new Packet(PacketType.JSON);
        packet.setData(object);
        send(packet, toUser);
    }

    @Override
    public void send(Packet packet, UUID toUser) {
        for (SocketIOClient client : clients) {
            if(client.getSessionId().equals(toUser)) {
                client.send(packet);
            }
        }
    }

    @Override
    public void sendSingleMessage(String message, UUID toUser) {
        Packet packet = new Packet(PacketType.MESSAGE);
        packet.setData(message);
        send(packet, toUser);
    }
}
