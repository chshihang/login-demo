package club.yunzhi.logindemo.config;


import club.yunzhi.logindemo.entity.User;
import club.yunzhi.logindemo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 系统启动时添加系统管理员
 */
@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    UserRepository userRepository;
    public CommandLineRunnerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if (!this.userRepository.findByUsername("13100000000").isPresent()) {
            User user = new User();
            //角色为系统管理员
            user.setUsername("13100000000");
            user.setPassword("admin");
            this.userRepository.save(user);
        }
    }

}
