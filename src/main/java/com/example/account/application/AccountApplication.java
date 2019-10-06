package com.example.account.application;

import com.example.account.entity.Account;
import com.example.account.facade.AccountFacade;
import com.example.account.model.AccountModel;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountApplication {

    private final AccountFacade accountFacade;

    public AccountApplication(AccountFacade accountFacade) {
        this.accountFacade = accountFacade;
    }

    @Transactional
    public Optional<AccountModel> retrieveAccount(String id) {
        Account account = accountFacade.getById(id);

        return Objects.isNull(account)
            ? Optional.empty()
            : Optional.of(new DozerBeanMapper().map(account, AccountModel.class));
    }

    @Transactional
    public AccountModel createAccount(AccountModel accountModel) {
        Account account = accountFacade.save(accountFacade.create(accountModel));
        accountModel.setId(account.getId());
        accountModel.setAccountNumber(account.getAccountNumber());
        return accountModel;
    }

    @Transactional
    public AccountModel updateAccount(AccountModel accountModel) {
        accountFacade.update(accountFacade.create(accountModel));
        return accountModel;
    }

    @Transactional
    public void deleteAccount(String id) {
        accountFacade.delete(id);
    }
}
