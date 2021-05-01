package io.twdps.starter.example.controller.account;

import io.twdps.starter.boot.exception.RequestValidationException;
import io.twdps.starter.boot.exception.ResourceNotFoundException;
import io.twdps.starter.boot.notifier.EntityLifecycleNotifier;
import io.twdps.starter.example.api.account.requests.AccountRequest;
import io.twdps.starter.example.api.account.requests.SubAccountRequest;
import io.twdps.starter.example.api.account.resources.AccountResource;
import io.twdps.starter.example.api.account.responses.AccountResponse;
import io.twdps.starter.example.api.account.responses.SubAccountResponse;
import io.twdps.starter.example.api.responses.PagedResponse;
import io.twdps.starter.example.controller.account.mapper.AccountRequestMapper;
import io.twdps.starter.example.service.spi.account.AccountService;
import io.twdps.starter.example.service.spi.account.model.Account;
import io.twdps.starter.example.service.spi.account.model.SubAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Optional;

@Slf4j
@RestController
public class AccountController implements AccountResource {

  private final AccountService manager;
  private final AccountRequestMapper mapper;
  private final EntityLifecycleNotifier notifier;
  // TODO: Need to find a better way to determine version of entity
  private final String entityVersion = "0.0.1";

  /**
   * constructor.
   *
   * @param manager instance of account manager
   * @param mapper instance of account request mappper
   */
  public AccountController(
      AccountService manager, AccountRequestMapper mapper, EntityLifecycleNotifier notifier) {
    this.manager = manager;
    this.mapper = mapper;
    this.notifier = notifier;
  }

  @Override
  public ResponseEntity<AccountResponse> addEntity(AccountRequest addEntityRequest)
      throws RequestValidationException {

    log.info("username->{}", addEntityRequest.getUserName());
    Account resource = mapper.toModel(addEntityRequest);
    Account saved = manager.add(resource);
    AccountResponse response = mapper.toAccountResponse(saved);
    notifier.created(saved, entityVersion, URI.create("user:anonymous"));
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
  public ResponseEntity<PagedResponse<AccountResponse>> findEntities(Pageable pageable) {
    Page<Account> resources = manager.findAll(pageable);

    return new ResponseEntity<>(mapper.toAccountResponsePage(resources), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<AccountResponse> updateEntityById(String id, AccountRequest request)
      throws ResourceNotFoundException, RequestValidationException {

    log.info("id->{}", id);
    Optional<Account> found = manager.updateById(id, mapper.toModel(request));
    if (found.isPresent()) {
      notifier.updated(found.get(), entityVersion, URI.create("user:anonymous"));
    }
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
    if (found.isPresent()) {
      notifier.deleted(found.get(), entityVersion, URI.create("user:anonymous"));
    }
    return new ResponseEntity<>(
        found
            .map(r -> mapper.toAccountResponse(r))
            .orElseThrow(() -> new ResourceNotFoundException(id)),
        HttpStatus.OK);
  }

  @Override
  // CSOFF: LineLength
  public ResponseEntity<SubAccountResponse> addSubAccount(String id, SubAccountRequest addEntityRequest)
      // CSON: LineLength
      throws RequestValidationException {

    log.info("username->{}", addEntityRequest.getUserName());
    SubAccount resource = mapper.toModel(addEntityRequest);
    SubAccount saved = manager.addSubAccount(id, resource);
    SubAccountResponse response = mapper.toSubAccountResponse(saved);
    notifier.created(saved, entityVersion, URI.create("user:anonymous"));
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @Override
  // CSOFF: LineLength
  public ResponseEntity<SubAccountResponse> getSubAccount(String id, String subResourceId)
      // CSON: LineLength
      throws ResourceNotFoundException {

    log.info("id->{}", id);
    Optional<SubAccount> found = manager.getSubAccount(id, subResourceId);
    return new ResponseEntity<>(
        found
            .map(r -> mapper.toSubAccountResponse(r))
            .orElseThrow(() -> new ResourceNotFoundException(id)),
        HttpStatus.OK);
  }


  @Override
  // CSOFF: LineLength
  public ResponseEntity<PagedResponse<SubAccountResponse>> getSubAccounts(String id, Pageable pageable) {
    // CSON: LineLength
    Page<SubAccount> resources = manager.getSubAccounts(id, pageable);

    return new ResponseEntity<>(mapper.toSubAccountResponsePage(resources), HttpStatus.OK);
  }


  @Override
  // CSOFF: LineLength
  public ResponseEntity<SubAccountResponse> updateSubAccount(String id, String subResourceId, SubAccountRequest request)
      // CSON: LineLength
      throws ResourceNotFoundException, RequestValidationException {

    log.info("id->{}", id);
    Optional<SubAccount> found =
        manager.updateSubAccount(id, subResourceId, mapper.toModel(request));
    if (found.isPresent()) {
      notifier.updated(found.get(), entityVersion, URI.create("user:anonymous"));
    }
    return new ResponseEntity<>(
        found
            .map(r -> mapper.toSubAccountResponse(r))
            .orElseThrow(() -> new ResourceNotFoundException(id)),
        HttpStatus.OK);
  }

  @Override
  // CSOFF: LineLength
  public ResponseEntity<SubAccountResponse> deleteSubAccount(String id, String subResourceId)
      // CSON: LineLength
      throws ResourceNotFoundException {

    log.info("id->{}", id);
    Optional<SubAccount> found = manager.deleteSubAccount(id, subResourceId);
    if (found.isPresent()) {
      notifier.deleted(found.get(), entityVersion, URI.create("user:anonymous"));
    }
    return new ResponseEntity<>(
        found
            .map(r -> mapper.toSubAccountResponse(r))
            .orElseThrow(() -> new ResourceNotFoundException(id)),
        HttpStatus.OK);
  }
}
