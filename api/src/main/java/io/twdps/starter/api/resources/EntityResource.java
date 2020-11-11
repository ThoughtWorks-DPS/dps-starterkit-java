package io.twdps.starter.api.resources;

import io.twdps.starter.api.responses.AddEntityResponse;
import io.twdps.starter.api.requests.AddEntityRequest;
import io.twdps.starter.api.responses.LookupEntityResponse;
import io.twdps.starter.api.responses.ResponseDataInArray;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/starter")
public interface EntityResource {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    AddEntityResponse addEntity(@RequestBody AddEntityRequest addEntityRequest);

    @GetMapping("/username/{username}")
    ResponseDataInArray<LookupEntityResponse> findEntityByUserName(
            @PathVariable(value = "username") String username);
}