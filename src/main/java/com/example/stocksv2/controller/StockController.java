package com.example.stocksv2.controller;

import com.example.stocksv2.dto.StockDTO;
import com.example.stocksv2.exceptions.ApiBadRequestException;
import com.example.stocksv2.exceptions.ApiNotFoundException;
import com.example.stocksv2.model.Stock;
import com.example.stocksv2.service.StockServiceImplementation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/stocks")
@Slf4j
public class StockController {

    private final StockServiceImplementation stockServiceImplementation;

    @Autowired
    public StockController(StockServiceImplementation stockServiceImplementation) {
        this.stockServiceImplementation = stockServiceImplementation;
    }

    @GetMapping
    public ResponseEntity<Page<Stock>> getAllStocks(
            @PageableDefault (sort = "id", direction = Sort.Direction.ASC, size = 3) Pageable page){
            try {
                Page<Stock> stockList = stockServiceImplementation.getAllStocks(page);
                return new ResponseEntity<>(stockList, HttpStatus.OK);
            } catch (Exception exception){
                log.error("Error getting stocks!");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

    }

    @GetMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Optional<Stock>> getStockById(@PathVariable (value = "id") Long id) {
       try {
           log.info("Attempting to get stock with id " + id);
           Optional<Stock> stockById = stockServiceImplementation.getStockById(id);
           return new ResponseEntity<>(stockById, HttpStatus.FOUND);
       } catch (ApiNotFoundException notFoundException){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('stock:write')")
    public ResponseEntity<List<Stock>> createStock(@RequestBody StockDTO stockDTO) {
        try {
            log.info("Attempting to create new stock");
            List<Stock> createdStock = stockServiceImplementation.createStock(stockDTO);
            return new ResponseEntity<>(createdStock, HttpStatus.CREATED);
        } catch (ApiBadRequestException exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(path = "{id}")
    @PreAuthorize("hasAuthority('stock:write')")
    public ResponseEntity<List<Stock>> updateStocks(@PathVariable (name = "id") Long id, @RequestBody StockDTO stockDTO){
        try {
            log.info("Attempting to update stock with id " + id);
            List<Stock> updatedStock = stockServiceImplementation.updateStock(id, stockDTO);
            return new ResponseEntity<>(updatedStock, HttpStatus.OK);
        } catch (ApiNotFoundException notFoundException){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasAuthority('stock:write')")
    public ResponseEntity<Void> deleteStock(@PathVariable (name = "id") Long id){
        try {
            log.info("Attempting to delete stock with id " + id);
            stockServiceImplementation.deleteStock(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (ApiNotFoundException notFoundException){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
