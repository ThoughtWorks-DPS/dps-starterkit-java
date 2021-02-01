package io.twdps.starter.api.account.resources;

import io.twdps.starter.api.account.requests.AccountRequest;
import io.twdps.starter.api.account.responses.AccountResponse;
import io.twdps.starter.api.account.responses.AddAccountResponse;
import io.twdps.starter.api.responses.ArrayResponse;
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

@RequestMapping(value = "/starter", produces = "application/json")
public interface AccountResource {

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  ResponseEntity<AddAccountResponse> addEntity(@RequestBody AccountRequest addAccountRequest);

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<AccountResponse> findEntityById(@PathVariable(value = "id") String id);

  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<ArrayResponse<AccountResponse>> findEntities();

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<AccountResponse> updateEntityById(@PathVariable(value = "id") String id,
                                                         @RequestBody AccountRequest request);

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<AccountResponse> deleteEntityById(@PathVariable(value = "id") String id);

  @GetMapping("/lastname/{lastname}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<ArrayResponse<AccountResponse>> findEntityByLastName(
      @PathVariable(value = "lastname") String lastName);

  @GetMapping("/username/{username}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<AccountResponse> findEntityByUsername(
      @PathVariable(value = "userName") String userName);
}
