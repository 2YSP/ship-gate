//package cn.sp.sync;
//
//import cn.sp.cache.RouteRuleCache;
//import cn.sp.constants.OperationTypeEnum;
//import cn.sp.pojo.dto.AppRuleDTO;
//import cn.sp.pojo.dto.RouteRuleOperationDTO;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import org.java_websocket.WebSocket;
//import org.java_websocket.handshake.ClientHandshake;
//import org.java_websocket.server.WebSocketServer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.CollectionUtils;
//
//import java.net.InetSocketAddress;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
///**
// * @Author: Ship
// * @Description:
// * @Date: Created in 2020/12/28
// */
//public class WebsocketSyncCacheServer extends WebSocketServer {
//
//    private final static Logger LOGGER = LoggerFactory.getLogger(WebsocketSyncCacheServer.class);
//
//    private Gson gson = new GsonBuilder().create();
//
//    private MessageHandler messageHandler;
//
//    public WebsocketSyncCacheServer(Integer port) {
//        super(new InetSocketAddress(port));
//        this.messageHandler = new MessageHandler();
//    }
//
//
//    @Override
//    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
//        LOGGER.info("server is open");
//    }
//
//    @Override
//    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
//        LOGGER.info("websocket server close...");
//    }
//
//    @Override
//    public void onMessage(WebSocket webSocket, String message) {
//        LOGGER.info("websocket server receive message:\n[{}]", message);
//        this.messageHandler.handler(message);
//    }
//
//    @Override
//    public void onError(WebSocket webSocket, Exception e) {
//
//    }
//
//    @Override
//    public void onStart() {
//        LOGGER.info("websocket server start...");
//    }
//
//
//    class MessageHandler {
//
//        public void handler(String message) {
//            RouteRuleOperationDTO operationDTO = gson.fromJson(message, RouteRuleOperationDTO.class);
//            if (CollectionUtils.isEmpty(operationDTO.getRuleList())) {
//                return;
//            }
//            Map<String, List<AppRuleDTO>> map = operationDTO.getRuleList()
//                    .stream().collect(Collectors.groupingBy(AppRuleDTO::getAppName));
//            if (OperationTypeEnum.INSERT.getCode().equals(operationDTO.getOperationType())
//                    || OperationTypeEnum.UPDATE.getCode().equals(operationDTO.getOperationType())) {
//                RouteRuleCache.add(map);
//            } else if (OperationTypeEnum.DELETE.getCode().equals(operationDTO.getOperationType())) {
//                RouteRuleCache.remove(map);
//            }
//        }
//    }
//}
