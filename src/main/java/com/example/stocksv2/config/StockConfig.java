package com.example.stocksv2.config;

import com.example.stocksv2.model.Stock;
import com.example.stocksv2.repository.StockRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class StockConfig {

    @Bean
    CommandLineRunner commandLineRunner(StockRepository stockRepository){
        return args -> {
            Stock stock1 = new Stock(
                    "BTC",
                    16_307_756.40,
                    Timestamp.valueOf(LocalDateTime.now())
            );

            Stock stock2 = new Stock(
                    "ETH",
                    1_256_509.86,
                    Timestamp.valueOf(LocalDateTime.now())
            );

            Stock stock3 = new Stock(
                    "BNB",
                    167_210.69,
                    Timestamp.valueOf(LocalDateTime.now())
            );

            Stock stock4 = new Stock(
                    "ZRX",
                    277.48,
                    Timestamp.valueOf(LocalDateTime.now())
            );

            Stock stock5 = new Stock(
                    "TRX",
                    24.75,
                    Timestamp.valueOf(LocalDateTime.now())
            );

            stockRepository.saveAll(List.of(stock1,stock2,stock3,stock4,stock5));
        };
    }
}
