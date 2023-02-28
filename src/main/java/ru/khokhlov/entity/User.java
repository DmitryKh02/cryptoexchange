package ru.khokhlov.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "client")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String key;
    @Column(nullable = false)
    String username;
    @Column(nullable = false)
    String email;

    public User() {
    }

    public User(Long id, String username, String email, String key){
        this.id = id;
        this.username = username;
        this.email = email;
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return key.equals(user.key);
    }

    @Override
    public String toString() {
        return "ID: " + id +
                "KEY: " + key +
                "USERNAME: " + username +
                "E-MAIL: " + email + "\n";
    }
}
