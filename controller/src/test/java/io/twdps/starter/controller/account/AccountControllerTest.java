package io.twdps.starter.controller.account;

import io.twdps.starter.api.account.requests.AccountRequest;
import io.twdps.starter.api.account.responses.AccountResponse;
import io.twdps.starter.api.account.responses.AddAccountResponse;
import io.twdps.starter.api.responses.ArrayResponse;
import io.twdps.starter.controller.account.mapper.AccountRequestMapper;
import io.twdps.starter.spi.account.AccountManager;
import io.twdps.starter.spi.account.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

  private AccountController controller;

  @Mock private AccountManager manager;
  @Mock private AccountRequestMapper mapper;

  private final String username = "jsmith";
  private final String bogusName = "bogus";
  private final String firstName = "Joe";
  private final String lastName = "Smith";
  private final String identifier = "12345";
  private final String fullName = "Joe Smith";

  private Account account;
  private Account output;
  private AccountRequest request;
  private AccountResponse response;
  private AddAccountResponse addResponse;
  private Optional<Account> emptyAccount = Optional.empty();
  private Optional<AccountResponse> emptyResponse = Optional.empty();
  private Optional<AccountResponse> optionalResponse;
  private Optional<Account> optionalOutput;
  private List<AccountResponse> responseList;
  private List<Account> outputList;
  private List<AccountResponse> emptyResponseList = Arrays.asList();
  private List<Account> emptyOutputList = Arrays.asList();

  /** setup data for each test. */
  @BeforeEach
  public void setup() {

    controller = new AccountController(manager, mapper);

    // use the real mapper to generate consistent objects to use in mapper stubs
    AccountRequestMapper real = Mappers.getMapper(AccountRequestMapper.class);

    request = new AccountRequest(username, firstName, lastName);
    account = real.toModel(request);
    output =
        new Account(
            identifier, account.getUserName(), account.getFirstName(), account.getLastName());
    addResponse = real.toAddAccountResponse(output);
    response = real.toAccountResponse(output);
    optionalResponse = Optional.of(response);
    optionalOutput = Optional.of(output);
    responseList = Arrays.asList(response, response);
    outputList = Arrays.asList(output, output);
  }

  @Test
  public void findByAccountIdFailTest() {

    Mockito.when(manager.findById(bogusName)).thenReturn(emptyAccount);

    ResponseEntity<AccountResponse> response = controller.findEntityById("bogus");

    assertThat(response.getStatusCodeValue()).isEqualTo(404);
  }

  @Test
  public void addAccountTest() {

    createMapperStubs();
    createAddResponseMapperStubs();
    Mockito.when(manager.add(account)).thenReturn(output);

    ResponseEntity<AddAccountResponse> response = controller.addEntity(request);

    assertThat(response.getBody().getResponse()).isEqualTo("Hello Joe");
    assertThat(response.getBody().getId()).isEqualTo(identifier);
    assertThat(response.getStatusCodeValue()).isEqualTo(201);
  }

  @Test
  public void findByUserNameTest() {

    createOptionalMapperStubs();
    Mockito.when(manager.findByUserName(username)).thenReturn(optionalOutput);

    ResponseEntity<AccountResponse> response = controller.findEntityByUsername(username);

    assertThat(response.getBody().getUserName()).isEqualTo(username);
    assertThat(response.getBody().getId()).isEqualTo(identifier);
    assertThat(response.getStatusCodeValue()).isEqualTo(200);
  }

  @Test
  public void findByUserNameFailedTest() {

    Mockito.when(manager.findByUserName(bogusName)).thenReturn(emptyAccount);

    ResponseEntity<AccountResponse> response = controller.findEntityByUsername(bogusName);

    assertThat(response.getStatusCodeValue()).isEqualTo(404);
  }

  @Test
  public void findByIdTest() {

    createOptionalMapperStubs();
    Mockito.when(manager.findById(identifier)).thenReturn(optionalOutput);

    ResponseEntity<AccountResponse> response = controller.findEntityById(identifier);

    assertThat(response.getBody().getFullName()).isEqualTo(fullName);
    assertThat(response.getBody().getId()).isEqualTo(identifier);
    assertThat(response.getStatusCodeValue()).isEqualTo(200);
  }

  @Test
  public void findByIdFailedTest() {

    Mockito.when(manager.findById(bogusName)).thenReturn(emptyAccount);

    ResponseEntity<AccountResponse> response = controller.findEntityById(bogusName);

    assertThat(response.getStatusCodeValue()).isEqualTo(404);
  }

  @Test
  public void findAllTest() {

    createListMapperStubs();
    Mockito.when(manager.findAll()).thenReturn(outputList);

    ResponseEntity<ArrayResponse<AccountResponse>> response = controller.findEntities();

    assertThat(response.getBody().getData().size()).isEqualTo(2);
    // Todo: check contents of the list objects
    assertThat(response.getStatusCodeValue()).isEqualTo(200);
  }

  @Test
  public void findAllEmptyTest() {

    createEmptyListMapperStubs();
    Mockito.when(manager.findAll()).thenReturn(emptyOutputList);

    ResponseEntity<ArrayResponse<AccountResponse>> response = controller.findEntities();

    assertThat(response.getBody().getData().size()).isEqualTo(0);
    assertThat(response.getStatusCodeValue()).isEqualTo(200);
  }

  @Test
  public void updateTest() {

    createMapperStubs();
    createOptionalMapperStubs();
    Mockito.when(manager.updateById(identifier, account)).thenReturn(optionalOutput);

    ResponseEntity<AccountResponse> response = controller.updateEntityById(identifier, request);

    assertThat(response.getBody().getFullName()).isEqualTo(fullName);
    assertThat(response.getBody().getId()).isEqualTo(identifier);
    assertThat(response.getStatusCodeValue()).isEqualTo(200);
  }

  @Test
  public void updateFailedTest() {

    createMapperStubs();
    Mockito.when(manager.updateById(bogusName, account)).thenReturn(emptyAccount);

    ResponseEntity<AccountResponse> response = controller.updateEntityById(bogusName, request);

    assertThat(response.getStatusCodeValue()).isEqualTo(404);
  }

  @Test
  public void deleteTest() {

    createOptionalMapperStubs();
    Mockito.when(manager.deleteById(identifier)).thenReturn(optionalOutput);

    ResponseEntity<AccountResponse> response = controller.deleteEntityById(identifier);

    assertThat(response.getBody().getFullName()).isEqualTo(fullName);
    assertThat(response.getBody().getId()).isEqualTo(identifier);
    assertThat(response.getStatusCodeValue()).isEqualTo(200);
  }

  @Test
  public void deleteFailedTest() {

    Mockito.when(manager.deleteById(bogusName)).thenReturn(emptyAccount);

    ResponseEntity<AccountResponse> response = controller.deleteEntityById(bogusName);

    assertThat(response.getStatusCodeValue()).isEqualTo(404);
  }

  private void createMapperStubs() {
    Mockito.when(mapper.toModel(request)).thenReturn(account);
  }

  private void createAddResponseMapperStubs() {
    Mockito.when(mapper.toAddAccountResponse(output)).thenReturn(addResponse);
  }

  private void createOptionalMapperStubs() {
    Mockito.when(mapper.toAccountResponse(optionalOutput)).thenReturn(response);
  }

  private void createListMapperStubs() {
    Mockito.when(mapper.toResponseList(outputList)).thenReturn(responseList);
  }

  private void createEmptyListMapperStubs() {
    Mockito.when(mapper.toResponseList(emptyOutputList)).thenReturn(emptyResponseList);
  }

}
