package com.domain.helper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ErrorMessage {
    @JsonProperty("message")
    private String message;

    public ErrorMessage(String message) {
        this.message = message;
    }
    
}