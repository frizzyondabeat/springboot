package com.example.stocksv2.controller;

import com.example.stocksv2.dto.StockDTO;
import com.example.stocksv2.model.Stock;
import com.example.stocksv2.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/stocks")
@Slf4j
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks(
            @PageableDefault (sort = "id", direction = Sort.Direction.ASC, size = 5) Pageable page){
        try {
            log.info("Fetching all stocks...");
            return new ResponseEntity<>(stockService.getAllStocks(page), HttpStatus.OK);
        } catch (Exception exception){
            log.error("Error getting stocks!.\nMessage:{}", exception.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Stock> getStockById(@PathVariable (value = "id") Long id) {
        try {
            log.info("Attempting to get stock with id " + id);
            return new ResponseEntity<>(stockService.getStockById(id), HttpStatus.FOUND);
        } catch (Exception exception){
            log.error("Error getting stock with id[{}].\nMessage:{}", id, exception.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('stock:write')")
    public ResponseEntity<Object> createStock(@RequestBody StockDTO stockDTO) {
        try {
            log.info("Attempting to create new stock");
            return new ResponseEntity<>(stockService.createStock(stockDTO), HttpStatus.CREATED);
        } catch (Exception exception){
            log.error("Error creating stock[{}].\nMessage:{}", stockDTO.getName(), exception.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(path = "{id}")
    @PreAuthorize("hasAuthority('stock:write')")
    public ResponseEntity<Stock> updateStocks(
            @PathVariable (name = "id") Long id,
            @RequestBody StockDTO stockDTO){
        try {
            log.info("Attempting to update stock with id " + id);
            return new ResponseEntity<>(stockService.updateStock(id, stockDTO), HttpStatus.CREATED);
        } catch (Exception exception){
            log.error("Error updating stock at id[{}].\nMessage:{}", id, exception.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasAuthority('stock:write')")
    public ResponseEntity<Void> deleteStock(@PathVariable (name = "id") Long id){
        try {
            log.info("Attempting to delete stock with id " + id);
            stockService.deleteStock(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception exception){
            log.error("Error deleting stock.\nMessage:{}", exception.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
