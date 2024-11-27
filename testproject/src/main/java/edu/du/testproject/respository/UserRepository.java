package edu.du.testproject.respository;

import edu.du.testproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    // username으로 사용자 조회 메서드 추가
    Optional<User> findByUsername(String username);// 추가된 부분

    Optional<User> findById(Long id);
}