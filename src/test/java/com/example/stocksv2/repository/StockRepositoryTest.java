package com.example.stocksv2.repository;

import com.example.stocksv2.model.Stock;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StockRepositoryTest {

    @Autowired
    private StockRepository underTest;


    @Test
    void itShouldFindByName() {
        String NAME = "XRP";
        assertThat(underTest.findByName(NAME)).isExactlyInstanceOf(List.of(Stock.class));
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


//    @Nested
//    class WhenExistsingByName {
//        private final String NAME = "NAME";
//
//        @BeforeEach
//        void setup() {
//        }
//    }
//
//    @Nested
//    class WhenFindingByAmount {
//        @BeforeEach
//        void setup() {
//        }
//    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme