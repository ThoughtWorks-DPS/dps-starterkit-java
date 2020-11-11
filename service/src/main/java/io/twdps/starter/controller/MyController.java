package io.twdps.starter.controller;

import io.twdps.starter.api.requests.AddEntityRequest;
import io.twdps.starter.api.resources.EntityResource;
import io.twdps.starter.api.responses.AddEntityResponse;
import io.twdps.starter.api.responses.LookupEntityResponse;
import io.twdps.starter.api.responses.ResponseDataInArray;
import io.twdps.starter.service.MyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
public class MyController implements EntityResource {

    private final MyService myService;

    public MyController(MyService myService) {

        this.myService = myService;
    }

    @Override
    public ResponseDataInArray<LookupEntityResponse> findEntityByUserName(
            @PathVariable(value = "username") String username) {
        List<LookupEntityResponse> responseArrayList = new ArrayList<LookupEntityResponse>();
        return new ResponseDataInArray<>(responseArrayList);
    }

    @Override
    public AddEntityResponse addEntity(AddEntityRequest addEntityRequest) {

        String userName = addEntityRequest.getUserName();
        log.info("username->{}", userName);

        String response = myService.hello(userName);
        return new AddEntityResponse(response);
    }
}
