package io.twdps.starter.controller.account.mapper;

import io.twdps.starter.api.account.requests.AccountRequest;
import io.twdps.starter.api.account.responses.AccountResponse;
import io.twdps.starter.api.account.responses.AddAccountResponse;
import io.twdps.starter.spi.account.model.Account;
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
    AccountRequest account = createAddAccountRequest();

    Account response = mapper.toModel(account);

    verifyAccount(response);
  }

  @Test
  public void mapperAccountResponseTest() {
    Account account = createAccount(identifier);

    AccountResponse response = mapper.toAccountResponse(account);

    verifyAccountResponse(response);
  }

  @Test
  public void mapperAddAccountResponseTest() {
    Account account = createAccount(identifier);

    AddAccountResponse response = mapper.toAddAccountResponse(account);

    verifyAddAccountResponse(response);
  }

  @Test
  public void mapperOptionalTest() {
    Optional<Account> account = Optional.of(createAccount(identifier));

    AccountResponse response = mapper.toAccountResponse(account);

    assertThat(response).isNotNull();
    verifyAccountResponse(response);
  }

  @Test
  public void mapperOptionalNullTest() {
    Optional<Account> account = Optional.ofNullable(null);

    AccountResponse response = mapper.toAccountResponse(account);

    assertThat(response).isNull();
  }

  @Test
  public void mapperOptionalEmptyTest() {
    Optional<Account> account = Optional.empty();

    AccountResponse response = mapper.toAccountResponse(account);

    assertThat(response).isNull();
  }

  @Test
  public void mapperEntityListTest() {
    List<Account> accounts = Arrays.asList(createAccount(identifier), createAccount(identifier));

    List<AccountResponse> response = mapper.toResponseList(accounts);

    assertThat(response.size()).isEqualTo(2);
    verifyAccountResponse(response.get(0));
    verifyAccountResponse(response.get(1));
  }

  /**
   * convenience function to create account object.
   *
   * @param id whether to create with identifier (null if not)
   * @return Account object
   */
  private Account createAccount(String id) {
    return new Account(id, username, firstName, lastName);
  }

  /**
   * convenience function to create account request object.
   *
   * @return AddAccountRequest object
   */
  private AccountRequest createAddAccountRequest() {
    return new AccountRequest(username, firstName, lastName);
  }

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  protected void verifyAccount(Account response) {
    assertThat(response.getUserName().equals(username));
    assertThat(response.getFirstName().equals(firstName));
    assertThat(response.getLastName().equals(lastName));
    assertThat(response.getId()).isNotEqualTo(identifier);
  }

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  protected void verifyAddAccountResponse(AddAccountResponse response) {
    assertThat(response.getResponse().equals(username));
    assertThat(response.getId()).isEqualTo(identifier);
  }

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  private void verifyAccountResponse(AccountResponse response) {
    assertThat(response.getUserName().equals(username));
    assertThat(response.getFullName().equals(fullName));
    assertThat(response.getId()).isEqualTo(identifier);
  }
}
