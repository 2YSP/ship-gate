package cn.sp.service.impl;

import cn.sp.bean.User;
import cn.sp.constants.AdminConstants;
import cn.sp.constants.ShipExceptionEnum;
import cn.sp.exception.ShipException;
import cn.sp.mapper.UserMapper;
import cn.sp.pojo.PayLoad;
import cn.sp.pojo.UserDTO;
import cn.sp.service.UserService;
import cn.sp.util.JwtUtils;
import cn.sp.utils.StringTools;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2021/1/4
 */
@Service
public class UserServiceImpl implements UserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userMapper;

    public static final String SALT = "d5ec0a02";

    @Override
    public void add(UserDTO userDTO) {
        User oldOne = queryByName(userDTO.getUserName());
        if (oldOne != null) {
            throw new ShipException("the userName already exist");
        }
        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setPassword(StringTools.md5Digest(userDTO.getPassword(), SALT));
        user.setCreatedTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    @Override
    public void login(UserDTO userDTO, HttpServletResponse response) {
        User user = queryByName(userDTO.getUserName());
        if (user == null) {
            throw new ShipException(ShipExceptionEnum.LOGIN_ERROR);
        }
        String pwd = StringTools.md5Digest(userDTO.getPassword(), SALT);
        if (!pwd.equals(user.getPassword())) {
            throw new ShipException(ShipExceptionEnum.LOGIN_ERROR);
        }
        PayLoad payLoad = new PayLoad(user.getId(), user.getUserName());
        try {
            String token = JwtUtils.generateToken(payLoad);
            Cookie cookie = new Cookie(AdminConstants.TOKEN_NAME, token);
            cookie.setHttpOnly(true);
            // 30min
            cookie.setMaxAge(30 * 60);
            response.addCookie(cookie);
        } catch (Exception e) {
            LOGGER.error("login error", e);
        }
    }

    private User queryByName(String userName) {
        QueryWrapper<User> wrapper = Wrappers.query();
        wrapper.lambda().eq(User::getUserName, userName);
        return userMapper.selectOne(wrapper);
    }

}
