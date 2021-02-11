package io.twdps.starter.example.controller.account;

import io.twdps.starter.example.api.account.requests.AccountRequest;
import io.twdps.starter.example.api.account.resources.AccountResource;
import io.twdps.starter.example.api.account.responses.AccountResponse;
import io.twdps.starter.example.api.responses.ArrayResponse;
import io.twdps.starter.example.controller.account.mapper.AccountRequestMapper;
import io.twdps.starter.example.service.spi.account.AccountService;
import io.twdps.starter.example.service.spi.account.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class AccountController implements AccountResource {

  private final AccountService manager;
  private final AccountRequestMapper mapper;

  /**
   * constructor.
   *
   * @param manager instance of account manager
   * @param mapper instance of account request mappper
   */
  public AccountController(AccountService manager, AccountRequestMapper mapper) {
    this.manager = manager;
    this.mapper = mapper;
  }

  @Override
  public ResponseEntity<AccountResponse> addEntity(AccountRequest addEntityRequest) {

    log.info("username->{}", addEntityRequest.getUserName());
    Account resource = mapper.toModel(addEntityRequest);
    Account saved = manager.add(resource);
    AccountResponse response = mapper.toAccountResponse(saved);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<AccountResponse> findEntityById(String id) {
    log.info("id->{}", id);
    Optional<Account> found = manager.findById(id);
    if (found.isPresent()) {
      return new ResponseEntity<>(mapper.toAccountResponse(found), HttpStatus.OK);
    } else {
      // TODO: construct error payload
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<ArrayResponse<AccountResponse>> findEntities() {
    List<Account> resources = manager.findAll();

    return new ResponseEntity<>(
        new ArrayResponse<>(mapper.toAccountResponseList(resources)), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<AccountResponse> updateEntityById(String id, AccountRequest request) {
    log.info("id->{}", id);
    Optional<Account> found = manager.updateById(id, mapper.toModel(request));
    if (found.isPresent()) {
      return new ResponseEntity<>(mapper.toAccountResponse(found), HttpStatus.OK);
    } else {
      // TODO: construct error payload
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<AccountResponse> deleteEntityById(String id) {
    log.info("id->{}", id);
    Optional<Account> found = manager.deleteById(id);
    if (found.isPresent()) {
      return new ResponseEntity<>(mapper.toAccountResponse(found), HttpStatus.OK);
    } else {
      // TODO: construct error payload
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }
}
