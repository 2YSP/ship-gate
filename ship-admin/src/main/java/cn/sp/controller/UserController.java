package cn.sp.controller;

import cn.sp.constants.AdminConstants;
import cn.sp.pojo.UserDTO;
import cn.sp.pojo.vo.Result;
import cn.sp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2021/1/4
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @PostMapping("")
    public Result add(@RequestBody @Validated UserDTO userDTO) {
        userService.add(userDTO);
        return Result.success();
    }


    @PostMapping("/login")
    public void login(@Validated UserDTO userDTO, HttpServletResponse response) throws IOException {
        userService.login(userDTO, response);
        response.sendRedirect("/app/list");
    }

    @GetMapping("/login/page")
    public String loginPage() {
        return "login";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response) throws IOException {
        Cookie cookie = new Cookie(AdminConstants.TOKEN_NAME, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "login";
    }
}
