package com.example.account.facade;

import com.example.account.dao.AccountRepository;
import com.example.account.entity.Account;
import com.example.account.model.AccountModel;
import org.springframework.stereotype.Component;

import static com.example.account.generator.AccountNumberGenerator.generateAccountNumber;

@Component
public class AccountFacade {

    private final AccountRepository accountRepository;

    public AccountFacade(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account create(AccountModel model) {
        return Account.builder()
            .id(model.getId())
            .accountNumber(generateAccountNumber())
            .name(model.getName())
            .email(model.getEmail())
            .build();
    }

    public Account getById(String id) {
        return accountRepository.findOne(id);
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public Account update(Account accountDetails) {
        Account account = getById(accountDetails.getId());
        account.setName(accountDetails.getName());
        account.setEmail(accountDetails.getEmail());

        return account;
    }

    public void delete(String id) {
        accountRepository.delete(id);
    }
}
