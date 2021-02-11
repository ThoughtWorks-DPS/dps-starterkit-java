package io.twdps.starter.example.controller.account.mapper;

import io.twdps.starter.example.api.account.requests.AccountRequest;
import io.twdps.starter.example.api.account.responses.AccountResponse;
import io.twdps.starter.example.service.spi.account.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountRequestMapperTest {

  private AccountRequestMapper mapper;

  private final String username = "jsmith";
  private final String pii = "123-45-6789";
  private final String firstName = "Joe";
  private final String lastName = "Smith";
  private final String identifier = "12345";
  private final String fullName = "Joe Smith";

  @BeforeEach
  public void setup() {
    mapper = Mappers.getMapper(AccountRequestMapper.class);
  }

  @Test
  public void mapperNewAccountTest() {
    AccountRequest resource = createAccountRequest();

    Account response = mapper.toModel(resource);

    verifyAccount(response);
  }

  @Test
  public void mapperAccountResponseTest() {
    Account resource = createAccount(identifier);

    AccountResponse response = mapper.toAccountResponse(resource);

    verifyAccountResponse(response);
  }

  @Test
  public void mapperOptionalTest() {
    Optional<Account> resource = Optional.of(createAccount(identifier));

    AccountResponse response = mapper.toAccountResponse(resource);

    assertThat(response).isNotNull();
    verifyAccountResponse(response);
  }

  @Test
  public void mapperOptionalNullTest() {
    Optional<Account> resource = Optional.ofNullable(null);

    AccountResponse response = mapper.toAccountResponse(resource);

    assertThat(response).isNull();
  }

  @Test
  public void mapperOptionalEmptyTest() {
    Optional<Account> resource = Optional.empty();

    AccountResponse response = mapper.toAccountResponse(resource);

    assertThat(response).isNull();
  }

  @Test
  public void mapperEntityListTest() {
    List<Account> resources = Arrays.asList(createAccount(identifier), createAccount(identifier));

    List<AccountResponse> response = mapper.toAccountResponseList(resources);

    assertThat(response.size()).isEqualTo(2);
    verifyAccountResponse(response.get(0));
    verifyAccountResponse(response.get(1));
  }

  /**
   * convenience function to create resource object.
   *
   * @param id whether to create with identifier (null if not)
   * @return Account object
   */
  private Account createAccount(String id) {
    return new Account(id, username, pii, firstName, lastName);
  }

  /**
   * convenience function to create resource request object.
   *
   * @return AccountRequest object
   */
  private AccountRequest createAccountRequest() {
    return new AccountRequest(username, pii, firstName, lastName);
  }

  /**
   * helper function to validate standard values.
   *
   * @param resource the object to validate
   */
  protected void verifyAccount(Account resource) {
    assertThat(resource.getUserName().equals(username));
    assertThat(resource.getPii().equals(pii));
    assertThat(resource.getFirstName().equals(firstName));
    assertThat(resource.getLastName().equals(lastName));
    assertThat(resource.getId()).isNotEqualTo(identifier);
  }

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  private void verifyAccountResponse(AccountResponse response) {
    assertThat(response.getUserName().equals(username));
    assertThat(response.getPii().equals(pii));
    assertThat(response.getFullName().equals(fullName));
    assertThat(response.getId()).isEqualTo(identifier);
  }
}