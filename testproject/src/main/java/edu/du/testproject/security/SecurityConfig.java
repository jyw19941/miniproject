package edu.du.testproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // BCrypt 암호화 방식 사용
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/enrollment").authenticated()  // 로그인, 회원가입 페이지는 누구나 접근 가능
                .anyRequest().permitAll()  // 그 외에는 인증된 사용자만 접근 가능
                .and()
                .formLogin()
                .loginPage("/login")  // 커스텀 로그인 페이지
                .loginProcessingUrl("/login")  // 로그인 폼 제출 URL
                .defaultSuccessUrl("/main", true)  // 로그인 성공 후 리다이렉트될 페이지
                .failureUrl("/login?error=true")  // 로그인 실패 후 리다이렉트될 페이지
                .permitAll()  // 로그인 페이지는 누구나 접근 가능
                .and()
                .logout()
                .logoutUrl("/logout")  // 로그아웃 URL
                .logoutSuccessUrl("/");  // 로그아웃 후 리다이렉트될 페이지
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());  // UserDetailsService와 PasswordEncoder 설정
    }
}
