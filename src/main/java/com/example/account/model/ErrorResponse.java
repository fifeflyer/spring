package com.example.account.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ErrorResponse {

    private String method;
    private String url;
    private String parameter;
    private String queryString;
    private String message;

    private List<ValidationError> validation;
}
