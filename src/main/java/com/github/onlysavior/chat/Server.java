package com.github.onlysavior.chat;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.github.onlysavior.chat.dataobject.ChatObject;
import com.github.onlysavior.chat.dataobject.SyncKey;
import com.github.onlysavior.chat.linstener.ChatDataLinstener;
import com.github.onlysavior.chat.linstener.ConnectDataLinstener;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-1-20
 * Time: 下午9:58
 * To change this template use File | Settings | File Templates.
 */
public class Server {
    public static void main(String[] args) throws Exception {
        Configuration config  = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);

        final SocketIOServer server = new SocketIOServer(config);
        server.addEventListener("chatevent", ChatObject.class, new ChatDataLinstener(server));
        server.addEventListener("connectevent", SyncKey.class, new ConnectDataLinstener(server));

        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }
}
