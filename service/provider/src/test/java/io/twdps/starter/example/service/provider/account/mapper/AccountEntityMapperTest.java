package io.twdps.starter.example.service.provider.account.mapper;

import io.twdps.starter.example.persistence.model.AccountEntity;
import io.twdps.starter.example.service.spi.account.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountEntityMapperTest {

  private AccountEntityMapper mapper;

  private final String username = "jsmith";
  private final String pii = "123-45-6789";
  private final String firstName = "Joe";
  private final String lastName = "Smith";
  private final String identifier = "12345";

  @BeforeEach
  public void setup() {
    mapper = Mappers.getMapper(AccountEntityMapper.class);
  }

  @Test
  public void mapperNewAccountTest() {
    Account resource = createAccount(null);

    AccountEntity response = mapper.toEntity(resource);

    verifyAccountEntity(response, false);
  }

  @Test
  public void mapperAccountTest() {
    Account resource = createAccount(identifier);

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
   * convenience function to create resource entity object.
   *
   * @return AccountEntity object
   */
  private AccountEntity createAccountEntity() {
    return new AccountEntity(identifier, username, pii, firstName, lastName);
  }

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  protected void verifyAccount(Account response) {
    assertThat(response.getUserName().equals(username));
    assertThat(response.getPii().equals(pii));
    assertThat(response.getFirstName().equals(firstName));
    assertThat(response.getLastName().equals(lastName));
    assertThat(response.getId()).isEqualTo(identifier);
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
  private void verifyAccountEntity(AccountEntity response, boolean hasId) {
    assertThat(response.getUserName().equals(username));
    assertThat(response.getPii().equals(pii));
    assertThat(response.getFirstName().equals(firstName));
    assertThat(response.getLastName().equals(lastName));
    if (hasId) {
      assertThat(response.getId()).isEqualTo(identifier);
    } else {
      assertThat(response.getId()).isNotEqualTo(identifier);
    }
  }
}
