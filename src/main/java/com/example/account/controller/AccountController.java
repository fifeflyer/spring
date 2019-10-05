package com.example.account.controller;

import com.example.account.AccountApplication;
import com.example.account.exception.AccountNotFoundException;
import com.example.account.model.AccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "v1/account", produces = APPLICATION_JSON_VALUE)
public class AccountController {

    private final AccountApplication accountApplication;

    @Autowired
    public AccountController(AccountApplication accountApplication) {
        this.accountApplication = accountApplication;
    }

    @GetMapping(value = "/{id}")
    public AccountModel retrieveAccount(@PathVariable String id) {
        return accountApplication.retrieveAccount(id).orElseThrow(AccountNotFoundException::new);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public AccountModel createAccount(@RequestBody @Valid AccountModel account) {
        return accountApplication.createAccount(account);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public AccountModel updateAccount(@RequestBody @Valid AccountModel account) {
        return accountApplication.updateAccount(account);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteAccount(@PathVariable String id) {
        accountApplication.deleteAccount(id);
    }
}
