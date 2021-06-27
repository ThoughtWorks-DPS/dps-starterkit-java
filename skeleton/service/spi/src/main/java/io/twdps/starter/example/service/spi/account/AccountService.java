package io.twdps.starter.example.service.spi.account;

import io.twdps.starter.boot.exception.RequestValidationException;
import io.twdps.starter.example.service.spi.account.model.Account;
import io.twdps.starter.example.service.spi.account.model.SubAccount;
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

  SubAccount addSubAccount(String id, SubAccount subResource) throws RequestValidationException;

  Page<SubAccount> getSubAccounts(String id, Pageable pageable);

  Optional<SubAccount> getSubAccount(String id, String subResourceId);

  // CSOFF: LineLength
  Optional<SubAccount> updateSubAccount(String id, String subResourceId, SubAccount subResource)
      throws RequestValidationException;
  // CSON: LineLength

  Optional<SubAccount> deleteSubAccount(String id, String subResourceId);
}
