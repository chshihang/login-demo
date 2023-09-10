package club.yunzhi.logindemo.service;

import club.yunzhi.logindemo.entity.User;
import club.yunzhi.logindemo.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    UserRepository userRepository;
    AuthenticationManager authenticationManager;

    UserServiceImpl(UserRepository userRepository,
                    AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public User getByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(RuntimeException::new);
    }

    @Override
    public String[] getUPByAuthStr(String authorizationString) {
        String str = authorizationString.substring(6);
        String decodedMessage = new String(Base64.getDecoder().decode(str), StandardCharsets.US_ASCII);
        return decodedMessage.split(":");
    }

    @Override
    public boolean authenticateByUP(String[] usernameAndPassword) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(usernameAndPassword[0], usernameAndPassword[1]);
        Authentication authentication = authenticationManager.authenticate(token);
        return authentication.isAuthenticated();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("用户不存在"));

        // 设置用户角色
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
    }
}
