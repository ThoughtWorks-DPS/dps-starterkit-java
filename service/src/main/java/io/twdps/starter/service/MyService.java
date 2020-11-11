package io.twdps.starter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyService {

    @Autowired
    private AccountEntityRepository accountEntityRepository;

    public String hello(String name) {
        return "Hello " + name;
    }
}
