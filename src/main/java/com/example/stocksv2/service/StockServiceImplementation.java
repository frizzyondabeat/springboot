package com.example.stocksv2.service;

import com.example.stocksv2.dto.StockDTO;
import com.example.stocksv2.exceptions.ApiBadRequestException;
import com.example.stocksv2.exceptions.ApiNotFoundException;
import com.example.stocksv2.model.Stock;
import com.example.stocksv2.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StockServiceImplementation implements StockService{

    private final StockRepository stockRepository;

    @Autowired
    public StockServiceImplementation(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public Page<Stock> getAllStocks(Pageable page) {
        return stockRepository.findAll(page);
    }

    @Override
    public Optional<Stock> getStockById(Long id) {
        if (!stockRepository.existsById(id)){
            log.error("Stock not found!");
            throw new ApiNotFoundException("Stock not found!");
        }
        log.info("Getting stock with id " + id);
        return stockRepository.findById(id);
    }

    @Override
    public List<Stock> createStock(StockDTO stockDTO) {
        if (stockRepository.existsByName(stockDTO.getName())){
            log.error("Stock already exists");
            throw new ApiBadRequestException("Stock already exists");
        }
        Stock stock = new Stock(
                stockDTO.getName(),
                stockDTO.getAmount(),
                Timestamp.valueOf(LocalDateTime.now())
            );
        stockRepository.save(stock);
        log.info("Stock created successfully!");
        return stockRepository.findByName(stockDTO.getName());
    }

    @Override
    @Transactional
    public List<Stock> updateStock(Long id, StockDTO stockDTO) {
        if (stockRepository.findById(id).isEmpty()){
            log.error("No stock found at id " + id);
            throw new ApiNotFoundException("No stock found at id " + id);
        }
        Stock stock = stockRepository.getById(id);
        stock.setAmount(stockDTO.getAmount());
        stockRepository.save(stock);
        log.info("Updated price at id " + id);
        return stockRepository.findByAmount(stockDTO.getAmount());
    }

    @Override
    @Transactional
    public void deleteStock(Long id) {
        if (stockRepository.findById(id).isEmpty()){
            log.error("Stock at " + id + " is not available ");
            throw new ApiNotFoundException("Stock not available " + id);
        }
        stockRepository.deleteById(id);
        log.info("Stock deleted successfully");
    }

}
