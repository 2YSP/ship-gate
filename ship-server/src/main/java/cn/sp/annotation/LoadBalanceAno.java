package cn.sp.annotation;

import java.lang.annotation.*;

/**
 * 负载均衡注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoadBalanceAno {

    String value() default "";
}
