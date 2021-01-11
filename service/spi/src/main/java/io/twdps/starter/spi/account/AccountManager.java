package io.twdps.starter.spi.account;

import io.twdps.starter.spi.account.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountManager {

  Account add(Account account);

  List<Account> findByLastName(String lastName);

  Optional<Account> findByUserName(String userName);

  Optional<Account> findById(String id);

  List<Account> findAll();

  Optional<Account> updateById(String id, Account record);

  Optional<Account> deleteById(String id);
}
