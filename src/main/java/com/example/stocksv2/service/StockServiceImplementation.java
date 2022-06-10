package com.example.stocksv2.service;

import com.example.stocksv2.dto.StockDTO;
import com.example.stocksv2.exceptions.ApiBadRequestException;
import com.example.stocksv2.exceptions.ApiNotFoundException;
import com.example.stocksv2.model.Stock;
import com.example.stocksv2.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockServiceImplementation implements StockService{

    private final StockRepository stockRepository;

    @Override
    public List<Stock> getAllStocks(Pageable page) {
        return stockRepository.findAll(page).getContent();
    }

    @Override
    public Stock getStockById(Long id) {
        log.info("Getting stock with id " + id);
        return stockRepository.findById(id).orElseThrow(ApiNotFoundException::new);
    }

    @Override
    public Stock createStock(StockDTO stockDTO) {
        if (stockRepository.existsByName(stockDTO.getName())){
            log.error("Stock already exists");
            throw new ApiBadRequestException("Stock already exists");
        }
        log.info("Stock created successfully!");
        return stockRepository.save(Stock.builder()
                .name(stockDTO.getName())
                .amount(stockDTO.getAmount())
                .lastUpdate(Timestamp.valueOf(LocalDateTime.now()))
                .build());
    }

    @Override
    @Transactional
    public Stock updateStock(Long id, StockDTO stockDTO) {
        return stockRepository.updateById(id, stockDTO.getName(), stockDTO.getAmount()).orElseThrow(ApiNotFoundException::new);
    }

    @Override
    @Transactional
    public void deleteStock(Long id) {
        if (!stockRepository.existsById(id)){
            log.error("Stock at " + id + " is not available ");
            throw new ApiNotFoundException("Stock not available " + id);
        }
        stockRepository.deleteById(id);
        log.info("Stock deleted successfully");
    }

}
