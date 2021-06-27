package io.twdps.starter.example.controller.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.twdps.starter.boot.exception.ResourceNotFoundException;
import io.twdps.starter.boot.notifier.lifecycle.entity.provider.MemoizedTimestampProvider;
import io.twdps.starter.boot.notifier.lifecycle.entity.provider.NoopEntityLifecycleNotifier;
import io.twdps.starter.boot.notifier.lifecycle.entity.spi.EntityLifecycleNotifier;
import io.twdps.starter.boot.test.data.provider.NamedDataFactory;
import io.twdps.starter.example.api.account.requests.AccountRequest;
import io.twdps.starter.example.api.account.requests.SubAccountRequest;
import io.twdps.starter.example.api.account.responses.AccountResponse;
import io.twdps.starter.example.api.account.responses.SubAccountResponse;
import io.twdps.starter.example.api.responses.PagedResponse;
import io.twdps.starter.example.controller.account.mapper.AccountRequestMapper;
import io.twdps.starter.example.data.account.model.AccountData;
import io.twdps.starter.example.data.account.provider.AccountDataFactory;
import io.twdps.starter.example.data.account.provider.AccountTestData;
import io.twdps.starter.example.data.subaccount.model.SubAccountData;
import io.twdps.starter.example.data.subaccount.provider.SubAccountDataFactory;
import io.twdps.starter.example.data.subaccount.provider.SubAccountTestData;
import io.twdps.starter.example.service.spi.account.AccountService;
import io.twdps.starter.example.service.spi.account.model.Account;
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
public class AccountControllerTest {

  private AccountController controller;

  @Mock private AccountService manager;
  @Mock private AccountRequestMapper mapper;

  private EntityLifecycleNotifier notifier =
      new NoopEntityLifecycleNotifier(new MemoizedTimestampProvider(ZonedDateTime.now()));

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
  private AccountRequest request;
  private AccountResponse response;
  private Optional<Account> emptyAccount = Optional.empty();
  private Optional<AccountResponse> emptyResponse = Optional.empty();
  private Optional<AccountResponse> optionalResponse;
  private Optional<Account> optionalOutput;
  private List<AccountResponse> responseList;
  private List<Account> outputList;
  private List<AccountResponse> emptyResponseList = Arrays.asList();
  private List<Account> emptyOutputList = Arrays.asList();
  private PagedResponse<AccountResponse> responsePage;
  private PagedResponse<AccountResponse> emptyResponsePage;
  private Page<Account> outputPage;
  private Page<Account> emptyOutputPage;
  private Pageable pageable = Pageable.unpaged();

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

    controller = new AccountController(manager, mapper, notifier);

    reference = resourceTestData.createBySpec(NamedDataFactory.DEFAULT_SPEC);
    bogus = resourceTestData.createBySpec("bogus");
    subReference = subResourceTestData.createBySpec(NamedDataFactory.DEFAULT_SPEC);
    subBogus = subResourceTestData.createBySpec("bogus");

    // use the real mapper to generate consistent objects to use in mapper stubs
    AccountRequestMapper real = Mappers.getMapper(AccountRequestMapper.class);

    request =
        new AccountRequest(
            reference.getUserName(),
            reference.getPii(),
            reference.getFirstName(),
            reference.getLastName());
    resource = real.toModel(request);
    output =
        new Account(
            reference.getId(),
            resource.getUserName(),
            resource.getPii(),
            resource.getFirstName(),
            resource.getLastName());
    response = real.toAccountResponse(output);
    optionalResponse = Optional.of(response);
    optionalOutput = Optional.of(output);
    responseList = Arrays.asList(response, response);
    outputList = Arrays.asList(output, output);
    responsePage = new PagedResponse<>(responseList, 10, (long) 100, 1, 10);
    emptyResponsePage = new PagedResponse<>(emptyResponseList, 0, (long) 0, 0, 0);
    outputPage = new PageImpl<>(outputList);
    emptyOutputPage = new PageImpl<>(emptyOutputList);

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

  private void createMapperStubs() {
    Mockito.when(mapper.toModel(request)).thenReturn(resource);
  }

  private void createResponseMapperStubs() {
    Mockito.when(mapper.toAccountResponse(output)).thenReturn(response);
  }

  private void createOptionalMapperStubs() {
    Mockito.when(mapper.toAccountResponse(optionalOutput)).thenReturn(response);
  }

  private void createListMapperStubs() {
    Mockito.when(mapper.toAccountResponsePage(outputPage)).thenReturn(responsePage);
  }

