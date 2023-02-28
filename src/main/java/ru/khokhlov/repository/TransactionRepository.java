package ru.khokhlov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.khokhlov.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(
            value = "SELECT COUNT(id) FROM transaction WHERE date BETWEEN :begin AND :end",
            nativeQuery = true)
    int countTransactionByDate(@Param("begin")String begin, @Param("end")String end);
}
