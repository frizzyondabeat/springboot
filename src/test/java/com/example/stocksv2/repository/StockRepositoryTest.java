package com.example.stocksv2.repository;

import com.example.stocksv2.model.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StockRepositoryTest {
    @Autowired
    private StockRepository underTest;

    @Nested
    class whenFindingByName {
        @Test
        void itShouldFindByName() {
            String NAME = "XRP";
            assertThat(underTest.findByName(NAME).get(0)).isExactlyInstanceOf(Stock.class);
        }

        @BeforeEach
        void setup() {
            Stock stock = new Stock(
                    null,
                    "XRP",
                    14532.89,
                    Timestamp.valueOf(LocalDateTime.now())
            );
            underTest.save(stock);
        }
    }



    @Nested
    class WhenExistingByName {
        @Test
        void itShouldExistByName(){
            String NAME = "GRU";
            assertThat(underTest.existsByName(NAME)).isTrue();
        }

        @Test
        void itShouldNotExistByName(){
            String NAME = "JAY";
            assertThat(underTest.existsByName(NAME)).isFalse();
        }

        @BeforeEach
        void setup() {
            Stock stock = new Stock(
                    null,
                    "GRU",
                    149532.59,
                    Timestamp.valueOf(LocalDateTime.now())
            );
            underTest.save(stock);
        }
    }

    @Nested
    class WhenFindingByAmount {

        @Test
        void itShouldFindByAmount(){
            Double amount = 10006532.99;
            assertThat(underTest.findByAmount(amount).get(0)).isExactlyInstanceOf(Stock.class);
        }

        @BeforeEach
        void setup() {
            Stock stock = new Stock(
                    null,
                    "FRC",
                    10006532.99,
                    Timestamp.valueOf(LocalDateTime.now())
            );
            underTest.save(stock);
        }
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme