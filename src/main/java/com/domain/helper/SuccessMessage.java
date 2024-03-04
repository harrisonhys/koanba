package com.domain.helper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SuccessMessage {
    @JsonProperty("message")
    private String message;

    public SuccessMessage(String message) {
        this.message = message;
    }
}