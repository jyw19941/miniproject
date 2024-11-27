package edu.du.testproject.service;

import edu.du.testproject.entity.User;
import edu.du.testproject.respository.UserRepository;
import edu.du.testproject.spring.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        return userRepository.save(user);  // save 메서드 호출로 DB에 insert
    }

    public User getAuthenticatedUser(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return null;  // 인증되지 않은 경우
        }
        return userRepository.findByUsername(username).orElse(null);  // Optional 처리
    }

    public Optional<User> getUserById(long userId) {
        return userRepository.findById(userId); // findById는 Optional을 반환합니다.
    }

    // 이메일로 사용자 정보 조회
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }
}
