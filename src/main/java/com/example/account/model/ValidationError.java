package com.example.account.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ValidationError {

    private String attribute;
    private String reason;
}
