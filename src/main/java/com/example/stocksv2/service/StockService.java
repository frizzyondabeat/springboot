package com.example.stocksv2.service;

import com.example.stocksv2.model.Stock;
import com.example.stocksv2.dto.StockDTO;

import java.util.List;
import java.util.Optional;


public interface StockService {
    List<Stock> getAllStocks();

    Optional<Stock> getStockById(Long id);

    List<Stock> createStock(StockDTO stockDTO);

    List<Stock> updateStock(Long id, StockDTO stockDTO);

    void deleteStock(Long id);
}
