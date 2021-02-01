package io.twdps.starter.service.provider.account;

import io.twdps.starter.persistence.model.AccountEntity;
import io.twdps.starter.persistence.model.AccountEntityRepository;
import io.twdps.starter.service.provider.account.mapper.AccountEntityMapper;
import io.twdps.starter.service.spi.account.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

  private AccountServiceImpl manager;

  @Mock private AccountEntityRepository repository;
  @Mock private AccountEntityMapper mapper;

  private final String username = "jsmith";
  private final String bogusName = "bogus";
  private final String firstName = "Joe";
  private final String lastName = "Smith";
  private final String identifier = "12345";

  private Account account;
  private Account output;
  private AccountEntity entity;
  private AccountEntity added;
  private Optional<Account> emptyAccount = Optional.empty();
  private Optional<AccountEntity> emptyEntity = Optional.empty();
  private Optional<AccountEntity> optionalEntity;
  private Optional<AccountEntity> optionalAdded;
  private Optional<Account> optionalOutput;
  private List<AccountEntity> entityList;
  private List<Account> outputList;
  private List<AccountEntity> emptyEntityList = Arrays.asList();
  private List<Account> emptyOutputList = Arrays.asList();

  /** setup data for each test. */
  @BeforeEach
  public void setup() {

    manager = new AccountServiceImpl(repository, mapper);

    // use the real mapper to generate consistent objects to use in mapper stubs
    AccountEntityMapper real = Mappers.getMapper(AccountEntityMapper.class);

    account = Account.builder().userName(username).firstName(firstName).lastName(lastName).build();
    entity = real.toEntity(account);
    added =
        new AccountEntity(
            identifier, entity.getUserName(), entity.getFirstName(), entity.getLastName());
    output = real.toModel(added);
    optionalEntity = Optional.of(entity);
    optionalAdded = Optional.of(added);
    optionalOutput = Optional.of(output);
    entityList = Arrays.asList(added, added);
    outputList = Arrays.asList(output, output);
  }

  private void createMapperStubs() {
    Mockito.when(mapper.toEntity(account)).thenReturn(entity);
    Mockito.when(mapper.toModel(added)).thenReturn(output);
  }

  private void createOptionalMapperStubs() {
    Mockito.when(mapper.toModel(optionalAdded)).thenReturn(optionalOutput);
  }

  private void createEmptyMapperStubs() {
    Mockito.when(mapper.toModel(emptyEntity)).thenReturn(emptyAccount);
  }

  private void createListMapperStubs() {
    Mockito.when(mapper.toModelList(entityList)).thenReturn(outputList);
  }

  private void createEmptyListMapperStubs() {
    Mockito.when(mapper.toModelList(emptyEntityList)).thenReturn(emptyOutputList);
  }

  @Test
  public void findByAccountIdFailTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findById(Mockito.any())).thenReturn(emptyEntity);

    Optional<Account> result = manager.findById("bogus");
    Assertions.assertThat(!result.isPresent()).isTrue();
  }

  @Test
  public void addAccountTest() {

    createMapperStubs();
    Mockito.when(repository.save(entity)).thenReturn(added);

    Account response = manager.add(account);

    Assertions.assertThat(response.getFirstName()).isEqualTo(account.getFirstName());
    Assertions.assertThat(response.getId()).isEqualTo(added.getId());
  }

  @Test
  public void findByUserNameTest() {

    createOptionalMapperStubs();
    Mockito.when(repository.findByUserName(username)).thenReturn(optionalAdded);

    Optional<Account> response = manager.findByUserName(username);

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(added.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(added.getId());
  }

  @Test
  public void findByUserNameFailedTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findByUserName(bogusName)).thenReturn(emptyEntity);

    Optional<Account> response = manager.findByUserName(bogusName);

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

  @Test
  public void findByLastNameTest() {

    createListMapperStubs();
    Mockito.when(repository.findByLastName(username)).thenReturn(entityList);

    List<Account> response = manager.findByLastName(username);

    Assertions.assertThat(response.isEmpty()).isFalse();
    Assertions.assertThat(response.get(0).getFirstName()).isEqualTo(added.getFirstName());
    Assertions.assertThat(response.get(0).getId()).isEqualTo(added.getId());
  }

  @Test
  public void findByLastNameFailedTest() {

    createEmptyListMapperStubs();
    Mockito.when(repository.findByLastName(bogusName)).thenReturn(Arrays.asList());

    List<Account> response = manager.findByLastName(bogusName);

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

  @Test
  public void findByIdTest() {

    createOptionalMapperStubs();
    Mockito.when(repository.findById(identifier)).thenReturn(optionalAdded);

    Optional<Account> response = manager.findById(identifier);

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(added.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(added.getId());
  }

  @Test
  public void findByIdFailedTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findById(bogusName)).thenReturn(emptyEntity);

    Optional<Account> response = manager.findById(bogusName);

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

  @Test
  public void findAllTest() {

    createListMapperStubs();
    Mockito.when(repository.findAll()).thenReturn(entityList);

    List<Account> response = manager.findAll();

    Assertions.assertThat(response.size()).isEqualTo(2);
  }

  @Test
  public void findAllEmptyTest() {

    createEmptyListMapperStubs();
    Mockito.when(repository.findAll()).thenReturn(emptyEntityList);

    List<Account> response = manager.findAll();

    Assertions.assertThat(response.size()).isEqualTo(0);
  }

  @Test
  public void updateTest() {

    createOptionalMapperStubs();
    Mockito.when(mapper.updateMetadata(account, added)).thenReturn(added);
    Mockito.when(repository.findById(identifier)).thenReturn(optionalAdded);
    Mockito.when(repository.save(added)).thenReturn(added);

    Optional<Account> response = manager.updateById(identifier, account);

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(account.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(identifier);
  }

  @Test
  public void updateFailedTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findById(identifier)).thenReturn(emptyEntity);

    Optional<Account> response = manager.updateById(identifier, account);

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

  @Test
  public void deleteTest() {

    createOptionalMapperStubs();
    Mockito.when(repository.findById(identifier)).thenReturn(optionalAdded);

    Optional<Account> response = manager.deleteById(identifier);

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(added.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(added.getId());
  }

  @Test
  public void deleteFailedTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findById(bogusName)).thenReturn(emptyEntity);

    Optional<Account> response = manager.deleteById(bogusName);

    Assertions.assertThat(response.isEmpty()).isTrue();
  }
}
