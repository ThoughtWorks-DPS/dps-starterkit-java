package io.twdps.starter.example.controller.subaccount;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.twdps.starter.boot.exception.ResourceNotFoundException;
import io.twdps.starter.boot.notifier.lifecycle.entity.provider.MemoizedTimestampProvider;
import io.twdps.starter.boot.notifier.lifecycle.entity.provider.NoopEntityLifecycleNotifier;
import io.twdps.starter.boot.notifier.lifecycle.entity.spi.EntityLifecycleNotifier;
import io.twdps.starter.boot.test.data.provider.NamedDataFactory;
import io.twdps.starter.example.api.responses.PagedResponse;
import io.twdps.starter.example.api.subaccount.requests.SubAccountRequest;
import io.twdps.starter.example.api.subaccount.responses.SubAccountResponse;
import io.twdps.starter.example.controller.subaccount.mapper.SubAccountRequestMapper;
import io.twdps.starter.example.data.subaccount.model.SubAccountData;
import io.twdps.starter.example.data.subaccount.provider.SubAccountDataFactory;
import io.twdps.starter.example.data.subaccount.provider.SubAccountTestData;
import io.twdps.starter.example.service.spi.subaccount.SubAccountService;
import io.twdps.starter.example.service.spi.subaccount.model.SubAccount;
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
public class SubAccountControllerTest {

  private SubAccountController controller;

  @Mock private SubAccountService manager;
  @Mock private SubAccountRequestMapper mapper;

  private EntityLifecycleNotifier notifier =
      new NoopEntityLifecycleNotifier(new MemoizedTimestampProvider(ZonedDateTime.now()));

  private SubAccountTestData resourceTestDataLoader = new SubAccountTestData();
  private SubAccountDataFactory resourceTestData =
      new SubAccountDataFactory(resourceTestDataLoader);

  private SubAccountData reference;
  private SubAccountData bogus;

  private SubAccount resource;
  private SubAccount output;
  private SubAccountRequest request;
  private SubAccountResponse response;
  private Optional<SubAccount> emptySubAccount = Optional.empty();
  private Optional<SubAccountResponse> emptyResponse = Optional.empty();
  private Optional<SubAccountResponse> optionalResponse;
  private Optional<SubAccount> optionalOutput;
  private List<SubAccountResponse> responseList;
  private List<SubAccount> outputList;
  private List<SubAccountResponse> emptyResponseList = Arrays.asList();
  private List<SubAccount> emptyOutputList = Arrays.asList();
  private PagedResponse<SubAccountResponse> responsePage;
  private PagedResponse<SubAccountResponse> emptyResponsePage;
  private Page<SubAccount> outputPage;
  private Page<SubAccount> emptyOutputPage;
  private Pageable pageable = Pageable.unpaged();

  /** setup data for each test. */
  @BeforeEach
  public void setup() {

    controller = new SubAccountController(manager, mapper, notifier);

    reference = resourceTestData.createBySpec(NamedDataFactory.DEFAULT_SPEC);
    bogus = resourceTestData.createBySpec("bogus");

    // use the real mapper to generate consistent objects to use in mapper stubs
    SubAccountRequestMapper real = Mappers.getMapper(SubAccountRequestMapper.class);

    request =
        new SubAccountRequest(
            reference.getUserName(),
            reference.getPii(),
            reference.getFirstName(),
            reference.getLastName());
    resource = real.toModel(request);
    output =
        new SubAccount(
            reference.getId(),
            resource.getUserName(),
            resource.getPii(),
            resource.getFirstName(),
            resource.getLastName());
    response = real.toSubAccountResponse(output);
    optionalResponse = Optional.of(response);
    optionalOutput = Optional.of(output);
    responseList = Arrays.asList(response, response);
    outputList = Arrays.asList(output, output);
    responsePage = new PagedResponse<>(responseList, 10, (long) 100, 1, 10);
    emptyResponsePage = new PagedResponse<>(emptyResponseList, 0, (long) 0, 0, 0);
    outputPage = new PageImpl<>(outputList);
    emptyOutputPage = new PageImpl<>(emptyOutputList);
  }

  private void createMapperStubs() {
    Mockito.when(mapper.toModel(request)).thenReturn(resource);
  }

  private void createResponseMapperStubs() {
    Mockito.when(mapper.toSubAccountResponse(output)).thenReturn(response);
  }

  private void createOptionalMapperStubs() {
    Mockito.when(mapper.toSubAccountResponse(optionalOutput)).thenReturn(response);
  }

