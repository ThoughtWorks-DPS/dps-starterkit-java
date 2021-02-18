package io.twdps.starter.example.controller.account;

import io.twdps.starter.boot.exception.RequestValidationException;
import io.twdps.starter.boot.exception.ResourceNotFoundException;
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
  public ResponseEntity<AccountResponse> addEntity(AccountRequest addEntityRequest)
      throws RequestValidationException {

    log.info("username->{}", addEntityRequest.getUserName());
    Account resource = mapper.toModel(addEntityRequest);
    Account saved = manager.add(resource);
    AccountResponse response = mapper.toAccountResponse(saved);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<AccountResponse> findEntityById(String id)
      throws ResourceNotFoundException {

    log.info("id->{}", id);
    Optional<Account> found = manager.findById(id);
    return new ResponseEntity<>(
        found
            .map(r -> mapper.toAccountResponse(r))
            .orElseThrow(() -> new ResourceNotFoundException(id)),
        HttpStatus.OK);
  }

  @Override
  public ResponseEntity<ArrayResponse<AccountResponse>> findEntities() {
    List<Account> resources = manager.findAll();

    return new ResponseEntity<>(
        new ArrayResponse<>(mapper.toAccountResponseList(resources)), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<AccountResponse> updateEntityById(String id, AccountRequest request)
      throws ResourceNotFoundException, RequestValidationException {

    log.info("id->{}", id);
    Optional<Account> found = manager.updateById(id, mapper.toModel(request));
    return new ResponseEntity<>(
        found
            .map(r -> mapper.toAccountResponse(r))
            .orElseThrow(() -> new ResourceNotFoundException(id)),
        HttpStatus.OK);
  }

  @Override
  public ResponseEntity<AccountResponse> deleteEntityById(String id)
      throws ResourceNotFoundException {

    log.info("id->{}", id);
    Optional<Account> found = manager.deleteById(id);
    return new ResponseEntity<>(
        found
            .map(r -> mapper.toAccountResponse(r))
            .orElseThrow(() -> new ResourceNotFoundException(id)),
        HttpStatus.OK);
  }
}
