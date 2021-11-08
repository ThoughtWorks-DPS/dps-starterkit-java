package io.twdps.starter.example.controller.account;

import io.twdps.starter.boot.exception.RequestValidationException;
import io.twdps.starter.boot.exception.ResourceNotFoundException;
import io.twdps.starter.boot.notifier.lifecycle.entity.spi.EntityLifecycleNotifier;
import io.twdps.starter.example.api.account.requests.SubAccountRequest;
import io.twdps.starter.example.api.account.resources.AccountSubAccountResource;
import io.twdps.starter.example.api.account.responses.SubAccountResponse;
import io.twdps.starter.example.api.responses.PagedResponse;
import io.twdps.starter.example.controller.account.mapper.AccountSubAccountRequestMapper;
import io.twdps.starter.example.service.spi.account.AccountSubAccountService;
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
public class AccountSubAccountController implements AccountSubAccountResource {

  private final AccountSubAccountService manager;
  private final AccountSubAccountRequestMapper mapper;
  private final EntityLifecycleNotifier notifier;
  // TODO: Need to find a better way to determine version of entity
  private final String entityVersion = "0.0.1";

  /**
   * constructor.
   *
   * @param manager instance of Account manager
   * @param mapper instance of Account request mappper
   */
  public AccountSubAccountController(
      AccountSubAccountService manager,
      AccountSubAccountRequestMapper mapper,
      EntityLifecycleNotifier notifier) {
    this.manager = manager;
    this.mapper = mapper;
    this.notifier = notifier;
  }

  @Override
  // CSOFF: LineLength
  public ResponseEntity<SubAccountResponse> addSubEntity(
      String id, SubAccountRequest addEntityRequest)
      // CSON: LineLength
      throws RequestValidationException {

    log.info("username->{}", addEntityRequest.getUserName());
    SubAccount resource = mapper.toModel(addEntityRequest);
    SubAccount saved = manager.add(id, resource);
    SubAccountResponse response = mapper.toSubAccountResponse(saved);
    notifier.created(saved, entityVersion, URI.create("user:anonymous"));
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @Override
  // CSOFF: LineLength
  public ResponseEntity<SubAccountResponse> getSubEntity(String id, String subResourceId)
      // CSON: LineLength
      throws ResourceNotFoundException {

    log.info("id->{}", id);
    Optional<SubAccount> found = manager.findById(id, subResourceId);
    return new ResponseEntity<>(
        found
            .map(r -> mapper.toSubAccountResponse(r))
            .orElseThrow(() -> new ResourceNotFoundException(id)),
        HttpStatus.OK);
  }

  @Override
  // CSOFF: LineLength
  public ResponseEntity<PagedResponse<SubAccountResponse>> getSubEntities(
      String id, Pageable pageable) {
    // CSON: LineLength
    Page<SubAccount> resources = manager.findAll(id, pageable);

    return new ResponseEntity<>(mapper.toSubAccountResponsePage(resources), HttpStatus.OK);
  }

  @Override
  // CSOFF: LineLength
  public ResponseEntity<SubAccountResponse> updateSubEntity(
      String id, String subResourceId, SubAccountRequest request)
      // CSON: LineLength
      throws ResourceNotFoundException, RequestValidationException {

    log.info("id->{}", id);
    Optional<SubAccount> found = manager.updateById(id, subResourceId, mapper.toModel(request));
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
  public ResponseEntity<SubAccountResponse> deleteSubEntity(String id, String subResourceId)
      // CSON: LineLength
      throws ResourceNotFoundException {

    log.info("id->{}", id);
    Optional<SubAccount> found = manager.deleteById(id, subResourceId);
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
