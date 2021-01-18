package io.twdps.starter.service.provider.account.mapper;

import io.twdps.starter.persistence.model.AccountEntity;
import io.twdps.starter.service.spi.account.model.Account;
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
  private final String firstName = "Joe";
  private final String lastName = "Smith";
  private final String identifier = "12345";

  @BeforeEach
  public void setup() {
    mapper = Mappers.getMapper(AccountEntityMapper.class);
  }

  @Test
  public void mapperNewAccountTest() {
    Account account = createAccount(null);

    AccountEntity response = mapper.toEntity(account);

    verifyAccountEntity(response, false);
  }

  @Test
  public void mapperAccountTest() {
    Account account = createAccount(identifier);

    AccountEntity response = mapper.toEntity(account);

    verifyAccountEntity(response);
  }

  @Test
  public void mapperEntityTest() {
    AccountEntity entity = createAccountEntity();

    Account response = mapper.toModel(entity);

    verifyAccount(response);
  }

  @Test
  public void mapperOptionalTest() {
    Optional<Account> account = Optional.of(createAccount(null));

    Optional<AccountEntity> response = mapper.toEntity(account);

    assertThat(response.isPresent());
    verifyAccountEntity(response.get(), false);
  }

  @Test
  public void mapperOptionalNullTest() {
    Optional<Account> account = Optional.ofNullable(null);

    Optional<AccountEntity> response = mapper.toEntity(account);

    assertThat(response.isEmpty());
  }

  @Test
  public void mapperOptionalEmptyTest() {
    Optional<Account> account = Optional.empty();

    Optional<AccountEntity> response = mapper.toEntity(account);

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
   * convenience function to create account object.
   *
   * @param id whether to create with identifier (null if not)
   * @return Account object
   */
  private Account createAccount(String id) {
    return new Account(id, username, firstName, lastName);
  }

  /**
   * convenience function to create account entity object.
   *
   * @return AccountEntity object
   */
  private AccountEntity createAccountEntity() {
    return new AccountEntity(identifier, username, firstName, lastName);
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
    assertThat(response.getFirstName().equals(firstName));
    assertThat(response.getLastName().equals(lastName));
    if (hasId) {
      assertThat(response.getId()).isEqualTo(identifier);
    } else {
      assertThat(response.getId()).isNotEqualTo(identifier);
    }
  }
}
