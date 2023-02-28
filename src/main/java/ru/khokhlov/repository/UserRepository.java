package ru.khokhlov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.khokhlov.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(
            value = "SELECT count(id) FROM client WHERE email = :email OR username = :username",
            nativeQuery = true)
    int isUserUnique(@Param("username") String username, @Param("email") String email);

    @Query(
            value = "SELECT count(id) FROM client WHERE key = :key",
            nativeQuery = true)
    int isKeyUnique(@Param("key") String key);

    @Query(
            value = "SELECT username FROM client WHERE key = :key",
            nativeQuery = true)
    String isItAdmin(@Param("key") String key);
}