package com.example.stocksv2.security.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserAuthorities {
    STOCK_READ("stock:read"),
    STOCK_WRITE("stock:write");

    private final String permission;
}
