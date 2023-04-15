package cn.sp.plugin.impl;

import cn.sp.chain.PluginChain;
import cn.sp.chain.ShipResponseUtil;
import cn.sp.config.ServerConfigProperties;
import cn.sp.constants.ServerConstants;
import cn.sp.constants.ShipExceptionEnum;
import cn.sp.constants.ShipPluginEnum;
import cn.sp.exception.ShipException;
import cn.sp.plugin.AbstractShipPlugin;
import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.RequestPath;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/29
 */
public class RateLimitPlugin extends AbstractShipPlugin {

    private final Logger logger = LoggerFactory.getLogger(RateLimitPlugin.class);

    private static Map<String, Integer> rateLimitTypeMap = new HashMap<>();

    static {
        rateLimitTypeMap.put(ServerConstants.LIMIT_BY_OPS, RuleConstant.FLOW_GRADE_QPS);
        rateLimitTypeMap.put(ServerConstants.LIMIT_BY_THREAD, RuleConstant.FLOW_GRADE_THREAD);
    }

    public RateLimitPlugin(ServerConfigProperties properties) {
        super(properties);
    }

    @Override
    public Integer order() {
        return ShipPluginEnum.RATE_LIMIT.getOrder();
    }

    @Override
    public String name() {
        return ShipPluginEnum.RATE_LIMIT.getName();
    }

    @Override
    public Mono<Void> execute(ServerWebExchange exchange, PluginChain pluginChain) {
        Assert.hasText(properties.getRateLimitType(), "config ship.gate.rateLimitType required!");
        Assert.notNull(properties.getRateLimitCount(), "config ship.gate.rateLimitCount required!");
        String appName = pluginChain.getAppName();
        List<FlowRule> list = new ArrayList<>();
        FlowRule flowRule = new FlowRule();
        flowRule.setResource(appName);
        flowRule.setGrade(rateLimitTypeMap.get(properties.getRateLimitType()));
        flowRule.setCount(properties.getRateLimitCount().doubleValue());
        list.add(flowRule);
        FlowRuleManager.loadRules(list);
        if (SphO.entry(appName)) {
            // 务必保证finally会被执行
            try {
                /**
                 * 被保护的业务逻辑
                 */
                return pluginChain.execute(exchange, pluginChain);
            } finally {
                SphO.exit();
            }
        }
        throw new ShipException(ShipExceptionEnum.REQUEST_LIMIT_ERROR);
    }
}
