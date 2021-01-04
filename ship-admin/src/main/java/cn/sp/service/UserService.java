package cn.sp.service;

import cn.sp.pojo.UserDTO;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2021/1/4
 */
public interface UserService {

    void add(UserDTO userDTO);

    void login(UserDTO userDTO, HttpServletResponse response);

}
