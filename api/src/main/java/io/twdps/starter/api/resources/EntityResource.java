package io.twdps.starter.api.resources;

import io.twdps.starter.api.requests.AddEntityRequest;
import io.twdps.starter.api.responses.AddAccountEntityResponse;
import io.twdps.starter.api.responses.LookupEntityResponse;
import io.twdps.starter.api.responses.ResponseDataInArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping(value = "/starter", produces = "application/json")
public interface EntityResource {

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  ResponseEntity<AddAccountEntityResponse> addEntity(
      @RequestBody AddEntityRequest addEntityRequest);

  @GetMapping("/lastname/{lastname}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<ResponseDataInArray<LookupEntityResponse>> findEntityByUserName(
      @PathVariable(value = "lastname") String lastName);
}