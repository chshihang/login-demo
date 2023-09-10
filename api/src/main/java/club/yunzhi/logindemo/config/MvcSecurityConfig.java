package club.yunzhi.logindemo.config;

import club.yunzhi.logindemo.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.http.HttpSessionStrategy;

@Configuration
@EnableWebSecurity
@EnableSpringHttpSession
public class MvcSecurityConfig
 extends WebSecurityConfigurerAdapter {

  private final BCryptPasswordEncoder passwordEncoder;

  public MvcSecurityConfig() {
    this.passwordEncoder = new BCryptPasswordEncoder();
    User.setPasswordEncoder(this.passwordEncoder);
  }

  /**
   * https://spring.io/guides/gs/securing-web/
   *
   * @param http http安全
   * @throws Exception 异常
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        // 开放端口
        .antMatchers("/h2-console/**").permitAll()
        .antMatchers("/wechat/**").permitAll()
        .antMatchers("/websocket/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .httpBasic()
        .and().cors()
        .and().csrf().disable()
        .formLogin().disable();

    http.headers().frameOptions().disable();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return this.passwordEncoder;
  }

  @Bean
  public HttpSessionStrategy httpSessionStrategy() {
    return new HeaderAndParamHttpSessionStrategy();
  }

  @Bean
  public SessionRepository sessionRepository() {
    return new MapSessionRepository();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
