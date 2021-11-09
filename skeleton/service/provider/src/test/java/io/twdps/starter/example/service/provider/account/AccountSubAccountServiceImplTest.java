package io.twdps.starter.example.service.provider.account;

import io.twdps.starter.boot.test.data.provider.NamedDataFactory;
import io.twdps.starter.example.data.subaccount.model.SubAccountData;
import io.twdps.starter.example.data.subaccount.provider.SubAccountDataFactory;
import io.twdps.starter.example.data.subaccount.provider.SubAccountTestData;
import io.twdps.starter.example.service.provider.account.mapper.AccountSubAccountEntityMapper;
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
public class AccountSubAccountServiceImplTest {

  private AccountSubAccountServiceImpl manager;

  @Mock private AccountSubAccountEntityMapper mapper;
  @Mock private SubAccountService resourceService;

  private final SubAccountTestData resourceTestDataLoader = new SubAccountTestData();
  private final SubAccountDataFactory resourceTestData =
      new SubAccountDataFactory(resourceTestDataLoader);

  private SubAccountData reference;
  private SubAccountData bogus;

  private final Pageable pageable = Pageable.unpaged();
  private final String parentIdentifier = "uuid-parent";
  private io.twdps.starter.example.service.spi.subaccount.model.SubAccount serviceResource;
  private io.twdps.starter.example.service.spi.subaccount.model.SubAccount serviceOutput;
  private List<io.twdps.starter.example.service.spi.subaccount.model.SubAccount> serviceOutputList;
  private List<io.twdps.starter.example.service.spi.subaccount.model.SubAccount>
      emptyServiceOutputList = Arrays.asList();
  private Page<io.twdps.starter.example.service.spi.subaccount.model.SubAccount> serviceOutputPage;
  private Page<io.twdps.starter.example.service.spi.subaccount.model.SubAccount>
      emptyServiceOutputPage;
  private Optional<io.twdps.starter.example.service.spi.subaccount.model.SubAccount>
      optionalServiceOutput;
  private Optional<io.twdps.starter.example.service.spi.subaccount.model.SubAccount>
      emptyServiceOutput = Optional.empty();
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

    manager = new AccountSubAccountServiceImpl(mapper, resourceService);

    reference = resourceTestData.createBySpec(NamedDataFactory.DEFAULT_SPEC);
    bogus = resourceTestData.createBySpec("bogus");

    subResource =
        SubAccount.builder()
            .userName(reference.getUserName())
            .firstName(reference.getFirstName())
            .lastName(reference.getLastName())
            .build();
    serviceResource =
        io.twdps.starter.example.service.spi.subaccount.model.SubAccount.builder()
            .userName(reference.getUserName())
            .firstName(reference.getFirstName())
            .lastName(reference.getLastName())
            .pii(reference.getPii())
            .build();
    serviceOutput =
        io.twdps.starter.example.service.spi.subaccount.model.SubAccount.builder()
            .id(reference.getId())
            .userName(reference.getUserName())
            .firstName(reference.getFirstName())
            .lastName(reference.getLastName())
            .pii(reference.getPii())
            .accountId(parentIdentifier)
            .build();
    optionalServiceOutput = Optional.of(serviceOutput);
    serviceOutputList = Arrays.asList(serviceOutput, serviceOutput);
    serviceOutputPage = new PageImpl<>(serviceOutputList);
    emptyServiceOutputPage = new PageImpl<>(emptyServiceOutputList);
    emptySubOutputPage = new PageImpl<>(emptySubOutputList);

