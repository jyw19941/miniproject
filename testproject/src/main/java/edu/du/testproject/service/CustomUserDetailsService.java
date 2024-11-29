package edu.du.testproject.service;

import edu.du.testproject.entity.User;
import edu.du.testproject.respository.UserRepository;
import edu.du.testproject.spring.UserDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("=========>사용자: " + username);

        // 사용자 이메일로 조회
        Optional<User> user = userRepository.findByEmail(username);

        // Optional이 비어있으면 예외를 던짐
        if (user.isEmpty()) {
            log.error("사용자를 찾을 수 없습니다: " + username);
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        // Optional에서 값이 있으면 UserDetails로 변환하여 반환
        return toUserDetails(user.get());
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private UserDetails toUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
