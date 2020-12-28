package cn.sp.sync;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/28
 */
public class WebsocketSyncCacheServer extends WebSocketServer {


    private final static Logger LOGGER = LoggerFactory.getLogger(WebsocketSyncCacheServer.class);

    public WebsocketSyncCacheServer(Integer port) {
        super(new InetSocketAddress(port));
    }


    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {

    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {

    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        LOGGER.info("=======message:" + s);
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {

    }

    @Override
    public void onStart() {

    }
}