  private void createEmptyListMapperStubs() {
    Mockito.when(mapper.toAccountResponsePage(emptyOutputPage)).thenReturn(emptyResponsePage);
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
  public void findByAccountIdFailTest() throws Exception {

    Mockito.when(manager.findById(bogus.getId())).thenReturn(emptyAccount);

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<AccountResponse> response = controller.findEntityById(bogus.getId());
        });
  }

  @Test
  public void addAccountTest() throws Exception {

    createMapperStubs();
    createResponseMapperStubs();
    Mockito.when(manager.add(resource)).thenReturn(output);

    ResponseEntity<AccountResponse> response = controller.addEntity(request);

    assertThat(response.getStatusCodeValue()).isEqualTo(201);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getPii()).isEqualTo(reference.getPii());
    assertThat(response.getBody().getFullName()).isEqualTo(reference.getFullName());
    assertThat(response.getBody().getId()).isEqualTo(reference.getId());
  }

  @Test
  public void findByIdTest() throws Exception {

    createResponseMapperStubs();
    Mockito.when(manager.findById(reference.getId())).thenReturn(optionalOutput);

    ResponseEntity<AccountResponse> response = controller.findEntityById(reference.getId());

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getPii()).isEqualTo(reference.getPii());
    assertThat(response.getBody().getFullName()).isEqualTo(reference.getFullName());
    assertThat(response.getBody().getId()).isEqualTo(reference.getId());
  }

  @Test
  public void findByIdFailedTest() throws Exception {

    Mockito.when(manager.findById(bogus.getId())).thenReturn(emptyAccount);

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<AccountResponse> response = controller.findEntityById(bogus.getId());
        });
  }

  @Test
  public void findAllTest() throws Exception {

    createListMapperStubs();
    Mockito.when(manager.findAll(pageable)).thenReturn(outputPage);

    ResponseEntity<PagedResponse<AccountResponse>> response = controller.findEntities(pageable);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getItems().size()).isEqualTo(2);
    // Todo: check contents of the list objects
  }

  @Test
  public void findAllEmptyTest() throws Exception {

    createEmptyListMapperStubs();
    Mockito.when(manager.findAll(pageable)).thenReturn(emptyOutputPage);

    ResponseEntity<PagedResponse<AccountResponse>> response = controller.findEntities(pageable);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getItems().size()).isEqualTo(0);
  }

  @Test
  public void updateTest() throws Exception {

    createMapperStubs();
    createResponseMapperStubs();
    Mockito.when(manager.updateById(reference.getId(), resource)).thenReturn(optionalOutput);

    ResponseEntity<AccountResponse> response =
        controller.updateEntityById(reference.getId(), request);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getPii()).isEqualTo(reference.getPii());
    assertThat(response.getBody().getFullName()).isEqualTo(reference.getFullName());
    assertThat(response.getBody().getId()).isEqualTo(reference.getId());
  }

  @Test
  public void updateFailedTest() throws Exception {

    createMapperStubs();
    Mockito.when(manager.updateById(bogus.getId(), resource)).thenReturn(emptyAccount);

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<AccountResponse> response =
              controller.updateEntityById(bogus.getId(), request);
        });
  }

  @Test
  public void deleteTest() throws Exception {

    createResponseMapperStubs();
    Mockito.when(manager.deleteById(reference.getId())).thenReturn(optionalOutput);

    ResponseEntity<AccountResponse> response = controller.deleteEntityById(reference.getId());

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getPii()).isEqualTo(reference.getPii());
    assertThat(response.getBody().getFullName()).isEqualTo(reference.getFullName());
    assertThat(response.getBody().getId()).isEqualTo(reference.getId());
  }

  @Test
  public void deleteFailedTest() throws Exception {

    Mockito.when(manager.deleteById(bogus.getId())).thenReturn(emptyAccount);

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<AccountResponse> response = controller.deleteEntityById(bogus.getId());
        });
  }

  @Test
  public void findBySubAccountIdFailTest() throws Exception {

    Mockito.when(manager.getSubAccount(reference.getId(), subBogus.getId()))
        .thenReturn(emptySubAccount);

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<SubAccountResponse> response =
              controller.getSubAccount(reference.getId(), subBogus.getId());
        });
  }

  @Test
  public void addSubAccountTest() throws Exception {

    createSubAccountMapperStubs();
    createSubAccountResponseMapperStubs();
    Mockito.when(manager.addSubAccount(reference.getId(), subResource)).thenReturn(subOutput);

    ResponseEntity<SubAccountResponse> response =
        controller.addSubAccount(reference.getId(), subRequest);

    assertThat(response.getStatusCodeValue()).isEqualTo(201);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getId()).isEqualTo(subReference.getId());
  }

  @Test
  public void findSubAccountByIdTest() throws Exception {

    createSubAccountResponseMapperStubs();
    Mockito.when(manager.getSubAccount(reference.getId(), subReference.getId()))
        .thenReturn(optionalSubOutput);

    ResponseEntity<SubAccountResponse> response =
        controller.getSubAccount(reference.getId(), subReference.getId());

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getId()).isEqualTo(subReference.getId());
  }

  @Test
  public void findSubAccountByIdFailedTest() throws Exception {

    Mockito.when(manager.getSubAccount(reference.getId(), subBogus.getId()))
        .thenReturn(emptySubAccount);

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<SubAccountResponse> response =
              controller.getSubAccount(reference.getId(), subBogus.getId());
        });
  }

  @Test
  public void findAllSubAccountTest() throws Exception {

    createSubAccountListMapperStubs();
    Mockito.when(manager.getSubAccounts(reference.getId(), pageable)).thenReturn(subOutputPage);

    ResponseEntity<PagedResponse<SubAccountResponse>> response =
        controller.getSubAccounts(reference.getId(), pageable);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getItems().size()).isEqualTo(2);
    // Todo: check contents of the list objects
  }

  @Test
  public void findAllSubAccountEmptyTest() throws Exception {

    createEmptySubAccountListMapperStubs();
    Mockito.when(manager.getSubAccounts(reference.getId(), pageable))
        .thenReturn(emptySubOutputPage);

    ResponseEntity<PagedResponse<SubAccountResponse>> response =
        controller.getSubAccounts(reference.getId(), pageable);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getItems().size()).isEqualTo(0);
  }

  @Test
  public void updateSubAccountTest() throws Exception {

    createSubAccountMapperStubs();
    createSubAccountResponseMapperStubs();
    Mockito.when(manager.updateSubAccount(reference.getId(), subReference.getId(), subResource))
        .thenReturn(optionalSubOutput);

    ResponseEntity<SubAccountResponse> response =
        controller.updateSubAccount(reference.getId(), subReference.getId(), subRequest);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getId()).isEqualTo(subReference.getId());
  }

  @Test
  public void updateSubAccountFailedTest() throws Exception {

    createSubAccountMapperStubs();
    Mockito.when(manager.updateSubAccount(reference.getId(), subBogus.getId(), subResource))
        .thenReturn(emptySubAccount);

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<SubAccountResponse> response =
              controller.updateSubAccount(reference.getId(), subBogus.getId(), subRequest);
        });
  }

  @Test
  public void deleteSubAccountTest() throws Exception {

    createSubAccountResponseMapperStubs();
    Mockito.when(manager.deleteSubAccount(reference.getId(), subReference.getId()))
        .thenReturn(optionalSubOutput);

    ResponseEntity<SubAccountResponse> response =
        controller.deleteSubAccount(reference.getId(), subReference.getId());

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getId()).isEqualTo(subReference.getId());
  }

  @Test
  public void deleteSubAccountFailedTest() throws Exception {

    Mockito.when(manager.deleteSubAccount(reference.getId(), subBogus.getId()))
        .thenReturn(emptySubAccount);

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<SubAccountResponse> response =
              controller.deleteSubAccount(reference.getId(), subBogus.getId());
        });
  }

  /**
   * helper function to validate standard values.
   *
   * @param resource the object to validate
   */
  protected void verifyAccount(Account resource) {
    assertThat(resource.getUserName().equals(reference.getUserName()));
    assertThat(resource.getPii().equals(reference.getPii()));
    assertThat(resource.getFirstName().equals(reference.getFirstName()));
    assertThat(resource.getLastName().equals(reference.getLastName()));
    assertThat(resource.getId()).isNotEqualTo(reference.getId());
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
  private void verifyAccountResponse(AccountResponse response) {
    assertThat(response.getUserName().equals(reference.getUserName()));
    assertThat(response.getPii().equals(reference.getPii()));
    assertThat(response.getFirstName().equals(reference.getFirstName()));
    assertThat(response.getFullName().equals(reference.getFullName()));
    assertThat(response.getId()).isEqualTo(reference.getId());
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
