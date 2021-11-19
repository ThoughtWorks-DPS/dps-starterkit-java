package io.twdps.starter.example.service.provider.account;

import io.twdps.starter.boot.test.data.provider.NamedDataFactory;
import io.twdps.starter.example.data.account.model.AccountData;
import io.twdps.starter.example.data.account.provider.AccountDataFactory;
import io.twdps.starter.example.data.account.provider.AccountTestData;
import io.twdps.starter.example.persistence.model.AccountEntity;
import io.twdps.starter.example.persistence.model.AccountEntityRepository;
import io.twdps.starter.example.service.provider.account.mapper.AccountEntityMapper;
import io.twdps.starter.example.service.spi.account.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

  private AccountServiceImpl manager;

  @Mock private AccountEntityRepository repository;
  @Mock private AccountEntityMapper mapper;

  private AccountTestData resourceTestDataLoader = new AccountTestData();
  private AccountDataFactory resourceTestData = new AccountDataFactory(resourceTestDataLoader);

  private AccountData reference;
  private AccountData bogus;

  private Account resource;
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
  private Page<AccountEntity> entityPage;
  private Page<Account> outputPage;
  private Page<AccountEntity> emptyEntityPage;
  private Page<Account> emptyOutputPage;
  private Pageable pageable = Pageable.unpaged();

  /** setup data for each test. */
  @BeforeEach
  public void setup() {

    manager = new AccountServiceImpl(repository, mapper);

    reference = resourceTestData.createBySpec(NamedDataFactory.DEFAULT_SPEC);
    bogus = resourceTestData.createBySpec("bogus");

    // use the real mapper to generate consistent objects to use in mapper stubs
    AccountEntityMapper real = Mappers.getMapper(AccountEntityMapper.class);

    resource =
        Account.builder()
            .userName(reference.getUserName())
            .pii(reference.getPii())
            .firstName(reference.getFirstName())
            .lastName(reference.getLastName())
            // TODO: Additional Account data elements
            .build();
    entity = real.toEntity(resource);
    added =
        new AccountEntity(
            reference.getId(),
            entity.getUserName(),
            entity.getPii(),
            entity.getFirstName(),
            entity.getLastName()
            // TODO: Additional AccountEntity data elements
            );
    output = real.toModel(added);
    optionalEntity = Optional.of(entity);
    optionalAdded = Optional.of(added);
    optionalOutput = Optional.of(output);
    entityList = Arrays.asList(added, added);
    outputList = Arrays.asList(output, output);
    entityPage = new PageImpl<>(entityList);
    outputPage = new PageImpl<>(outputList);
    emptyEntityPage = new PageImpl<>(emptyEntityList);
    emptyOutputPage = new PageImpl<>(emptyOutputList);
  }

  private void createMapperStubs() {
    Mockito.when(mapper.toEntity(resource)).thenReturn(entity);
    Mockito.when(mapper.toModel(added)).thenReturn(output);
  }

  private void createOptionalMapperStubs() {
    Mockito.when(mapper.toModel(optionalAdded)).thenReturn(optionalOutput);
  }

  private void createEmptyMapperStubs() {
    Mockito.when(mapper.toModel(emptyEntity)).thenReturn(emptyAccount);
  }

  private void createListMapperStubs() {
    Mockito.when(mapper.toModelPage(entityPage)).thenReturn(outputPage);
  }

  private void createEmptyListMapperStubs() {
    Mockito.when(mapper.toModelPage(emptyEntityPage)).thenReturn(emptyOutputPage);
  }

  @Test
  public void findByAccountIdFailTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findById(Mockito.any())).thenReturn(emptyEntity);

    Optional<Account> result = manager.findById(bogus.getId());
    Assertions.assertThat(!result.isPresent()).isTrue();
  }

  @Test
  public void addAccountTest() {

    createMapperStubs();
    Mockito.when(repository.save(entity)).thenReturn(added);

    Account response = manager.add(resource);

    Assertions.assertThat(response.getFirstName()).isEqualTo(resource.getFirstName());
    Assertions.assertThat(response.getId()).isEqualTo(added.getId());
  }

  @Test
  public void findByUserNameTest() {

    createOptionalMapperStubs();
    Mockito.when(repository.findByUserName(reference.getUserName())).thenReturn(optionalAdded);

    Optional<Account> response = manager.findByUserName(reference.getUserName());

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(added.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(added.getId());
  }

  @Test
  public void findByUserNameFailedTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findByUserName(bogus.getUserName())).thenReturn(emptyEntity);

    Optional<Account> response = manager.findByUserName(bogus.getUserName());

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

  @Test
  public void findByLastNameTest() {

    createListMapperStubs();
    Mockito.when(repository.findByLastName(reference.getLastName(), pageable))
        .thenReturn(entityPage);

    Page<Account> response = manager.findByLastName(reference.getLastName(), pageable);

    Assertions.assertThat(response.getContent().isEmpty()).isFalse();
    Assertions.assertThat(response.getContent().get(0).getFirstName())
        .isEqualTo(added.getFirstName());
    Assertions.assertThat(response.getContent().get(0).getId()).isEqualTo(added.getId());
  }

  @Test
  public void findByLastNameFailedTest() {

    createEmptyListMapperStubs();
    Mockito.when(repository.findByLastName(bogus.getLastName(), pageable))
        .thenReturn(emptyEntityPage);

    Page<Account> response = manager.findByLastName(bogus.getLastName(), pageable);

    Assertions.assertThat(response.getContent().isEmpty()).isTrue();
  }

  @Test
  public void findByIdTest() {

    createOptionalMapperStubs();
    Mockito.when(repository.findById(reference.getId())).thenReturn(optionalAdded);

    Optional<Account> response = manager.findById(reference.getId());

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(added.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(added.getId());
  }

  @Test
  public void findByIdFailedTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findById(bogus.getId())).thenReturn(emptyEntity);

    Optional<Account> response = manager.findById(bogus.getId());

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

  @Test
  public void findAllTest() {

    createListMapperStubs();
    Mockito.when(repository.findAll(pageable)).thenReturn(entityPage);

    Page<Account> response = manager.findAll(pageable);

    Assertions.assertThat(response.getContent().size()).isEqualTo(2);
  }

  @Test
  public void findAllEmptyTest() {

    createEmptyListMapperStubs();
    Mockito.when(repository.findAll(pageable)).thenReturn(emptyEntityPage);

    Page<Account> response = manager.findAll(pageable);

    Assertions.assertThat(response.getContent().size()).isEqualTo(0);
  }

  @Test
  public void updateTest() {

    createOptionalMapperStubs();
    Mockito.when(mapper.updateMetadata(resource, added)).thenReturn(added);
    Mockito.when(repository.findById(reference.getId())).thenReturn(optionalAdded);
    Mockito.when(repository.save(added)).thenReturn(added);

    Optional<Account> response = manager.updateById(reference.getId(), resource);

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(resource.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(reference.getId());
  }

  @Test
  public void updateFailedTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findById(reference.getId())).thenReturn(emptyEntity);

    Optional<Account> response = manager.updateById(reference.getId(), resource);

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

  @Test
  public void deleteTest() {

    createOptionalMapperStubs();
    Mockito.when(repository.findById(reference.getId())).thenReturn(optionalAdded);

    Optional<Account> response = manager.deleteById(reference.getId());

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(added.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(added.getId());
  }

  @Test
  public void deleteFailedTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findById(bogus.getId())).thenReturn(emptyEntity);

    Optional<Account> response = manager.deleteById(bogus.getId());

    Assertions.assertThat(response.isEmpty()).isTrue();
  }
}
