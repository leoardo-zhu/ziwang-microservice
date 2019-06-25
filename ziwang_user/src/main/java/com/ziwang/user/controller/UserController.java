package com.ziwang.user.controller;

import com.ziwang.user.pojo.User;
import com.ziwang.user.service.UserService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @PostMapping("/sendsms/{mobile}")
    public Result sendSms(@PathVariable String mobile) {
        userService.sendSms(mobile);
        return new Result(true, StatusCode.OK, "发送成功");
    }

    @PostMapping("/register/{code}")
    public Result register(@PathVariable String code, @RequestBody User user) {
        userService.register(code, user);
        return new Result(true, StatusCode.OK, "注册成功");
    }

    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> user) {
        return new Result(true, StatusCode.OK, "登录成功", userService.loginByAccount(user));
    }

    @PostMapping("/login/{code}")
    public Result login(@PathVariable String code, @RequestBody User user) {
        return new Result(true, StatusCode.OK, "登录成功", userService.loginBySms(code, user));
    }

    @PostMapping("/dologin")
    public Result doLogin() {
        String token = request.getHeader("Authorization");
        System.out.println(token);
        return userService.doLogin(token);
    }

    @GetMapping("/{id}")
    public Result getUser(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "用户信息", userService.user(id));
    }

    @GetMapping("/token/{token}")
    public Result getUserInfo(@PathVariable String token) {
        return new Result(true, StatusCode.OK, "用户信息", userService.userInfo(token));
    }

    @PutMapping
    public Result updateUser(@RequestBody User user){
        userService.updateUser(user);
        return new Result(true,StatusCode.OK,"修改成功");
    }
}
