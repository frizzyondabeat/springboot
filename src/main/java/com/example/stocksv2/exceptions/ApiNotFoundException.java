package com.example.stocksv2.exceptions;


public class ApiNotFoundException extends RuntimeException{
    public ApiNotFoundException(String message) {
        super(message);
    }

}
