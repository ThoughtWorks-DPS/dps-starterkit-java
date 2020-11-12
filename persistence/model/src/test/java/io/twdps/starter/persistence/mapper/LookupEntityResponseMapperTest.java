package io.twdps.starter.persistence.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import io.twdps.starter.api.responses.LookupEntityResponse;
import io.twdps.starter.persistence.model.AccountEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LookupEntityResponseMapperTest {

  private LookupEntityResponseMapper lookupEntityResponseMapper;

  @BeforeEach
  public void setup() {
    lookupEntityResponseMapper = new LookupEntityResponseMapperImpl();
  }

  @Test
  public void mapperTest() {
    AccountEntity accountEntity = new AccountEntity("jsmith", "Joe", "Smith");
    LookupEntityResponse response = lookupEntityResponseMapper
        .toLookupEntityResponse(accountEntity);
    assertThat(response.getFullName().equals("Joe Smith"));
  }
}