  private void createListMapperStubs() {
    Mockito.when(mapper.toSubAccountResponsePage(outputPage)).thenReturn(responsePage);
  }

  private void createEmptyListMapperStubs() {
    Mockito.when(mapper.toSubAccountResponsePage(emptyOutputPage)).thenReturn(emptyResponsePage);
  }

  @Test
  public void findBySubAccountIdFailTest() throws Exception {

    Mockito.when(manager.findById(bogus.getId())).thenReturn(emptySubAccount);

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<SubAccountResponse> response = controller.findEntityById(bogus.getId());
        });
  }

  @Test
  public void addSubAccountTest() throws Exception {

    createMapperStubs();
    createResponseMapperStubs();
    Mockito.when(manager.add(resource)).thenReturn(output);

    ResponseEntity<SubAccountResponse> response = controller.addEntity(request);

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

    ResponseEntity<SubAccountResponse> response = controller.findEntityById(reference.getId());

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getPii()).isEqualTo(reference.getPii());
    assertThat(response.getBody().getFullName()).isEqualTo(reference.getFullName());
    assertThat(response.getBody().getId()).isEqualTo(reference.getId());
  }

  @Test
  public void findByIdFailedTest() throws Exception {

    Mockito.when(manager.findById(bogus.getId())).thenReturn(emptySubAccount);

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<SubAccountResponse> response = controller.findEntityById(bogus.getId());
        });
  }

  @Test
  public void findAllTest() throws Exception {

    createListMapperStubs();
    Mockito.when(manager.findAll(pageable)).thenReturn(outputPage);

    ResponseEntity<PagedResponse<SubAccountResponse>> response = controller.findEntities(pageable);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getItems().size()).isEqualTo(2);
    // Todo: check contents of the list objects
  }

  @Test
  public void findAllEmptyTest() throws Exception {

    createEmptyListMapperStubs();
    Mockito.when(manager.findAll(pageable)).thenReturn(emptyOutputPage);

    ResponseEntity<PagedResponse<SubAccountResponse>> response = controller.findEntities(pageable);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getItems().size()).isEqualTo(0);
  }

  @Test
  public void updateTest() throws Exception {

    createMapperStubs();
    createResponseMapperStubs();
    Mockito.when(manager.updateById(reference.getId(), resource)).thenReturn(optionalOutput);

    ResponseEntity<SubAccountResponse> response =
        controller.updateEntityById(reference.getId(), request);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getPii()).isEqualTo(reference.getPii());
    assertThat(response.getBody().getFullName()).isEqualTo(reference.getFullName());
    assertThat(response.getBody().getId()).isEqualTo(reference.getId());
  }

  @Test
  public void updateFailedTest() throws Exception {

    createMapperStubs();
    Mockito.when(manager.updateById(bogus.getId(), resource)).thenReturn(emptySubAccount);

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<SubAccountResponse> response =
              controller.updateEntityById(bogus.getId(), request);
        });
  }

  @Test
  public void deleteTest() throws Exception {

    createResponseMapperStubs();
    Mockito.when(manager.deleteById(reference.getId())).thenReturn(optionalOutput);

    ResponseEntity<SubAccountResponse> response = controller.deleteEntityById(reference.getId());

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getPii()).isEqualTo(reference.getPii());
    assertThat(response.getBody().getFullName()).isEqualTo(reference.getFullName());
    assertThat(response.getBody().getId()).isEqualTo(reference.getId());
  }

  @Test
  public void deleteFailedTest() throws Exception {

    Mockito.when(manager.deleteById(bogus.getId())).thenReturn(emptySubAccount);

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<SubAccountResponse> response = controller.deleteEntityById(bogus.getId());
        });
  }

  /**
   * helper function to validate standard values.
   *
   * @param resource the object to validate
   */
  protected void verifySubAccount(SubAccount resource) {
    assertThat(resource.getUserName().equals(reference.getUserName()));
    assertThat(resource.getPii().equals(reference.getPii()));
    assertThat(resource.getFirstName().equals(reference.getFirstName()));
    assertThat(resource.getLastName().equals(reference.getLastName()));
    assertThat(resource.getId()).isNotEqualTo(reference.getId());
  }

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  private void verifySubAccountResponse(SubAccountResponse response) {
    assertThat(response.getUserName().equals(reference.getUserName()));
    assertThat(response.getPii().equals(reference.getPii()));
    assertThat(response.getFirstName().equals(reference.getFirstName()));
    assertThat(response.getFullName().equals(reference.getFullName()));
    assertThat(response.getId()).isEqualTo(reference.getId());
  }
}
