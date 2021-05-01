package io.twdps.starter.example.service.provider.subaccount;

import io.twdps.starter.example.persistence.model.SubAccountEntity;
import io.twdps.starter.example.persistence.model.SubAccountEntityRepository;

import io.twdps.starter.example.service.provider.subaccount.mapper.SubAccountEntityMapper;
import io.twdps.starter.example.service.spi.subaccount.model.SubAccount;
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
public class SubAccountServiceImplTest {

  private SubAccountServiceImpl manager;

  @Mock private SubAccountEntityRepository repository;
  @Mock private SubAccountEntityMapper mapper;

  private final String username = "jsmith";
  private final String pii = "123-45-6789";
  private final String bogusName = "bogus";
  private final String firstName = "Joe";
  private final String lastName = "Smith";
  private final String identifier = "12345";

  private SubAccount resource;
  private SubAccount output;
  private SubAccountEntity entity;
  private SubAccountEntity added;
  private Optional<SubAccount> emptySubAccount = Optional.empty();
  private Optional<SubAccountEntity> emptyEntity = Optional.empty();
  private Optional<SubAccountEntity> optionalEntity;
  private Optional<SubAccountEntity> optionalAdded;
  private Optional<SubAccount> optionalOutput;
  private List<SubAccountEntity> entityList;
  private List<SubAccount> outputList;
  private List<SubAccountEntity> emptyEntityList = Arrays.asList();
  private List<SubAccount> emptyOutputList = Arrays.asList();
  private Page<SubAccountEntity> entityPage;
  private Page<SubAccount> outputPage;
  private Page<SubAccountEntity> emptyEntityPage;
  private Page<SubAccount> emptyOutputPage;
  private Pageable pageable = Pageable.unpaged();

  /** setup data for each test. */
  @BeforeEach
  public void setup() {

    manager = new SubAccountServiceImpl(repository, mapper);

    // use the real mapper to generate consistent objects to use in mapper stubs
    SubAccountEntityMapper real = Mappers.getMapper(SubAccountEntityMapper.class);

    resource =
        SubAccount.builder()
            .userName(username)
            .pii(pii)
            .firstName(firstName)
            .lastName(lastName)
            .build();
    entity = real.toEntity(resource);
    added =
        new SubAccountEntity(
            identifier,
            entity.getUserName(),
            entity.getPii(),
            entity.getFirstName(),
            entity.getLastName(),
            entity.getAccountId());
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
    Mockito.when(mapper.toModel(emptyEntity)).thenReturn(emptySubAccount);
  }

  private void createListMapperStubs() {
    Mockito.when(mapper.toModelPage(entityPage)).thenReturn(outputPage);
  }

  private void createEmptyListMapperStubs() {
    Mockito.when(mapper.toModelPage(emptyEntityPage)).thenReturn(emptyOutputPage);
  }

  @Test
  public void findBySubAccountIdFailTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findById(Mockito.any())).thenReturn(emptyEntity);

    Optional<SubAccount> result = manager.findById("bogus");
    Assertions.assertThat(!result.isPresent()).isTrue();
  }

  @Test
  public void addSubAccountTest() {

    createMapperStubs();
    Mockito.when(repository.save(entity)).thenReturn(added);

    SubAccount response = manager.add(resource);

    Assertions.assertThat(response.getFirstName()).isEqualTo(resource.getFirstName());
    Assertions.assertThat(response.getId()).isEqualTo(added.getId());
  }

  @Test
  public void findByUserNameTest() {

    createOptionalMapperStubs();
    Mockito.when(repository.findByUserName(username)).thenReturn(optionalAdded);

    Optional<SubAccount> response = manager.findByUserName(username);

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(added.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(added.getId());
  }

  @Test
  public void findByUserNameFailedTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findByUserName(bogusName)).thenReturn(emptyEntity);

    Optional<SubAccount> response = manager.findByUserName(bogusName);

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

  @Test
  public void findByLastNameTest() {

    createListMapperStubs();
    Mockito.when(repository.findByLastName(username, pageable)).thenReturn(entityPage);

    Page<SubAccount> response = manager.findByLastName(username, pageable);

    Assertions.assertThat(response.getContent().isEmpty()).isFalse();
    Assertions.assertThat(response.getContent().get(0).getFirstName())
        .isEqualTo(added.getFirstName());
    Assertions.assertThat(response.getContent().get(0).getId()).isEqualTo(added.getId());
  }

  @Test
  public void findByLastNameFailedTest() {

    createEmptyListMapperStubs();
    Mockito.when(repository.findByLastName(bogusName, pageable)).thenReturn(emptyEntityPage);

    Page<SubAccount> response = manager.findByLastName(bogusName, pageable);

    Assertions.assertThat(response.getContent().isEmpty()).isTrue();
  }

  @Test
  public void findByIdTest() {

    createOptionalMapperStubs();
    Mockito.when(repository.findById(identifier)).thenReturn(optionalAdded);

    Optional<SubAccount> response = manager.findById(identifier);

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(added.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(added.getId());
  }

  @Test
  public void findByIdFailedTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findById(bogusName)).thenReturn(emptyEntity);

    Optional<SubAccount> response = manager.findById(bogusName);

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

  @Test
  public void findAllTest() {

    createListMapperStubs();
    Mockito.when(repository.findAll(pageable)).thenReturn(entityPage);

    Page<SubAccount> response = manager.findAll(pageable);

    Assertions.assertThat(response.getContent().size()).isEqualTo(2);
  }

  @Test
  public void findAllEmptyTest() {

    createEmptyListMapperStubs();
    Mockito.when(repository.findAll(pageable)).thenReturn(emptyEntityPage);

    Page<SubAccount> response = manager.findAll(pageable);

    Assertions.assertThat(response.getContent().size()).isEqualTo(0);
  }

  @Test
  public void updateTest() {

    createOptionalMapperStubs();
    Mockito.when(mapper.updateMetadata(resource, added)).thenReturn(added);
    Mockito.when(repository.findById(identifier)).thenReturn(optionalAdded);
    Mockito.when(repository.save(added)).thenReturn(added);

    Optional<SubAccount> response = manager.updateById(identifier, resource);

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(resource.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(identifier);
  }

  @Test
  public void updateFailedTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findById(identifier)).thenReturn(emptyEntity);

    Optional<SubAccount> response = manager.updateById(identifier, resource);

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

  @Test
  public void deleteTest() {

    createOptionalMapperStubs();
    Mockito.when(repository.findById(identifier)).thenReturn(optionalAdded);

    Optional<SubAccount> response = manager.deleteById(identifier);

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(added.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(added.getId());
  }

  @Test
  public void deleteFailedTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findById(bogusName)).thenReturn(emptyEntity);

    Optional<SubAccount> response = manager.deleteById(bogusName);

    Assertions.assertThat(response.isEmpty()).isTrue();
  }
}
