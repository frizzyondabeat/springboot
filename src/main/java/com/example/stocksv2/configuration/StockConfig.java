package com.example.stocksv2.configuration;

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

            stockRepository.saveAll(List.of(stock1,stock2));
        };
    }
}
