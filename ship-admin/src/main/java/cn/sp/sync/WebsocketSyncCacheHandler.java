package cn.sp.sync;

import cn.sp.exception.ShipException;
import cn.sp.utils.ShipThreadFactory;
import cn.sp.utils.StringTools;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.enums.ReadyState;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/28
 */
@Component
public class WebsocketSyncCacheHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(WebsocketSyncCacheHandler.class);

    private WebSocketClient client;

    @Value("${ship.server-web-socket-url}")
    private String serverWebSocketUrl;

    public WebsocketSyncCacheHandler() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1,
                new ShipThreadFactory("websocket-connect", true).create());
        try {
            client = new WebSocketClient(new URI("ws://127.0.0.1:9999")) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    String content = StringTools.byteToStr(serverHandshake.getContent());
                    LOGGER.info("content:[{}]", content);
                }

                @Override
                public void onMessage(String s) {

                }

                @Override
                public void onClose(int i, String s, boolean b) {

                }

                @Override
                public void onError(Exception e) {

                }
            };

            client.connectBlocking();
            //使用调度线程池进行断线重连，30秒进行一次
            executor.scheduleAtFixedRate(() -> {
                if (client != null && client.isClosed()) {
                    try {
                        client.reconnectBlocking();
                    } catch (InterruptedException e) {
                        LOGGER.error("reconnect server fail", e);
                    }
                }
            }, 10, 30, TimeUnit.SECONDS);

        } catch (Exception e) {
            LOGGER.error("websocket sync cache exception", e);
            throw new ShipException(e.getMessage());
        }
    }

    public void send(String content) {
        while (!client.getReadyState().equals(ReadyState.OPEN)) {
            LOGGER.debug("connecting ...please wait");
        }
        client.send(content);
    }
}
