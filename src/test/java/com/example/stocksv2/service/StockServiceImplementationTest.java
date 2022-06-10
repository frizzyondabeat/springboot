package com.example.stocksv2.service;

import com.example.stocksv2.dto.StockDTO;
import com.example.stocksv2.exceptions.ApiBadRequestException;
import com.example.stocksv2.exceptions.ApiNotFoundException;
import com.example.stocksv2.model.Stock;
import com.example.stocksv2.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StockServiceImplementationTest {

    @Mock
    private StockRepository stockRepository;
    @InjectMocks
    private StockServiceImplementation underTest;

    @Nested
    class WhenGettingAllStocks {
        @Mock
        private Pageable page;

        @Test
        void canGetAllStocks(){
            underTest.getAllStocks(page);
            verify(stockRepository).findAll(page);
        }

        @BeforeEach
        void setup() {
            underTest = new StockServiceImplementation(stockRepository);
        }
    }

    @Nested
    class WhenGettingStockById {
        @Test
        void canThrowExceptionInGetStockById(){
            assertThrows(ApiNotFoundException.class,() -> underTest.getStockById(1L),"Stock not found");
        }

        @Test
        void canGetStockById(){
            Long id = 1L;
            given(stockRepository.existsById(id)).willReturn(true);
            underTest.getStockById(id);
            verify(stockRepository).findById(id);
        }

        @BeforeEach
        void setup() {
            underTest = new StockServiceImplementation(stockRepository);
        }
    }

    @Nested
    class WhenCreatingStock {
        @Mock
        private StockDTO stockDTO;

        @Test
        void canCreateStock(){
            String name = "FGP";
            verify(stockRepository).findByName(name);
        }

        @Test
        void canThrowExceptionWhenCreatingStock(){
            StockDTO stockDTO1 = new StockDTO("FGP", 7812.89);
            given(stockRepository.existsByName(stockDTO1.getName())).willReturn(true);
            assertThatThrownBy(
                    () -> underTest.createStock(stockDTO1))
                    .isInstanceOf(ApiBadRequestException.class)
                    .hasMessageContaining("Stock already exists");


        }

        @BeforeEach
        void setup() {
            underTest = new StockServiceImplementation(stockRepository);
            StockDTO stock = new StockDTO("FGP", 5689.25);
            underTest.createStock(stock);
        }
    }

    @Nested
    class WhenUpdatingStock {
        @Mock
        private StockDTO stockDTO;

        @Test
        void canUpdateStock(){
            StockDTO stockDTO = new StockDTO("FGOP", 765.98);
            given(stockRepository.existsById(1L)).willReturn(true);
            given(stockRepository.getById(1L)).
                    willReturn(new Stock("FGOP", 567889.25, Timestamp.valueOf(LocalDateTime.now())));
            underTest.updateStock(1L, stockDTO);
            verify(stockRepository).findByAmount(stockDTO.getAmount());
        }

        @Test
        void canThrowExceptionWhenUpdatingStock(){
            Long id = 1L;
            assertThrows(
                    ApiNotFoundException.class,
                    () -> underTest.updateStock(id, new StockDTO("XFU", 245.98)),
                    "No stock found at id " + id);

            verify(stockRepository, never()).save(any());
        }

        @BeforeEach
        void setup() {
            underTest = new StockServiceImplementation(stockRepository);
            StockDTO stock = new StockDTO("FGOP", 567889.25);
        }
    }

    @Nested
    class WhenDeletingStock {

        @Test
        void canDeleteStock(){
            Long id = 1L;
            given(stockRepository.existsById(id)).willReturn(true);
            underTest.deleteStock(id);
            verify(stockRepository).deleteById(id);
        }

        @Test
        void willThrowExceptionWhenDeletingStock(){
            Long id = 1L;
            given(!stockRepository.existsById(id)).willReturn(false);
            assertThrows(
                    ApiNotFoundException.class,
                    () -> underTest.deleteStock(id),
                    "Stock not available " + id);
        }
        @BeforeEach
        void setup() {
            underTest = new StockServiceImplementation(stockRepository);
        }
    }
}