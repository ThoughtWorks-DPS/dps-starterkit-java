package io.twdps.starter.example.service.provider.account;

import io.twdps.starter.boot.test.data.provider.NamedDataFactory;
import io.twdps.starter.example.data.account.model.AccountData;
import io.twdps.starter.example.data.account.provider.AccountDataFactory;
import io.twdps.starter.example.data.account.provider.AccountTestData;
import io.twdps.starter.example.data.subaccount.model.SubAccountData;
import io.twdps.starter.example.data.subaccount.provider.SubAccountDataFactory;
import io.twdps.starter.example.data.subaccount.provider.SubAccountTestData;
import io.twdps.starter.example.persistence.model.AccountEntity;
import io.twdps.starter.example.persistence.model.AccountEntityRepository;
import io.twdps.starter.example.service.provider.account.mapper.AccountEntityMapper;
import io.twdps.starter.example.service.spi.account.model.Account;
import io.twdps.starter.example.service.spi.account.model.SubAccount;
import io.twdps.starter.example.service.spi.subaccount.SubAccountService;
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
  @Mock private SubAccountService subResourceService;

  private AccountTestData resourceTestDataLoader = new AccountTestData();
  private AccountDataFactory resourceTestData = new AccountDataFactory(resourceTestDataLoader);
  private SubAccountTestData subResourceTestDataLoader = new SubAccountTestData();
  private SubAccountDataFactory subResourceTestData =
      new SubAccountDataFactory(subResourceTestDataLoader);

  private AccountData reference;
  private AccountData bogus;
  private SubAccountData subReference;
  private SubAccountData subBogus;

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

  private io.twdps.starter.example.service.spi.subaccount.model.SubAccount serviceSubResource;
  private io.twdps.starter.example.service.spi.subaccount.model.SubAccount serviceSubOutput;
  private Optional<io.twdps.starter.example.service.spi.subaccount.model.SubAccount>
      optionalServiceSubResource;
  private List<io.twdps.starter.example.service.spi.subaccount.model.SubAccount>
      serviceSubOutputList;
  private List<io.twdps.starter.example.service.spi.subaccount.model.SubAccount>
      emptyServiceSubOutputList = Arrays.asList();
  private Page<io.twdps.starter.example.service.spi.subaccount.model.SubAccount>
      serviceSubOutputPage;
  private Page<io.twdps.starter.example.service.spi.subaccount.model.SubAccount>
      emptyServiceSubOutputPage;
  private Optional<io.twdps.starter.example.service.spi.subaccount.model.SubAccount>
      optionalServiceSubOutput;
  private Optional<io.twdps.starter.example.service.spi.subaccount.model.SubAccount>
      emptyServiceSubOutput = Optional.empty();
  private SubAccount subResource;
  private SubAccount subOutput;
  private Optional<SubAccount> emptySubResource = Optional.empty();
  private Optional<SubAccount> optionalSubOutput;
  private List<SubAccount> subOutputList;
  private List<SubAccount> emptySubOutputList = Arrays.asList();
  private Page<SubAccount> subOutputPage;
  private Page<SubAccount> emptySubOutputPage;

  /** setup data for each test. */
  @BeforeEach
  public void setup() {

    manager = new AccountServiceImpl(repository, mapper, subResourceService);

    reference = resourceTestData.createBySpec(NamedDataFactory.DEFAULT_SPEC);
    bogus = resourceTestData.createBySpec("bogus");
    subReference = subResourceTestData.createBySpec(NamedDataFactory.DEFAULT_SPEC);
    subBogus = subResourceTestData.createBySpec("bogus");

    // use the real mapper to generate consistent objects to use in mapper stubs
    AccountEntityMapper real = Mappers.getMapper(AccountEntityMapper.class);

    resource =
        Account.builder()
            .userName(reference.getUserName())
            .pii(reference.getPii())
            .firstName(reference.getFirstName())
            .lastName(reference.getLastName())
            .build();
    entity = real.toEntity(resource);
    added =
        new AccountEntity(
            reference.getId(),
            entity.getUserName(),
            entity.getPii(),
            entity.getFirstName(),
            entity.getLastName());
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

    subResource =
        SubAccount.builder()
            .userName(subReference.getUserName())
            .firstName(subReference.getFirstName())
            .lastName(subReference.getLastName())
            .build();
    serviceSubResource =
        io.twdps.starter.example.service.spi.subaccount.model.SubAccount.builder()
            .userName(subReference.getUserName())
            .firstName(subReference.getFirstName())
            .lastName(subReference.getLastName())
            .pii(subReference.getPii())
            .build();
    serviceSubOutput =
        io.twdps.starter.example.service.spi.subaccount.model.SubAccount.builder()
            .id(subReference.getId())
            .userName(subReference.getUserName())
            .firstName(subReference.getFirstName())
            .lastName(subReference.getLastName())
            .pii(subReference.getPii())
            .accountId(reference.getId())
            .build();
    optionalServiceSubResource = Optional.of(serviceSubResource);
    optionalServiceSubOutput = Optional.of(serviceSubOutput);
    serviceSubOutputList = Arrays.asList(serviceSubOutput, serviceSubOutput);
    serviceSubOutputPage = new PageImpl<>(serviceSubOutputList);
    emptyServiceSubOutputPage = new PageImpl<>(emptyServiceSubOutputList);
    subOutput = real.fromServiceSubAccount(serviceSubOutput);
    optionalSubOutput = Optional.of(subOutput);
    subOutputList = Arrays.asList(subOutput, subOutput);
    subOutputPage = new PageImpl<>(subOutputList);
    emptySubOutputPage = new PageImpl<>(emptySubOutputList);
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

  private void createSubAccountMapperStubs() {
    Mockito.when(mapper.toServiceSubAccount(subResource, reference.getId()))
        .thenReturn(serviceSubResource);
  }

  private void createReverseSubAccountMapperStubs() {
    Mockito.when(mapper.fromServiceSubAccount(serviceSubOutput)).thenReturn(subOutput);
  }

  private void createOptionalSubAccountMapperStubs() {
    Mockito.when(mapper.fromServiceSubAccount(optionalServiceSubOutput))
        .thenReturn(optionalSubOutput);
  }

  private void createEmptySubAccountMapperStubs() {
    Mockito.when(mapper.fromServiceSubAccount(emptyServiceSubOutput)).thenReturn(emptySubResource);
  }

  private void createSubAccountListMapperStubs() {
    Mockito.when(mapper.fromServiceSubAccountPage(serviceSubOutputPage)).thenReturn(subOutputPage);
  }

  private void createEmptySubAccountListMapperStubs() {
    Mockito.when(mapper.fromServiceSubAccountPage(emptyServiceSubOutputPage))
        .thenReturn(emptySubOutputPage);
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

  @Test
  public void findBySubAccountIdFailTest() {

    createEmptySubAccountMapperStubs();
    Mockito.when(subResourceService.findById(Mockito.any())).thenReturn(emptyServiceSubOutput);

    Optional<SubAccount> result = manager.getSubAccount(reference.getId(), bogus.getId());
    Assertions.assertThat(!result.isPresent()).isTrue();
  }

  @Test
  public void addSubAccountTest() {

    createSubAccountMapperStubs();
    createReverseSubAccountMapperStubs();
    Mockito.when(subResourceService.add(serviceSubResource)).thenReturn(serviceSubOutput);

    SubAccount response = manager.addSubAccount(reference.getId(), subResource);

    Assertions.assertThat(response.getFirstName()).isEqualTo(subResource.getFirstName());
    Assertions.assertThat(response.getId()).isEqualTo(subReference.getId());
  }

  @Test
  public void findSubAccountByIdTest() {

    createOptionalSubAccountMapperStubs();
    Mockito.when(subResourceService.findById(subReference.getId()))
        .thenReturn(optionalServiceSubOutput);

    Optional<SubAccount> response = manager.getSubAccount(reference.getId(), subReference.getId());

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(subOutput.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(subOutput.getId());
  }

  @Test
  public void findSubAccountByIdFailedTest() {

    createEmptySubAccountMapperStubs();
    Mockito.when(subResourceService.findById(bogus.getId())).thenReturn(emptyServiceSubOutput);

    Optional<SubAccount> response = manager.getSubAccount(reference.getId(), bogus.getId());

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

  @Test
  public void findAllSubAccountTest() {

    createSubAccountListMapperStubs();
    Mockito.when(subResourceService.findAllByAccountId(reference.getId(), pageable))
        .thenReturn(serviceSubOutputPage);

    Page<SubAccount> response = manager.getSubAccounts(reference.getId(), pageable);

    Assertions.assertThat(response.getContent().size()).isEqualTo(2);
  }

  @Test
  public void findAllSubAccountEmptyTest() {

    createEmptySubAccountListMapperStubs();
    Mockito.when(subResourceService.findAllByAccountId(reference.getId(), pageable))
        .thenReturn(emptyServiceSubOutputPage);

    Page<SubAccount> response = manager.getSubAccounts(reference.getId(), pageable);

    Assertions.assertThat(response.getContent().size()).isEqualTo(0);
  }

  @Test
  public void updateSubAccountTest() {

    createSubAccountMapperStubs();
    createOptionalSubAccountMapperStubs();
    Mockito.when(subResourceService.updateById(subReference.getId(), serviceSubResource))
        .thenReturn(optionalServiceSubOutput);

    Optional<SubAccount> response =
        manager.updateSubAccount(reference.getId(), subReference.getId(), subResource);

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(subResource.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(subReference.getId());
  }

  @Test
  public void updateSubAccountFailedTest() {

    createSubAccountMapperStubs();
    createEmptySubAccountMapperStubs();
    Mockito.when(subResourceService.updateById(subReference.getId(), serviceSubResource))
        .thenReturn(emptyServiceSubOutput);

    Optional<SubAccount> response =
        manager.updateSubAccount(reference.getId(), subReference.getId(), subResource);

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

  @Test
  public void deleteSubAccountTest() {

    createOptionalSubAccountMapperStubs();
    Mockito.when(subResourceService.deleteById(subReference.getId()))
        .thenReturn(optionalServiceSubOutput);

    Optional<SubAccount> response =
        manager.deleteSubAccount(reference.getId(), subReference.getId());

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(subOutput.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(subOutput.getId());
  }

  @Test
  public void deleteSubAccountFailedTest() {

    createEmptySubAccountMapperStubs();
    Mockito.when(subResourceService.deleteById(bogus.getId())).thenReturn(emptyServiceSubOutput);

    Optional<SubAccount> response = manager.deleteSubAccount(reference.getId(), bogus.getId());

    Assertions.assertThat(response.isEmpty()).isTrue();
  }
}
