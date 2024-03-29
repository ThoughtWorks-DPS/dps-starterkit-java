package io.twdps.starter.example.controller.account.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import io.twdps.starter.boot.test.data.provider.NamedDataFactory;
import io.twdps.starter.example.api.account.requests.AccountRequest;
import io.twdps.starter.example.api.account.responses.AccountResponse;
import io.twdps.starter.example.api.responses.PagedResponse;
import io.twdps.starter.example.data.account.model.AccountData;
import io.twdps.starter.example.data.account.provider.AccountDataFactory;
import io.twdps.starter.example.data.account.provider.AccountTestData;
import io.twdps.starter.example.service.spi.account.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AccountRequestMapperTest {

  private AccountRequestMapper mapper;

  private AccountTestData resourceTestDataLoader = new AccountTestData();
  private AccountDataFactory resourceTestData = new AccountDataFactory(resourceTestDataLoader);

  private AccountData reference;

  /** Setup mapper and test data factory before each test. */
  @BeforeEach
  public void setup() {
    mapper = Mappers.getMapper(AccountRequestMapper.class);

    reference = resourceTestData.createBySpec(NamedDataFactory.DEFAULT_SPEC);
  }

  @Test
  public void mapperNewAccountTest() {
    AccountRequest resource = createAccountRequest();

    Account response = mapper.toModel(resource);

    verifyAccount(response);
  }

  @Test
  public void mapperAccountResponseTest() {
    Account resource = createAccount(reference.getId());

    AccountResponse response = mapper.toAccountResponse(resource);

    verifyAccountResponse(response);
  }

  @Test
  public void mapperOptionalTest() {
    Optional<Account> resource = Optional.of(createAccount(reference.getId()));

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
    List<Account> resources =
        Arrays.asList(createAccount(reference.getId()), createAccount(reference.getId()));

    List<AccountResponse> response = mapper.toAccountResponseList(resources);

    assertThat(response.size()).isEqualTo(2);
    verifyAccountResponse(response.get(0));
    verifyAccountResponse(response.get(1));
  }

  @Test
  public void mapperEntityPageTest() {
    Pageable pageable = PageRequest.of(0, 1);
    Page<Account> resources =
        new PageImpl<>(Arrays.asList(createAccount(reference.getId())), pageable, 100);
    PagedResponse<AccountResponse> response = mapper.toAccountResponsePage(resources);

    assertThat(response.getItems().size()).isEqualTo(1);
    assertThat(response.getTotalItems()).isEqualTo(100);
    assertThat(response.getPageNumber()).isEqualTo(0);
    assertThat(response.getPageSize()).isEqualTo(1);
    assertThat(response.getTotalPages()).isEqualTo(100);
    verifyAccountResponse(response.getItems().get(0));
  }

  /**
   * convenience function to create resource object.
   *
   * @param id whether to create with identifier (null if not)
   * @return Account object
   */
  private Account createAccount(String id) {
    return new Account(
        id,
        reference.getUserName(),
        reference.getPii(),
        reference.getFirstName(),
        reference.getLastName()
        // TODO: Additional Account data elements
        );
  }

  /**
   * convenience function to create resource request object.
   *
   * @return AccountRequest object
   */
  private AccountRequest createAccountRequest() {
    return new AccountRequest(
        reference.getUserName(),
        reference.getPii(),
        reference.getFirstName(),
        reference.getLastName()
        // TODO: Additional AccountRequest data elements
        );
  }

  /**
   * helper function to validate standard values.
   *
   * @param resource the object to validate
   * @param reference what to compare with
   */
  protected void verifyAccount(Account resource, AccountData reference) {
    assertThat(resource.getUserName()).isEqualTo(reference.getUserName());
    assertThat(resource.getPii()).isEqualTo(reference.getPii());
    assertThat(resource.getFirstName()).isEqualTo(reference.getFirstName());
    assertThat(resource.getLastName()).isEqualTo(reference.getLastName());
    // TODO: Add assertions for additional Account fields
    assertThat(resource.getId()).isNotEqualTo(reference.getId());
  }

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   * @param reference what to compare with
   */
  private void verifyAccountResponse(AccountResponse response, AccountData reference) {
    assertThat(response.getUserName()).isEqualTo(reference.getUserName());
    assertThat(response.getPii()).isEqualTo(reference.getPii());
    assertThat(response.getFullName()).isEqualTo(reference.getFullName());
    // TODO: Add assertions for additional AccountResponse fields
    assertThat(response.getId()).isEqualTo(reference.getId());
  }

  /**
   * helper function to validate standard values.
   *
   * @param resource the object to validate
   */
  protected void verifyAccount(Account resource) {
    verifyAccount(resource, reference);
  }

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  private void verifyAccountResponse(AccountResponse response) {
    verifyAccountResponse(response, reference);
  }
}
