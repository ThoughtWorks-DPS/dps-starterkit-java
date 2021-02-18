package io.twdps.starter.example.service.spi.account;

import io.twdps.starter.boot.exception.RequestValidationException;
import io.twdps.starter.example.service.spi.account.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

  Account add(Account resource) throws RequestValidationException;

  List<Account> findByLastName(String lastName);

  Optional<Account> findByUserName(String userName);

  Optional<Account> findById(String id);

  List<Account> findAll();

  Optional<Account> updateById(String id, Account record) throws RequestValidationException;

  Optional<Account> deleteById(String id);
}
