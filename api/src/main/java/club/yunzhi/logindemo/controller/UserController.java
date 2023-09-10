package club.yunzhi.logindemo.controller;

import club.yunzhi.logindemo.entity.User;
import club.yunzhi.logindemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Arrays;
import java.util.Base64;

@RestController
@RequestMapping("user")
public class UserController {
    UserService userService;

    UserController(UserService userService) {
            this.userService = userService;
    }


    @GetMapping("login")
    User login(HttpServletRequest httpServletRequest) {
        String[] usernameAndPassword = this.userService.getUPByAuthStr(httpServletRequest.getHeader("Authorization"));
        if (this.userService.authenticateByUP(usernameAndPassword)) {
            return this.userService.getByUsername(usernameAndPassword[0]);
        };
        return null;
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