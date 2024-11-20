package edu.du.testproject.spring;


import edu.du.testproject.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

@Repository

public class UserDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User selectByEmail(String email) {
        List<User> results = jdbcTemplate.query("select * from user where email = ?",
                new RowMapper<User>() {
            @Override
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User(
                        rs.getString("USERNAME"),
                        rs.getString("EMAIL"),
                        rs.getString("PASSWORD"),
                        rs.getTimestamp("REGDATE").toLocalDateTime());
                user.setId(rs.getInt("ID"));
                return user;
            }
                }, email);
        return results.isEmpty() ? null : results.get(0);
    }


    public void insert(User user) {
        if (user.getRegdate() == null) {
            user.setRegdate(LocalDateTime.now()); // null일 경우 현재 시간 설정
        }

        // 기본값 설정: role이 null인 경우 'user'로 설정
        if (user.getRole() == null) {
            user.setRole("user"); // 기본값 'user' 설정
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(
                        "INSERT INTO USER (USERNAME, EMAIL, PASSWORD, REGDATE, ROLE) VALUES (?, ?, ?, ?, ?)",
                        new String[] { "ID" }); // role 컬럼 추가
                pstmt.setString(1, user.getUsername());
                pstmt.setString(2, user.getEmail());
                pstmt.setString(3, user.getPassword());
                pstmt.setTimestamp(4, Timestamp.valueOf(user.getRegdate())); // null 처리 후 Timestamp 설정
                pstmt.setString(5, user.getRole()); // role 값 삽입
                return pstmt;
            }
        }, keyHolder);

        Number keyValue = keyHolder.getKey();
        user.setId(keyValue.intValue());
    }

}
