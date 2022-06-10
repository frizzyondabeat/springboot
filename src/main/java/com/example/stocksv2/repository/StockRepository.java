package com.example.stocksv2.repository;

import com.example.stocksv2.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findByName(String name);

    boolean existsByName(String name);

    List<Stock> findByAmount(Double amount);

    @Modifying
    @Query("update Stock s set s.name=?2, s.amount=?3 where s.id=?1")
    Optional<Stock> updateById(Long id, String name, Double amount);
}
