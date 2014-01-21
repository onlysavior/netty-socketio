package com.corundumstudio.socketio;

import com.corundumstudio.socketio.parser.Packet;

import java.util.UUID;

/**
 * Created by yanye.lj on 14-1-21.
 */
public class ClientOperationsAdapter implements ClientOperations{
    @Override
    public void sendMessage(String message) {

    }

    @Override
    public void sendJsonObject(Object object) {

    }

    @Override
    public void send(Packet packet) {

    }

    @Override
    public void disconnect() {

    }

    @Override
    public void sendEvent(String name, Object data) {

    }

    @Override
    public void sendSingleMessage(String message, UUID toUser) {

    }

    @Override
    public void send(Packet packet, UUID toUser) {

    }

    @Override
    public void sendJsonObject(Object object, UUID toUser) {

    }
}
