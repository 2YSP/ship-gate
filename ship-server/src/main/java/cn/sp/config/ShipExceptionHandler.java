package cn.sp.config;

import cn.sp.chain.ShipResponseUtil;
import cn.sp.exception.ShipException;
import cn.sp.plugin.impl.RateLimitPlugin;
import cn.sp.pojo.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2023/4/15
 */
public class ShipExceptionHandler implements WebExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(ShipExceptionHandler.class);

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {
        logger.error("ship server exception msg:{}", throwable.getMessage());
        if (throwable instanceof ShipException) {
            ShipException shipException = (ShipException) throwable;
            return ShipResponseUtil.doResponse(serverWebExchange, new ApiResult(shipException.getCode(), shipException.getErrMsg()));
        }
        String errorMsg = "system error";
        if (throwable instanceof IllegalArgumentException) {
            errorMsg = throwable.getMessage();
        }
        return ShipResponseUtil.doResponse(serverWebExchange, new ApiResult(5000, errorMsg));
    }
}
