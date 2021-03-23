package io.twdps.starter.example.service.spi.account;

import io.twdps.starter.boot.exception.RequestValidationException;
import io.twdps.starter.example.service.spi.account.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AccountService {

  Account add(Account resource) throws RequestValidationException;

  Page<Account> findByLastName(String lastName, Pageable pageable);

  Optional<Account> findByUserName(String userName);

  Optional<Account> findById(String id);

  Page<Account> findAll(Pageable pageable);

  Optional<Account> updateById(String id, Account record) throws RequestValidationException;

  Optional<Account> deleteById(String id);
}
