package cn.sp.controller;

import cn.sp.pojo.UserDTO;
import cn.sp.pojo.vo.Result;
import cn.sp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2021/1/4
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    public Result add(@RequestBody @Validated UserDTO userDTO) {
        userService.add(userDTO);
        return Result.success();
    }

    @PostMapping("/login")
    public Result login(@RequestBody @Validated UserDTO userDTO, HttpServletResponse response) {
        userService.login(userDTO,response);
        return Result.success();
    }
}
