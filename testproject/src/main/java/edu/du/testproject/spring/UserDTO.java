package edu.du.testproject.spring;


import java.time.LocalDateTime;

public class UserDTO {

    private int id;
    private String username;
    private String email;
    private String password;
    private LocalDateTime regdate = LocalDateTime.now();

    // 기본 생성자
    public UserDTO() {
    }

    // 모든 필드를 포함한 생성자
    public UserDTO(String username, String email, String password, LocalDateTime regdate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.regdate = regdate;
    }

    // Getter와 Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getRegdate() {
        return regdate;
    }

    public void setRegdate(LocalDateTime regdate) {
        this.regdate = regdate;
    }
}