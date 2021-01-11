package io.twdps.starter.controller.account;

import io.twdps.starter.api.account.requests.AccountRequest;
import io.twdps.starter.api.account.resources.AccountResource;
import io.twdps.starter.api.account.responses.AccountResponse;
import io.twdps.starter.api.account.responses.AddAccountResponse;
import io.twdps.starter.api.responses.ArrayResponse;
import io.twdps.starter.controller.account.mapper.AccountRequestMapper;
import io.twdps.starter.spi.account.AccountManager;
import io.twdps.starter.spi.account.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class AccountController implements AccountResource {

  private final AccountManager accountManager;
  private final AccountRequestMapper accountRequestMapper;

  /**
   * constructor.
   *
   * @param accountManager instance of account manager
   * @param accountRequestMapper instance of account request mappper
   */
  public AccountController(
      AccountManager accountManager, AccountRequestMapper accountRequestMapper) {

    this.accountManager = accountManager;
    this.accountRequestMapper = accountRequestMapper;
  }

  @Override
  public ResponseEntity<ArrayResponse<AccountResponse>> findEntityByLastName(String lastName) {
    List<Account> accounts = accountManager.findByLastName(lastName);

    if (accounts.size() > 0) {
      return new ResponseEntity<>(
          new ArrayResponse<>(accountRequestMapper.toResponseList(accounts)), HttpStatus.OK);
    } else {
      // TODO: construct error payload
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<AccountResponse> findEntityByUsername(
      @PathVariable(value = "userName") String userName) {
    Optional<Account> account = accountManager.findByUserName(userName);
    if (account.isPresent()) {
      return new ResponseEntity<>(accountRequestMapper.toAccountResponse(account), HttpStatus.OK);
    } else {
      // TODO: construct error payload
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<AddAccountResponse> addEntity(AccountRequest addEntityRequest) {

    log.info("username->{}", addEntityRequest.getUserName());
    Account account = accountRequestMapper.toModel(addEntityRequest);
    Account saved = accountManager.add(account);
    AddAccountResponse response = accountRequestMapper.toAddAccountResponse(saved);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<AccountResponse> findEntityById(String id) {
    log.info("id->{}", id);
    Optional<Account> found = accountManager.findById(id);
    if (found.isPresent()) {
      return new ResponseEntity<>(
          accountRequestMapper.toAccountResponse(found), HttpStatus.OK);
    } else {
      // TODO: construct error payload
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<ArrayResponse<AccountResponse>> findEntities() {
    List<Account> accounts = accountManager.findAll();

    return new ResponseEntity<>(
        new ArrayResponse<>(accountRequestMapper.toResponseList(accounts)), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<AccountResponse> updateEntityById(String id, AccountRequest request) {
    log.info("id->{}", id);
    Optional<Account> found = accountManager.updateById(id, accountRequestMapper.toModel(request));
    if (found.isPresent()) {
      return new ResponseEntity<>(accountRequestMapper.toAccountResponse(found), HttpStatus.OK);
    } else {
      // TODO: construct error payload
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<AccountResponse> deleteEntityById(String id) {
    log.info("id->{}", id);
    Optional<Account> found = accountManager.deleteById(id);
    if (found.isPresent()) {
      return new ResponseEntity<>(accountRequestMapper.toAccountResponse(found), HttpStatus.OK);
    } else {
      // TODO: construct error payload
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }
}
