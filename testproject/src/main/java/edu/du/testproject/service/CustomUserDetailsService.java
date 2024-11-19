package edu.du.testproject.service;

import edu.du.testproject.entity.User;
import edu.du.testproject.spring.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Attempting to load user with username: " + username);
        User user = userDAO.selectByEmail(username);
        if (user == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
        System.out.println("User found: " + user);
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())  // 이메일을 username으로 설정
                .password(user.getPassword()) // 암호화된 비밀번호
                .roles("USER")
                .build();
    }
}
