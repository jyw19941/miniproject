package edu.du.testproject.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String username;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;


    @Column(nullable = false)
    private String role;

    private LocalDateTime regdate;



    public User(String username, String email, String password, LocalDateTime regdate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.regdate = regdate != null ? regdate : LocalDateTime.now(); // null일 경우 기본값 설정
    }
}
