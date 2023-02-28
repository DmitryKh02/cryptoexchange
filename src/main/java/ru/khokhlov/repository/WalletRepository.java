package ru.khokhlov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.khokhlov.entity.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @Query(
            value = "SELECT * FROM wallet WHERE key = :key",
            nativeQuery = true)
    Wallet findWalletByKey(@Param("key") String key);

    @Query(
            value = "SELECT rub FROM wallet WHERE key = :key",
            nativeQuery = true)
    double getRub(@Param("key") String key);

    @Query(
            value = "SELECT btc FROM wallet WHERE key = :key",
            nativeQuery = true)
    double getBtc(@Param("key") String key);

    @Query(
            value = "SELECT ton FROM wallet WHERE key = :key",
            nativeQuery = true)
    double getTon(@Param("key") String key);

    @Query(
            value = "SELECT SUM(rub) FROM wallet",
            nativeQuery = true)
    double getSumOfRub();

    @Query(
            value = "SELECT SUM(btc) FROM wallet",
            nativeQuery = true)
    double getSumOfBtc();

    @Query(
            value = "SELECT SUM(ton) FROM wallet",
            nativeQuery = true)
    double getSumOfTon();

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE wallet SET wallet_number =:mean WHERE key = :key",
            nativeQuery = true)
    void setWallet(@Param("mean") String mean, @Param("key") String key);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE wallet SET card_number =:mean WHERE key = :key",
            nativeQuery = true)
    void setCard(@Param("mean") String mean, @Param("key") String key);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE wallet SET rub = rub + :mean WHERE key = :key",
            nativeQuery = true)
    void addRub(@Param("mean") double mean, @Param("key") String key);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE wallet SET btc = btc + :mean WHERE key = :key",
            nativeQuery = true)
    void addBtc(@Param("mean") double mean, @Param("key") String key);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE wallet SET ton = ton + :mean WHERE key = :key",
            nativeQuery = true)
    void addTon(@Param("mean") double mean, @Param("key") String key);


}
