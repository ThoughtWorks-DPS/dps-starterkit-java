package io.twdps.starter.controller;

import io.twdps.starter.api.requests.AddEntityRequest;
import io.twdps.starter.api.resources.EntityResource;
import io.twdps.starter.api.responses.AddAccountEntityResponse;
import io.twdps.starter.api.responses.LookupEntityResponse;
import io.twdps.starter.api.responses.ResponseDataInArray;
import io.twdps.starter.persistence.mapper.AccountEntityCreateMapper;
import io.twdps.starter.persistence.mapper.AccountEntityMapper;
import io.twdps.starter.persistence.mapper.LookupEntityResponseMapper;
import io.twdps.starter.persistence.model.AccountEntity;
import io.twdps.starter.spi.AccountManager;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class AccountEntityController implements EntityResource {

  private final AccountManager accountManager;
  private final AccountEntityMapper accountEntityMapper;
  private final LookupEntityResponseMapper lookupEntityResponseMapper;
  private final AccountEntityCreateMapper accountEntityCreateMapper;

  public AccountEntityController(AccountManager accountManager,
      AccountEntityMapper accountEntityMapper,
      LookupEntityResponseMapper lookupEntityResponseMapper,
      AccountEntityCreateMapper accountEntityCreateMapper) {

    this.accountManager = accountManager;
    this.accountEntityMapper = accountEntityMapper;
    this.lookupEntityResponseMapper = lookupEntityResponseMapper;
    this.accountEntityCreateMapper = accountEntityCreateMapper;
  }

  @Override
  public ResponseEntity<ResponseDataInArray<LookupEntityResponse>> findEntityByUserName(
      String lastName) {
    List<AccountEntity> accountEntities = accountManager.findByLastName(lastName);

    if (accountEntities.size() > 0) {
      return new ResponseEntity<>(
          new ResponseDataInArray<LookupEntityResponse>(
              lookupEntityResponseMapper.toLookupEntityResponseList(accountEntities)),
          HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<AddAccountEntityResponse> addEntity(AddEntityRequest addEntityRequest) {

    log.info("username->{}", addEntityRequest.getUserName());
    AccountEntity accountEntity = accountEntityMapper.toAccountEntity(addEntityRequest);
    AccountEntity saved = accountManager.addEntity(accountEntity);
    AddAccountEntityResponse response = accountEntityCreateMapper
        .toAddAccountEntityResponse(accountEntity);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
}
