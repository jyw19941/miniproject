package edu.du.testproject.spring;


import edu.du.testproject.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

@Repository

public class UserDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User selectByEmail(String email) {
        List<User> results = jdbcTemplate.query("select * from users where email = ?",
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

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(
                        "insert into USERS (USERNAME,EMAIL,PASSWORD,REGDATE) VALUES (?, ?, ?, ?)",
                        new String[] { "ID"});
                pstmt.setString(1, user.getUsername());
                pstmt.setString(2, user.getEmail());
                pstmt.setString(3, user.getPassword());
                pstmt.setTimestamp(4, Timestamp.valueOf(user.getRegdate())); // null 처리 후 Timestamp 설정
                return pstmt;
            }
        }, keyHolder);

        Number keyValue = keyHolder.getKey();
        user.setId(keyValue.intValue());
    }

}
