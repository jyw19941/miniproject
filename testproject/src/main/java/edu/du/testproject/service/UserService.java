//package edu.du.testproject.service;
//
//import edu.du.testproject.spring.UserDTO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//    public boolean registerUser(UserDTO userDTO) {
//        if (!userDTO.isPasswordMatching()) {
//            throw new IllegalArgumentException("Passwords do not match");
//        }
//
//        // 비밀번호 암호화
//        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
//
//        // UserDTO -> User 엔티티로 변환
//        UserDTO user = new UserDTO();
//        user.setUsername(userDTO.getUsername());
//        user.setEmail(userDTO.getEmail());
//        user.setPassword(encodedPassword);
//
//        // 유저 저장
//        userRepository.save(user);
//        return true;
//    }
//}
