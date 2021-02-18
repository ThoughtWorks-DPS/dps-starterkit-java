package io.twdps.starter.example.api.account.resources;

import io.twdps.starter.boot.exception.RequestValidationException;
import io.twdps.starter.boot.exception.ResourceNotFoundException;
import io.twdps.starter.example.api.account.requests.AccountRequest;
import io.twdps.starter.example.api.account.responses.AccountResponse;
import io.twdps.starter.example.api.responses.ArrayResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping(value = "/v1/example/accounts", produces = "application/json")
public interface AccountResource {

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  ResponseEntity<AccountResponse> addEntity(@RequestBody AccountRequest request)
      throws RequestValidationException;

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<AccountResponse> findEntityById(@PathVariable(value = "id") String id)
      throws ResourceNotFoundException;

  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<ArrayResponse<AccountResponse>> findEntities();

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<AccountResponse> updateEntityById(@PathVariable(value = "id") String id,
                                                         @RequestBody AccountRequest request)
      throws ResourceNotFoundException, RequestValidationException;

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<AccountResponse> deleteEntityById(@PathVariable(value = "id") String id)
      throws ResourceNotFoundException;

}
