package club.yunzhi.logindemo.controller;

import club.yunzhi.logindemo.entity.User;
import club.yunzhi.logindemo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("user")
public class UserController {

    UserService userService;

    UserController(UserService userService) {
            this.userService = userService;
    }


    @GetMapping("login")
    User login(Principal user) {
        return this.userService.getByUsername(user.getName());
    }

    @GetMapping("me")
    User me(Principal user) {
        return this.userService.getByUsername(user.getName());
    }

    @RequestMapping("hello")
    String hello() {
        return "hello world";
    }

}