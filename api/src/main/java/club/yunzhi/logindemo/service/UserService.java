package club.yunzhi.logindemo.service;

import club.yunzhi.logindemo.entity.User;

public interface UserService {
    User getByUsername(String username);

    String[] getUPByAuthStr(String authorization);

    boolean authenticateByUP(String[] usernameAndPassword);
}
