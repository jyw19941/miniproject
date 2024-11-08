package edu.du.testproject.spring;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository

public class UserDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserDTO selectByEmail(String email) {
        List<UserDTO> results = jdbcTemplate.query("select * from users where email = ?",
                new RowMapper<UserDTO>() {
            @Override
                    public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserDTO user = new UserDTO(
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


    public void insert(UserDTO user) {
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
                pstmt.setTimestamp(4, Timestamp.valueOf(user.getRegdate()));
                return pstmt;
            }
        },keyHolder);
        Number keyVaule = keyHolder.getKey();
        user.setId(keyVaule.intValue());
    }
}
