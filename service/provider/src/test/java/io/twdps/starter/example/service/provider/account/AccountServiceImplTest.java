package io.twdps.starter.example.service.provider.account;

import io.twdps.starter.example.persistence.model.AccountEntity;
import io.twdps.starter.example.persistence.model.AccountEntityRepository;
import io.twdps.starter.example.persistence.model.SubAccountEntity;
import io.twdps.starter.example.persistence.model.SubAccountEntityRepository;
import io.twdps.starter.example.service.provider.account.mapper.AccountEntityMapper;
import io.twdps.starter.example.service.spi.account.model.Account;
import io.twdps.starter.example.service.spi.account.model.SubAccount;
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
  @Mock private SubAccountEntityRepository subResourceRepository;

  private final String username = "jsmith";
  private final String pii = "123-45-6789";
  private final String bogusName = "bogus";
  private final String firstName = "Joe";
  private final String lastName = "Smith";
  private final String identifier = "12345";
  private final String subIdentifier = "abcde";
  private final String parentIdentifier = "12345";

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

  private SubAccount subResource;
  private SubAccount subOutput;
  private SubAccountEntity subEntity;
  private SubAccountEntity subAdded;
  private Optional<SubAccount> emptySubAccount = Optional.empty();
  private Optional<SubAccountEntity> emptySubEntity = Optional.empty();
  private Optional<SubAccountEntity> optionalSubEntity;
  private Optional<SubAccountEntity> optionalSubAdded;
  private Optional<SubAccount> optionalSubOutput;
  private List<SubAccountEntity> subEntityList;
  private List<SubAccount> subOutputList;
  private List<SubAccountEntity> emptySubEntityList = Arrays.asList();
  private List<SubAccount> emptySubOutputList = Arrays.asList();
  private Page<SubAccountEntity> subEntityPage;
  private Page<SubAccount> subOutputPage;
  private Page<SubAccountEntity> emptySubEntityPage;
  private Page<SubAccount> emptySubOutputPage;

  /** setup data for each test. */
  @BeforeEach
  public void setup() {

    manager = new AccountServiceImpl(repository, mapper, subResourceRepository);

    // use the real mapper to generate consistent objects to use in mapper stubs
    AccountEntityMapper real = Mappers.getMapper(AccountEntityMapper.class);

    resource =
        Account.builder()
            .userName(username)
            .pii(pii)
            .firstName(firstName)
            .lastName(lastName)
            .build();
    entity = real.toEntity(resource);
    added =
        new AccountEntity(
            identifier,
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
            .userName(username)
            .firstName(firstName)
            .lastName(lastName)
            .build();
    subEntity = real.toSubAccountEntity(subResource);
    subAdded =
        new SubAccountEntity(
            subIdentifier,
            subEntity.getUserName(),
            subEntity.getPii(),
            subEntity.getFirstName(),
            subEntity.getLastName(),
            parentIdentifier);
    subOutput = real.toSubAccountModel(subAdded);
    optionalSubEntity = Optional.of(subEntity);
    optionalSubAdded = Optional.of(subAdded);
    optionalSubOutput = Optional.of(subOutput);
    subEntityList = Arrays.asList(subAdded, subAdded);
    subOutputList = Arrays.asList(subOutput, subOutput);
    subEntityPage = new PageImpl<>(subEntityList);
    subOutputPage = new PageImpl<>(subOutputList);
    emptySubEntityPage = new PageImpl<>(emptySubEntityList);
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
    Mockito.when(mapper.toSubAccountEntity(subResource)).thenReturn(subEntity);
    Mockito.when(mapper.toSubAccountModel(subAdded)).thenReturn(subOutput);
  }

  private void createOptionalSubAccountMapperStubs() {
    Mockito.when(mapper.toSubAccountModel(optionalSubAdded)).thenReturn(optionalSubOutput);
  }

  private void createEmptySubAccountMapperStubs() {
    Mockito.when(mapper.toSubAccountModel(emptySubEntity)).thenReturn(emptySubAccount);
  }

  private void createSubAccountListMapperStubs() {
    Mockito.when(mapper.toSubAccountModelPage(subEntityPage)).thenReturn(subOutputPage);
  }

  private void createEmptySubAccountListMapperStubs() {
    Mockito.when(mapper.toSubAccountModelPage(emptySubEntityPage)).thenReturn(emptySubOutputPage);
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

    Account response = manager.add(resource);

    Assertions.assertThat(response.getFirstName()).isEqualTo(resource.getFirstName());
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
    Mockito.when(repository.findByLastName(username, pageable)).thenReturn(entityPage);

    Page<Account> response = manager.findByLastName(username, pageable);

    Assertions.assertThat(response.getContent().isEmpty()).isFalse();
    Assertions.assertThat(response.getContent().get(0).getFirstName())
        .isEqualTo(added.getFirstName());
    Assertions.assertThat(response.getContent().get(0).getId()).isEqualTo(added.getId());
  }

  @Test
  public void findByLastNameFailedTest() {

    createEmptyListMapperStubs();
    Mockito.when(repository.findByLastName(bogusName, pageable)).thenReturn(emptyEntityPage);

    Page<Account> response = manager.findByLastName(bogusName, pageable);

    Assertions.assertThat(response.getContent().isEmpty()).isTrue();
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
    Mockito.when(repository.findById(identifier)).thenReturn(optionalAdded);
    Mockito.when(repository.save(added)).thenReturn(added);

    Optional<Account> response = manager.updateById(identifier, resource);

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(resource.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(identifier);
  }

  @Test
  public void updateFailedTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findById(identifier)).thenReturn(emptyEntity);

    Optional<Account> response = manager.updateById(identifier, resource);

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

  @Test
  public void findBySubAccountIdFailTest() {

    createEmptySubAccountMapperStubs();
    Mockito.when(subResourceRepository.findById(Mockito.any())).thenReturn(emptySubEntity);

    Optional<SubAccount> result =
        manager.getSubAccount(identifier, "bogus");
    Assertions.assertThat(!result.isPresent()).isTrue();
  }

  @Test
  public void addSubAccountTest() {

    createSubAccountMapperStubs();
    Mockito.when(subResourceRepository.save(subEntity)).thenReturn(subAdded);

    SubAccount response =
        manager.addSubAccount(identifier, subResource);

    Assertions.assertThat(response.getFirstName()).isEqualTo(subResource.getFirstName());
    Assertions.assertThat(response.getId()).isEqualTo(subAdded.getId());
    Assertions.assertThat(response.getId()).isEqualTo(subIdentifier);
  }

  @Test
  public void findSubAccountByIdTest() {

    createOptionalSubAccountMapperStubs();
    Mockito.when(subResourceRepository.findById(subIdentifier)).thenReturn(optionalSubAdded);

    Optional<SubAccount> response =
        manager.getSubAccount(identifier, subIdentifier);

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(subAdded.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(subAdded.getId());
  }

  @Test
  public void findSubAccountByIdFailedTest() {

    createEmptySubAccountMapperStubs();
    Mockito.when(subResourceRepository.findById(bogusName)).thenReturn(emptySubEntity);

    Optional<SubAccount> response =
        manager.getSubAccount(identifier, bogusName);

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

  @Test
  public void findAllSubAccountTest() {

    createSubAccountListMapperStubs();
    Mockito.when(subResourceRepository.findAllByAccountId(identifier, pageable))
        .thenReturn(subEntityPage);

    Page<SubAccount> response =
        manager.getSubAccounts(identifier, pageable);

    Assertions.assertThat(response.getContent().size()).isEqualTo(2);
  }

  @Test
  public void findAllSubAccountEmptyTest() {

    createEmptySubAccountListMapperStubs();
    Mockito.when(subResourceRepository.findAllByAccountId(identifier, pageable))
        .thenReturn(emptySubEntityPage);

    Page<SubAccount> response =
        manager.getSubAccounts(identifier, pageable);

    Assertions.assertThat(response.getContent().size()).isEqualTo(0);
  }

  @Test
  public void updateSubAccountTest() {

    createOptionalSubAccountMapperStubs();
    Mockito.when(mapper.updateSubAccountMetadata(subResource, subAdded))
        .thenReturn(subAdded);
    Mockito.when(subResourceRepository.findById(subIdentifier)).thenReturn(optionalSubAdded);
    Mockito.when(subResourceRepository.save(subAdded)).thenReturn(subAdded);

    Optional<SubAccount> response =
        manager.updateSubAccount(identifier, subIdentifier, subResource);

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(subResource.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(subIdentifier);
  }

  @Test
  public void updateSubAccountFailedTest() {

    createEmptySubAccountMapperStubs();
    Mockito.when(subResourceRepository.findById(subIdentifier)).thenReturn(emptySubEntity);

    Optional<SubAccount> response =
        manager.updateSubAccount(identifier, subIdentifier, subResource);

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

  @Test
  public void deleteSubAccountTest() {

    createOptionalSubAccountMapperStubs();
    Mockito.when(subResourceRepository.findById(subIdentifier)).thenReturn(optionalSubAdded);

    Optional<SubAccount> response =
        manager.deleteSubAccount(identifier, subIdentifier);

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(subAdded.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(subAdded.getId());
  }

  @Test
  public void deleteSubAccountFailedTest() {

    createEmptySubAccountMapperStubs();
    Mockito.when(subResourceRepository.findById(bogusName)).thenReturn(emptySubEntity);

    Optional<SubAccount> response =
        manager.deleteSubAccount(identifier, bogusName);

    Assertions.assertThat(response.isEmpty()).isTrue();
  }
}
