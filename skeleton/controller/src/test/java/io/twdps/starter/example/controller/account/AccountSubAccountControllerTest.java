package io.twdps.starter.example.controller.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.twdps.starter.boot.exception.ResourceNotFoundException;
import io.twdps.starter.boot.notifier.lifecycle.entity.provider.MemoizedTimestampProvider;
import io.twdps.starter.boot.notifier.lifecycle.entity.provider.NoopEntityLifecycleNotifier;
import io.twdps.starter.boot.notifier.lifecycle.entity.spi.EntityLifecycleNotifier;
import io.twdps.starter.boot.test.data.provider.NamedDataFactory;
import io.twdps.starter.example.api.account.requests.SubAccountRequest;
import io.twdps.starter.example.api.account.responses.SubAccountResponse;
import io.twdps.starter.example.api.responses.PagedResponse;
import io.twdps.starter.example.controller.account.mapper.AccountSubAccountRequestMapper;
import io.twdps.starter.example.data.subaccount.model.SubAccountData;
import io.twdps.starter.example.data.subaccount.provider.SubAccountDataFactory;
import io.twdps.starter.example.data.subaccount.provider.SubAccountTestData;
import io.twdps.starter.example.service.spi.account.AccountSubAccountService;
import io.twdps.starter.example.service.spi.account.model.SubAccount;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class AccountSubAccountControllerTest {

  private AccountSubAccountController controller;

  @Mock private AccountSubAccountService manager;
  @Mock private AccountSubAccountRequestMapper mapper;

  private EntityLifecycleNotifier notifier =
      new NoopEntityLifecycleNotifier(new MemoizedTimestampProvider(ZonedDateTime.now()));

  private SubAccountTestData subResourceTestDataLoader = new SubAccountTestData();
  private SubAccountDataFactory subResourceTestData =
      new SubAccountDataFactory(subResourceTestDataLoader);

  private SubAccountData subReference;
  private SubAccountData subBogus;

  private final String parentIdentifier = "uuid-parent";
  private final Pageable pageable = Pageable.unpaged();

  private SubAccount subResource;
  private SubAccount subOutput;
  private SubAccountRequest subRequest;
  private SubAccountResponse subResponse;
  private Optional<SubAccount> emptySubAccount = Optional.empty();
  private Optional<SubAccountResponse> emptySubResponse = Optional.empty();
  private Optional<SubAccountResponse> optionalSubResponse;
  private Optional<SubAccount> optionalSubOutput;
  private List<SubAccountResponse> subResponseList;
  private List<SubAccount> subOutputList;
  private List<SubAccountResponse> emptySubResponseList = Arrays.asList();
  private List<SubAccount> emptySubOutputList = Arrays.asList();
  private PagedResponse<SubAccountResponse> subResponsePage;
  private PagedResponse<SubAccountResponse> emptySubResponsePage;
  private Page<SubAccount> subOutputPage;
  private Page<SubAccount> emptySubOutputPage;

  /** setup data for each test. */
  @BeforeEach
  public void setup() {

    controller = new AccountSubAccountController(manager, mapper, notifier);

    subReference = subResourceTestData.createBySpec(NamedDataFactory.DEFAULT_SPEC);
    subBogus = subResourceTestData.createBySpec("bogus");

    // use the real mapper to generate consistent objects to use in mapper stubs
    AccountSubAccountRequestMapper real = Mappers.getMapper(AccountSubAccountRequestMapper.class);

    subRequest =
        new SubAccountRequest(
            subReference.getUserName(), subReference.getFirstName(), subReference.getLastName());
    subResource = real.toModel(subRequest);
    subOutput =
        new SubAccount(
            subReference.getId(),
            subResource.getUserName(),
            subResource.getFirstName(),
            subResource.getLastName());
    subResponse = real.toSubAccountResponse(subOutput);
    optionalSubResponse = Optional.of(subResponse);
    optionalSubOutput = Optional.of(subOutput);
    subResponseList = Arrays.asList(subResponse, subResponse);
    subOutputList = Arrays.asList(subOutput, subOutput);
    subResponsePage = new PagedResponse<>(subResponseList, 10, (long) 100, 1, 10);
    emptySubResponsePage = new PagedResponse<>(emptySubResponseList, 0, (long) 0, 0, 0);
    subOutputPage = new PageImpl<>(subOutputList);
    emptySubOutputPage = new PageImpl<>(emptySubOutputList);
  }

  private void createSubAccountMapperStubs() {
    Mockito.when(mapper.toModel(subRequest)).thenReturn(subResource);
  }

  private void createSubAccountResponseMapperStubs() {
    Mockito.when(mapper.toSubAccountResponse(subOutput)).thenReturn(subResponse);
  }

  private void createOptionalSubAccountMapperStubs() {
    Mockito.when(mapper.toSubAccountResponse(optionalSubOutput)).thenReturn(subResponse);
  }

  private void createSubAccountListMapperStubs() {
    Mockito.when(mapper.toSubAccountResponsePage(subOutputPage)).thenReturn(subResponsePage);
  }

  private void createEmptySubAccountListMapperStubs() {
    Mockito.when(mapper.toSubAccountResponsePage(emptySubOutputPage))
        .thenReturn(emptySubResponsePage);
  }

  @Test
  public void findBySubAccountIdFailTest() throws Exception {

    Mockito.when(manager.findById(parentIdentifier, subBogus.getId())).thenReturn(emptySubAccount);

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<SubAccountResponse> response =
              controller.getSubEntity(parentIdentifier, subBogus.getId());
        });
  }

  @Test
  public void addSubEntityTest() throws Exception {

    createSubAccountMapperStubs();
    createSubAccountResponseMapperStubs();
    Mockito.when(manager.add(parentIdentifier, subResource)).thenReturn(subOutput);

    ResponseEntity<SubAccountResponse> response =
        controller.addSubEntity(parentIdentifier, subRequest);

    assertThat(response.getStatusCodeValue()).isEqualTo(201);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getId()).isEqualTo(subReference.getId());
  }

  @Test
  public void findSubAccountByIdTest() throws Exception {

    createSubAccountResponseMapperStubs();
    Mockito.when(manager.findById(parentIdentifier, subReference.getId()))
        .thenReturn(optionalSubOutput);

    ResponseEntity<SubAccountResponse> response =
        controller.getSubEntity(parentIdentifier, subReference.getId());

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getId()).isEqualTo(subReference.getId());
  }

  @Test
  public void findSubAccountByIdFailedTest() throws Exception {

    Mockito.when(manager.findById(parentIdentifier, subBogus.getId())).thenReturn(emptySubAccount);

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<SubAccountResponse> response =
              controller.getSubEntity(parentIdentifier, subBogus.getId());
        });
  }

  @Test
  public void findAllSubAccountTest() throws Exception {

    createSubAccountListMapperStubs();
    Mockito.when(manager.findAll(parentIdentifier, pageable)).thenReturn(subOutputPage);

    ResponseEntity<PagedResponse<SubAccountResponse>> response =
        controller.getSubEntities(parentIdentifier, pageable);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getItems().size()).isEqualTo(2);
    // Todo: check contents of the list objects
  }

  @Test
  public void findAllSubAccountEmptyTest() throws Exception {

    createEmptySubAccountListMapperStubs();
    Mockito.when(manager.findAll(parentIdentifier, pageable)).thenReturn(emptySubOutputPage);

    ResponseEntity<PagedResponse<SubAccountResponse>> response =
        controller.getSubEntities(parentIdentifier, pageable);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getItems().size()).isEqualTo(0);
  }

  @Test
  public void updateSubAccountTest() throws Exception {

    createSubAccountMapperStubs();
    createSubAccountResponseMapperStubs();
    Mockito.when(manager.updateById(parentIdentifier, subReference.getId(), subResource))
        .thenReturn(optionalSubOutput);

    ResponseEntity<SubAccountResponse> response =
        controller.updateSubEntity(parentIdentifier, subReference.getId(), subRequest);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getId()).isEqualTo(subReference.getId());
  }

  @Test
  public void updateSubAccountFailedTest() throws Exception {

    createSubAccountMapperStubs();
    Mockito.when(manager.updateById(parentIdentifier, subBogus.getId(), subResource))
        .thenReturn(emptySubAccount);

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<SubAccountResponse> response =
              controller.updateSubEntity(parentIdentifier, subBogus.getId(), subRequest);
        });
  }

  @Test
  public void deleteSubAccountTest() throws Exception {

    createSubAccountResponseMapperStubs();
    Mockito.when(manager.deleteById(parentIdentifier, subReference.getId()))
        .thenReturn(optionalSubOutput);

    ResponseEntity<SubAccountResponse> response =
        controller.deleteSubEntity(parentIdentifier, subReference.getId());

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getId()).isEqualTo(subReference.getId());
  }

  @Test
  public void deleteSubAccountFailedTest() throws Exception {

    Mockito.when(manager.deleteById(parentIdentifier, subBogus.getId()))
        .thenReturn(emptySubAccount);

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<SubAccountResponse> response =
              controller.deleteSubEntity(parentIdentifier, subBogus.getId());
        });
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
  protected void verifySubAccountResponse(SubAccountResponse response) {
    assertThat(response.getId()).isEqualTo(subReference.getId());
  }
}