    // use the real mapper to generate consistent objects to use in mapper stubs
    AccountSubAccountEntityMapper real = Mappers.getMapper(AccountSubAccountEntityMapper.class);
    subOutput = real.fromServiceModel(serviceOutput);
    optionalSubOutput = Optional.of(subOutput);
    subOutputList = Arrays.asList(subOutput, subOutput);
    subOutputPage = new PageImpl<>(subOutputList);
  }

  private void createSubAccountMapperStubs() {
    Mockito.when(mapper.toServiceModel(subResource, parentIdentifier)).thenReturn(serviceResource);
  }

  private void createReverseSubAccountMapperStubs() {
    Mockito.when(mapper.fromServiceModel(serviceOutput)).thenReturn(subOutput);
  }

  private void createOptionalSubAccountMapperStubs() {
    Mockito.when(mapper.fromServiceModel(optionalServiceOutput)).thenReturn(optionalSubOutput);
  }

  private void createEmptySubAccountMapperStubs() {
    Mockito.when(mapper.fromServiceModel(emptyServiceOutput)).thenReturn(emptySubResource);
  }

  private void createSubAccountListMapperStubs() {
    Mockito.when(mapper.fromServiceModelPage(serviceOutputPage)).thenReturn(subOutputPage);
  }

  private void createEmptySubAccountListMapperStubs() {
    Mockito.when(mapper.fromServiceModelPage(emptyServiceOutputPage))
        .thenReturn(emptySubOutputPage);
  }

  @Test
  public void findByIdFailTest() {

    createEmptySubAccountMapperStubs();
    Mockito.when(resourceService.findById(Mockito.any())).thenReturn(emptyServiceOutput);

    Optional<SubAccount> result = manager.findById(parentIdentifier, bogus.getId());
    Assertions.assertThat(!result.isPresent()).isTrue();
  }

  @Test
  public void addTest() {

    createSubAccountMapperStubs();
    createReverseSubAccountMapperStubs();
    Mockito.when(resourceService.add(serviceResource)).thenReturn(serviceOutput);

    SubAccount response = manager.add(parentIdentifier, subResource);

    Assertions.assertThat(response.getFirstName()).isEqualTo(subResource.getFirstName());
    Assertions.assertThat(response.getId()).isEqualTo(reference.getId());
  }

  @Test
  public void findByIdTest() {

    createOptionalSubAccountMapperStubs();
    Mockito.when(resourceService.findById(reference.getId())).thenReturn(optionalServiceOutput);

    Optional<SubAccount> response = manager.findById(parentIdentifier, reference.getId());

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(subOutput.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(subOutput.getId());
  }

  @Test
  public void findByIdFailedTest() {

    createEmptySubAccountMapperStubs();
    Mockito.when(resourceService.findById(bogus.getId())).thenReturn(emptyServiceOutput);

    Optional<SubAccount> response = manager.findById(parentIdentifier, bogus.getId());

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

  @Test
  public void findAllTest() {

    createSubAccountListMapperStubs();
    Mockito.when(resourceService.findAllByAccountId(parentIdentifier, pageable))
        .thenReturn(serviceOutputPage);

    Page<SubAccount> response = manager.findAll(parentIdentifier, pageable);

    Assertions.assertThat(response.getContent().size()).isEqualTo(2);
  }

  @Test
  public void findAllEmptyTest() {

    createEmptySubAccountListMapperStubs();
    Mockito.when(resourceService.findAllByAccountId(parentIdentifier, pageable))
        .thenReturn(emptyServiceOutputPage);

    Page<SubAccount> response = manager.findAll(parentIdentifier, pageable);

    Assertions.assertThat(response.getContent().size()).isEqualTo(0);
  }

  @Test
  public void updateByIdTest() {

    createSubAccountMapperStubs();
    createOptionalSubAccountMapperStubs();
    Mockito.when(resourceService.updateById(reference.getId(), serviceResource))
        .thenReturn(optionalServiceOutput);

    Optional<SubAccount> response =
        manager.updateById(parentIdentifier, reference.getId(), subResource);

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(subResource.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(reference.getId());
  }

  @Test
  public void updateByIdFailedTest() {

    createSubAccountMapperStubs();
    createEmptySubAccountMapperStubs();
    Mockito.when(resourceService.updateById(reference.getId(), serviceResource))
        .thenReturn(emptyServiceOutput);

    Optional<SubAccount> response =
        manager.updateById(parentIdentifier, reference.getId(), subResource);

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

  @Test
  public void deleteByIdTest() {

    createOptionalSubAccountMapperStubs();
    Mockito.when(resourceService.deleteById(reference.getId())).thenReturn(optionalServiceOutput);

    Optional<SubAccount> response = manager.deleteById(parentIdentifier, reference.getId());

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(subOutput.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(subOutput.getId());
  }

  @Test
  public void deleteByIdFailedTest() {

    createEmptySubAccountMapperStubs();
    Mockito.when(resourceService.deleteById(bogus.getId())).thenReturn(emptyServiceOutput);

    Optional<SubAccount> response = manager.deleteById(parentIdentifier, bogus.getId());

    Assertions.assertThat(response.isEmpty()).isTrue();
  }
}
