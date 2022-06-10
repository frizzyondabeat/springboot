package com.example.stocksv2.service;

import com.example.stocksv2.dto.StockDTO;
import com.example.stocksv2.model.Stock;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface StockService {
    List<Stock> getAllStocks(Pageable page);

    Stock getStockById(Long id);

    Stock createStock(StockDTO stockDTO);

    Stock updateStock(Long id, StockDTO stockDTO);

    void deleteStock(Long id);
}
