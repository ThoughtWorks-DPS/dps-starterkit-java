package io.twdps.starter.example.service.provider.account.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import io.twdps.starter.boot.test.data.provider.NamedDataFactory;
import io.twdps.starter.example.data.account.model.AccountData;
import io.twdps.starter.example.data.account.provider.AccountDataFactory;
import io.twdps.starter.example.data.account.provider.AccountTestData;
import io.twdps.starter.example.persistence.model.AccountEntity;
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

public class AccountEntityMapperTest {

  private AccountEntityMapper mapper;

  private AccountTestData resourceTestDataLoader = new AccountTestData();
  private AccountDataFactory resourceTestData = new AccountDataFactory(resourceTestDataLoader);

  private AccountData reference;

  /** Setup mapper and test data factory before each test. */
  @BeforeEach
  public void setup() {
    mapper = Mappers.getMapper(AccountEntityMapper.class);

    reference = resourceTestData.createBySpec(NamedDataFactory.DEFAULT_SPEC);
  }

  @Test
  public void mapperNewAccountTest() {
    Account resource = createAccount(null);

    AccountEntity response = mapper.toEntity(resource);

    verifyAccountEntity(response, false);
  }

  @Test
  public void mapperAccountTest() {
    Account resource = createAccount(reference.getId());

    AccountEntity response = mapper.toEntity(resource);

    verifyAccountEntity(response);
  }

  @Test
  public void mapperEntityTest() {
    AccountEntity entity = createAccountEntity();

    Account response = mapper.toModel(entity);

    verifyAccount(response);
  }

  @Test
  public void mapperOptionalEntityTest() {
    Optional<AccountEntity> entity = Optional.of(createAccountEntity());

    Optional<Account> response = mapper.toModel(entity);

    assertThat(response.isPresent());
    verifyAccount(response.get());
  }

  @Test
  public void mapperOptionalTest() {
    Optional<Account> resource = Optional.of(createAccount(null));

    Optional<AccountEntity> response = mapper.toEntity(resource);

    assertThat(response.isPresent());
    verifyAccountEntity(response.get(), false);
  }

  @Test
  public void mapperOptionalNullTest() {
    Optional<Account> resource = Optional.ofNullable(null);

    Optional<AccountEntity> response = mapper.toEntity(resource);

    assertThat(response.isEmpty());
  }

  @Test
  public void mapperOptionalEmptyTest() {
    Optional<Account> resource = Optional.empty();

    Optional<AccountEntity> response = mapper.toEntity(resource);

    assertThat(response.isEmpty());
  }

  @Test
  public void mapperEntityListTest() {
    List<AccountEntity> entities = Arrays.asList(createAccountEntity(), createAccountEntity());

    List<Account> response = mapper.toModelList(entities);

    assertThat(response.size()).isEqualTo(2);
    verifyAccount(response.get(0));
    verifyAccount(response.get(1));
  }

  @Test
  public void mapperEntityPageTest() {
    Pageable pageable = PageRequest.of(0, 3);
    Page<AccountEntity> entities =
        new PageImpl<>(
            Arrays.asList(createAccountEntity(), createAccountEntity(), createAccountEntity()),
            pageable,
            100);

    Page<Account> response = mapper.toModelPage(entities);

    assertThat(response.getContent().size()).isEqualTo(3);
    assertThat(response.getTotalElements()).isEqualTo(100);
    assertThat(response.getNumber()).isEqualTo(0);
    assertThat(response.getNumberOfElements()).isEqualTo(3);

    verifyAccount(response.toList().get(0));
    verifyAccount(response.toList().get(1));
    verifyAccount(response.toList().get(2));
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
   * convenience function to create resource entity object.
   *
   * @return AccountEntity object
   */
  private AccountEntity createAccountEntity() {
    return new AccountEntity(
        reference.getId(),
        reference.getUserName(),
        reference.getPii(),
        reference.getFirstName(),
        reference.getLastName());
  }

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  protected void verifyAccount(Account response) {
    assertThat(response.getUserName()).isEqualTo(reference.getUserName());
    assertThat(response.getPii()).isEqualTo(reference.getPii());
    assertThat(response.getFirstName()).isEqualTo(reference.getFirstName());
    assertThat(response.getLastName()).isEqualTo(reference.getLastName());
    assertThat(response.getId()).isEqualTo(reference.getId());
  }

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  private void verifyAccountEntity(AccountEntity response) {
    verifyAccountEntity(response, true);
  }

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  // CSOFF: LineLength
  private void verifyAccountEntity(AccountEntity response, boolean hasId) {
    // CSON: LineLength
    assertThat(response.getUserName()).isEqualTo(reference.getUserName());
    assertThat(response.getPii()).isEqualTo(reference.getPii());
    assertThat(response.getFirstName()).isEqualTo(reference.getFirstName());
    assertThat(response.getLastName()).isEqualTo(reference.getLastName());
    if (hasId) {
      assertThat(response.getId()).isEqualTo(reference.getId());
    } else {
      assertThat(response.getId()).isNotEqualTo(reference.getId());
    }
  }
}
