package io.twdps.starter.example.controller.account.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import io.twdps.starter.boot.test.data.spi.DataFactory;
import io.twdps.starter.example.api.account.requests.AccountRequest;
import io.twdps.starter.example.api.account.requests.SubAccountRequest;
import io.twdps.starter.example.api.account.responses.AccountResponse;
import io.twdps.starter.example.api.account.responses.SubAccountResponse;
import io.twdps.starter.example.api.responses.PagedResponse;
import io.twdps.starter.example.data.account.model.AccountData;
import io.twdps.starter.example.data.account.provider.AccountDataFactory;
import io.twdps.starter.example.data.account.provider.AccountTestData;
import io.twdps.starter.example.data.subaccount.model.SubAccountData;
import io.twdps.starter.example.data.subaccount.provider.SubAccountDataFactory;
import io.twdps.starter.example.data.subaccount.provider.SubAccountTestData;
import io.twdps.starter.example.service.spi.account.model.Account;
import io.twdps.starter.example.service.spi.account.model.SubAccount;
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
  private SubAccountTestData subResourceTestDataLoader = new SubAccountTestData();
  private SubAccountDataFactory subResourceTestData =
      new SubAccountDataFactory(subResourceTestDataLoader);

  private AccountData reference;
  private SubAccountData subReference;

  /** Setup mapper and test data factory before each test. */
  @BeforeEach
  public void setup() {
    mapper = Mappers.getMapper(AccountRequestMapper.class);

    reference = resourceTestData.getNamedData(DataFactory.DEFAULT_NAME);
    subReference = subResourceTestData.getNamedData(DataFactory.DEFAULT_NAME);
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

  @Test
  public void mapperNewSubAccountTest() {
    SubAccountRequest resource = createSubAccountRequest();

    SubAccount response = mapper.toModel(resource);

    verifySubAccount(response);
  }

  @Test
  public void mapperSubAccountResponseTest() {
    SubAccount resource = createSubAccount(reference.getId());

    SubAccountResponse response = mapper.toSubAccountResponse(resource);

    verifySubAccountResponse(response);
  }

  @Test
  public void mapperOptionalSubAccountTest() {
    Optional<SubAccount> resource = Optional.of(createSubAccount(reference.getId()));

    SubAccountResponse response = mapper.toSubAccountResponse(resource);

    assertThat(response).isNotNull();
    verifySubAccountResponse(response);
  }

  @Test
  public void mapperOptionalSubAccountNullTest() {
    Optional<SubAccount> resource = Optional.ofNullable(null);

    SubAccountResponse response = mapper.toSubAccountResponse(resource);

    assertThat(response).isNull();
  }

  @Test
  public void mapperOptionalSubAccountEmptyTest() {
    Optional<SubAccount> resource = Optional.empty();

    SubAccountResponse response = mapper.toSubAccountResponse(resource);

    assertThat(response).isNull();
  }

  @Test
  public void mapperSubEntityListTest() {
    List<SubAccount> resources =
        Arrays.asList(createSubAccount(reference.getId()), createSubAccount(reference.getId()));

    List<SubAccountResponse> response = mapper.toSubAccountResponseList(resources);

    assertThat(response.size()).isEqualTo(2);
    verifySubAccountResponse(response.get(0));
    verifySubAccountResponse(response.get(1));
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
        reference.getLastName());
  }

  /**
   * convenience function to create subresource object.
   *
   * @param id whether to create with identifier (null if not)
   * @return SubAccount object
   */
  private SubAccount createSubAccount(String id) {
    return new SubAccount(
        id, subReference.getUserName(), subReference.getFirstName(), subReference.getLastName());
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
        reference.getLastName());
  }

  /**
   * convenience function to create subresource request object.
   *
   * @return SubAccountRequest object
   */
  private SubAccountRequest createSubAccountRequest() {
    return new SubAccountRequest(
        subReference.getUserName(), subReference.getFirstName(), subReference.getLastName());
  }

  /**
   * helper function to validate standard values.
   *
   * @param resource the object to validate
   */
  protected void verifyAccount(Account resource) {
    assertThat(resource.getUserName().equals(reference.getUserName()));
    assertThat(resource.getPii().equals(reference.getPii()));
    assertThat(resource.getFirstName().equals(reference.getFirstName()));
    assertThat(resource.getLastName().equals(reference.getLastName()));
    assertThat(resource.getId()).isNotEqualTo(reference.getId());
  }

  /**
   * helper function to validate standard values.
   *
   * @param resource the object to validate
   */
  protected void verifySubAccount(SubAccount resource) {
    assertThat(resource.getUserName().equals(subReference.getUserName()));
    assertThat(resource.getFirstName().equals(subReference.getFirstName()));
    assertThat(resource.getLastName().equals(subReference.getLastName()));
    assertThat(resource.getId()).isNotEqualTo(subReference.getId());
  }

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  private void verifyAccountResponse(AccountResponse response) {
    assertThat(response.getUserName().equals(reference.getUserName()));
    assertThat(response.getPii().equals(reference.getPii()));
    assertThat(response.getFullName().equals(reference.getFullName()));
    assertThat(response.getId()).isEqualTo(reference.getId());
  }

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  protected void verifySubAccountResponse(SubAccountResponse response) {
    assertThat(response.getId()).isEqualTo(subReference.getId());
  }
}
