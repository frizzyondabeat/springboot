package com.example.stocksv2.exceptions;

public class ApiBadRequestException extends RuntimeException{
    public ApiBadRequestException(String message) {
        super(message);
    }
}
