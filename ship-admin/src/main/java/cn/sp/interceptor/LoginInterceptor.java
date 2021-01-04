package cn.sp.interceptor;

import cn.sp.constants.AdminConstants;
import cn.sp.constants.ShipExceptionEnum;
import cn.sp.exception.ShipException;
import cn.sp.service.UserService;
import cn.sp.util.JwtUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2021/1/4
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    private static List<String> ignoreUrlList = Lists.newArrayList("/app/register", "/app/unregister", "/user/login");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (ignoreUrlList.contains(request.getRequestURI())) {
            return true;
        }
        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new ShipException(ShipExceptionEnum.NOT_LOGIN);
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(AdminConstants.TOKEN_NAME)) {
                token = cookie.getValue();
            }
        }
        if (StringUtils.isEmpty(token)) {
            throw new ShipException(ShipExceptionEnum.NOT_LOGIN);
        }
        boolean result = JwtUtils.checkSignature(token);
        if (!result) {
            throw new ShipException(ShipExceptionEnum.TOKEN_ERROR);
        }
        return true;
    }
}
