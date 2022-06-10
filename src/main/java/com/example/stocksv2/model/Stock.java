package com.example.stocksv2.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "Stock")
@Table(name = "stock")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Stock {
    @Id
    @SequenceGenerator(
            name = "stock_sequence",
            sequenceName = "stock_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "stock_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;

    @Column(
            name = "amount",
            nullable = false,
            precision = 2
    )
    private Double amount;

    @Column(
            name = "last_update",
            nullable = false
    )
    private Timestamp lastUpdate;

    public Stock(String name, Double amount, Timestamp lastUpdate) {
        this.name = name;
        this.amount = amount;
        this.lastUpdate = lastUpdate;
    }
}
