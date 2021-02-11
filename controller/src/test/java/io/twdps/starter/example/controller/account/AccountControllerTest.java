package io.twdps.starter.example.controller.account;

import io.twdps.starter.example.api.account.requests.AccountRequest;
import io.twdps.starter.example.api.account.responses.AccountResponse;
import io.twdps.starter.example.api.responses.ArrayResponse;
import io.twdps.starter.example.controller.account.mapper.AccountRequestMapper;
import io.twdps.starter.example.service.spi.account.AccountService;
import io.twdps.starter.example.service.spi.account.model.Account;
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

  @Mock private AccountService manager;
  @Mock private AccountRequestMapper mapper;

  private final String username = "jsmith";
  private final String pii = "123-45-6789";
  private final String bogusName = "bogus";
  private final String firstName = "Joe";
  private final String lastName = "Smith";
  private final String identifier = "12345";
  private final String fullName = "Joe Smith";

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

  /** setup data for each test. */
  @BeforeEach
  public void setup() {

    controller = new AccountController(manager, mapper);

    // use the real mapper to generate consistent objects to use in mapper stubs
    AccountRequestMapper real = Mappers.getMapper(AccountRequestMapper.class);

    request = new AccountRequest(username, pii, firstName, lastName);
    resource = real.toModel(request);
    output =
        new Account(
            identifier,
            resource.getUserName(),
            resource.getPii(),
            resource.getFirstName(),
            resource.getLastName());
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
    createResponseMapperStubs();
    Mockito.when(manager.add(resource)).thenReturn(output);

    ResponseEntity<AccountResponse> response = controller.addEntity(request);

    assertThat(response.getStatusCodeValue()).isEqualTo(201);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getPii()).isEqualTo(pii);
    assertThat(response.getBody().getFullName()).isEqualTo(fullName);
    assertThat(response.getBody().getId()).isEqualTo(identifier);
  }

  @Test
  public void findByIdTest() {

    createOptionalMapperStubs();
    Mockito.when(manager.findById(identifier)).thenReturn(optionalOutput);

    ResponseEntity<AccountResponse> response = controller.findEntityById(identifier);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getPii()).isEqualTo(pii);
    assertThat(response.getBody().getFullName()).isEqualTo(fullName);
    assertThat(response.getBody().getId()).isEqualTo(identifier);
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

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getData().size()).isEqualTo(2);
    // Todo: check contents of the list objects
  }

  @Test
  public void findAllEmptyTest() {

    createEmptyListMapperStubs();
    Mockito.when(manager.findAll()).thenReturn(emptyOutputList);

    ResponseEntity<ArrayResponse<AccountResponse>> response = controller.findEntities();

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getData().size()).isEqualTo(0);
  }

  @Test
  public void updateTest() {

    createMapperStubs();
    createOptionalMapperStubs();
    Mockito.when(manager.updateById(identifier, resource)).thenReturn(optionalOutput);

    ResponseEntity<AccountResponse> response = controller.updateEntityById(identifier, request);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getPii()).isEqualTo(pii);
    assertThat(response.getBody().getFullName()).isEqualTo(fullName);
    assertThat(response.getBody().getId()).isEqualTo(identifier);
  }

  @Test
  public void updateFailedTest() {

    createMapperStubs();
    Mockito.when(manager.updateById(bogusName, resource)).thenReturn(emptyAccount);

    ResponseEntity<AccountResponse> response = controller.updateEntityById(bogusName, request);

    assertThat(response.getStatusCodeValue()).isEqualTo(404);
  }

  @Test
  public void deleteTest() {

    createOptionalMapperStubs();
    Mockito.when(manager.deleteById(identifier)).thenReturn(optionalOutput);

    ResponseEntity<AccountResponse> response = controller.deleteEntityById(identifier);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getPii()).isEqualTo(pii);
    assertThat(response.getBody().getFullName()).isEqualTo(fullName);
    assertThat(response.getBody().getId()).isEqualTo(identifier);
  }

  @Test
  public void deleteFailedTest() {

    Mockito.when(manager.deleteById(bogusName)).thenReturn(emptyAccount);

    ResponseEntity<AccountResponse> response = controller.deleteEntityById(bogusName);

    assertThat(response.getStatusCodeValue()).isEqualTo(404);
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
    Mockito.when(mapper.toAccountResponseList(outputList)).thenReturn(responseList);
  }

  private void createEmptyListMapperStubs() {
    Mockito.when(mapper.toAccountResponseList(emptyOutputList)).thenReturn(emptyResponseList);
  }
}
