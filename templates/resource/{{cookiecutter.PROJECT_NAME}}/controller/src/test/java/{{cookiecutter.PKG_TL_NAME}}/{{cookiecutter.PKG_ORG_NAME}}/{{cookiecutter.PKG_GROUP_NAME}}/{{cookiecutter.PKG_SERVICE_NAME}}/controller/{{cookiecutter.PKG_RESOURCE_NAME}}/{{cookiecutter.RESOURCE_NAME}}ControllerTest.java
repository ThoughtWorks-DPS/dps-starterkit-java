package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.controller.{{cookiecutter.PKG_RESOURCE_NAME}};

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.exception.ResourceNotFoundException;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.notifier.EntityLifecycleNotifier;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.notifier.MemoizedTimestampProvider;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.notifier.NoopEntityLifecycleNotifier;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.requests.{{cookiecutter.RESOURCE_NAME}}Request;
{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.requests.{{cookiecutter.SUB_RESOURCE_NAME}}Request;
{%- endif %}
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.responses.{{cookiecutter.RESOURCE_NAME}}Response;
{%-if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.responses.{{cookiecutter.SUB_RESOURCE_NAME}}Response;
{%- endif %}
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.responses.PagedResponse;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.controller.{{cookiecutter.PKG_RESOURCE_NAME}}.mapper.{{cookiecutter.RESOURCE_NAME}}RequestMapper;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.{{cookiecutter.RESOURCE_NAME}}Service;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.RESOURCE_NAME}};
{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}};
{%- endif %}
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class {{cookiecutter.RESOURCE_NAME}}ControllerTest {

  private {{cookiecutter.RESOURCE_NAME}}Controller controller;

  @Mock private {{cookiecutter.RESOURCE_NAME}}Service manager;
  @Mock private {{cookiecutter.RESOURCE_NAME}}RequestMapper mapper;
  private EntityLifecycleNotifier notifier =
      new NoopEntityLifecycleNotifier(new MemoizedTimestampProvider(ZonedDateTime.now()));

  private final String userName = "jsmith";
  private final String pii = "123-45-6789";
  private final String bogusName = "bogus";
  private final String firstName = "Joe";
  private final String lastName = "Smith";
  private final String identifier = "12345";
  private final String fullName = "Joe Smith";
{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
  private final String subIdentifier = "abcde";
{%- endif %}

  private {{cookiecutter.RESOURCE_NAME}} resource;
  private {{cookiecutter.RESOURCE_NAME}} output;
  private {{cookiecutter.RESOURCE_NAME}}Request request;
  private {{cookiecutter.RESOURCE_NAME}}Response response;
  private Optional<{{cookiecutter.RESOURCE_NAME}}> empty{{cookiecutter.RESOURCE_NAME}} = Optional.empty();
  private Optional<{{cookiecutter.RESOURCE_NAME}}Response> emptyResponse = Optional.empty();
  private Optional<{{cookiecutter.RESOURCE_NAME}}Response> optionalResponse;
  private Optional<{{cookiecutter.RESOURCE_NAME}}> optionalOutput;
  private List<{{cookiecutter.RESOURCE_NAME}}Response> responseList;
  private List<{{cookiecutter.RESOURCE_NAME}}> outputList;
  private List<{{cookiecutter.RESOURCE_NAME}}Response> emptyResponseList = Arrays.asList();
  private List<{{cookiecutter.RESOURCE_NAME}}> emptyOutputList = Arrays.asList();
  private PagedResponse<{{cookiecutter.RESOURCE_NAME}}Response> responsePage;
  private PagedResponse<{{cookiecutter.RESOURCE_NAME}}Response> emptyResponsePage;
  private Page<{{cookiecutter.RESOURCE_NAME}}> outputPage;
  private Page<{{cookiecutter.RESOURCE_NAME}}> emptyOutputPage;
  private Pageable pageable = Pageable.unpaged();

{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}

  private {{cookiecutter.SUB_RESOURCE_NAME}} subResource;
  private {{cookiecutter.SUB_RESOURCE_NAME}} subOutput;
  private {{cookiecutter.SUB_RESOURCE_NAME}}Request subRequest;
  private {{cookiecutter.SUB_RESOURCE_NAME}}Response subResponse;
  private Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> empty{{cookiecutter.SUB_RESOURCE_NAME}} = Optional.empty();
  private Optional<{{cookiecutter.SUB_RESOURCE_NAME}}Response> emptySubResponse = Optional.empty();
  private Optional<{{cookiecutter.SUB_RESOURCE_NAME}}Response> optionalSubResponse;
  private Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> optionalSubOutput;
  private List<{{cookiecutter.SUB_RESOURCE_NAME}}Response> subResponseList;
  private List<{{cookiecutter.SUB_RESOURCE_NAME}}> subOutputList;
  private List<{{cookiecutter.SUB_RESOURCE_NAME}}Response> emptySubResponseList = Arrays.asList();
  private List<{{cookiecutter.SUB_RESOURCE_NAME}}> emptySubOutputList = Arrays.asList();
  private PagedResponse<{{cookiecutter.SUB_RESOURCE_NAME}}Response> subResponsePage;
  private PagedResponse<{{cookiecutter.SUB_RESOURCE_NAME}}Response> emptySubResponsePage;
  private Page<{{cookiecutter.SUB_RESOURCE_NAME}}> subOutputPage;
  private Page<{{cookiecutter.SUB_RESOURCE_NAME}}> emptySubOutputPage;
{%- endif %}

  /** setup data for each test. */
  @BeforeEach
  public void setup() {

    controller = new {{cookiecutter.RESOURCE_NAME}}Controller(manager, mapper, notifier);

    // use the real mapper to generate consistent objects to use in mapper stubs
    {{cookiecutter.RESOURCE_NAME}}RequestMapper real = Mappers.getMapper({{cookiecutter.RESOURCE_NAME}}RequestMapper.class);

    request = new {{cookiecutter.RESOURCE_NAME}}Request(userName, pii, firstName, lastName);
    resource = real.toModel(request);
    output =
        new {{cookiecutter.RESOURCE_NAME}}(
            identifier,
            resource.getUserName(),
            resource.getPii(),
            resource.getFirstName(),
            resource.getLastName());
    response = real.to{{cookiecutter.RESOURCE_NAME}}Response(output);
    optionalResponse = Optional.of(response);
    optionalOutput = Optional.of(output);
    responseList = Arrays.asList(response, response);
    outputList = Arrays.asList(output, output);
    responsePage = new PagedResponse<>(responseList, 10, (long) 100, 1, 10);
    emptyResponsePage = new PagedResponse<>(emptyResponseList, 0, (long) 0, 0, 0);
    outputPage = new PageImpl<>(outputList);
    emptyOutputPage = new PageImpl<>(emptyOutputList);

{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}

    subRequest = new {{cookiecutter.SUB_RESOURCE_NAME}}Request(userName, firstName, lastName);
    subResource = real.toModel(subRequest);
    subOutput =
        new {{cookiecutter.SUB_RESOURCE_NAME}}(
            subIdentifier,
            subResource.getUserName(),
            subResource.getFirstName(),
            subResource.getLastName());
    subResponse = real.to{{cookiecutter.SUB_RESOURCE_NAME}}Response(subOutput);
    optionalSubResponse = Optional.of(subResponse);
    optionalSubOutput = Optional.of(subOutput);
    subResponseList = Arrays.asList(subResponse, subResponse);
    subOutputList = Arrays.asList(subOutput, subOutput);
    subResponsePage = new PagedResponse<>(subResponseList, 10, (long) 100, 1, 10);
    emptySubResponsePage = new PagedResponse<>(emptySubResponseList, 0, (long) 0, 0, 0);
    subOutputPage = new PageImpl<>(subOutputList);
    emptySubOutputPage = new PageImpl<>(emptySubOutputList);
{%- endif %}
  }

  private void createMapperStubs() {
    Mockito.when(mapper.toModel(request)).thenReturn(resource);
  }

  private void createResponseMapperStubs() {
    Mockito.when(mapper.to{{cookiecutter.RESOURCE_NAME}}Response(output)).thenReturn(response);
  }

  private void createOptionalMapperStubs() {
    Mockito.when(mapper.to{{cookiecutter.RESOURCE_NAME}}Response(optionalOutput)).thenReturn(response);
  }

  private void createListMapperStubs() {
    Mockito.when(mapper.to{{cookiecutter.RESOURCE_NAME}}ResponsePage(outputPage)).thenReturn(responsePage);
  }

  private void createEmptyListMapperStubs() {
    Mockito.when(mapper.to{{cookiecutter.RESOURCE_NAME}}ResponsePage(emptyOutputPage)).thenReturn(emptyResponsePage);
  }

{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}

  private void create{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs() {
    Mockito.when(mapper.toModel(subRequest)).thenReturn(subResource);
  }

  private void create{{cookiecutter.SUB_RESOURCE_NAME}}ResponseMapperStubs() {
    Mockito.when(mapper.to{{cookiecutter.SUB_RESOURCE_NAME}}Response(subOutput)).thenReturn(subResponse);
  }

  private void createOptional{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs() {
    Mockito.when(mapper.to{{cookiecutter.SUB_RESOURCE_NAME}}Response(optionalSubOutput)).thenReturn(subResponse);
  }

  private void create{{cookiecutter.SUB_RESOURCE_NAME}}ListMapperStubs() {
    Mockito.when(mapper.to{{cookiecutter.SUB_RESOURCE_NAME}}ResponsePage(subOutputPage)).thenReturn(subResponsePage);
  }

  private void createEmpty{{cookiecutter.SUB_RESOURCE_NAME}}ListMapperStubs() {
    Mockito.when(mapper.to{{cookiecutter.SUB_RESOURCE_NAME}}ResponsePage(emptySubOutputPage))
        .thenReturn(emptySubResponsePage);
  }
{%- endif %}

  @Test
  public void findBy{{cookiecutter.RESOURCE_NAME}}IdFailTest() throws Exception {

    Mockito.when(manager.findById(bogusName)).thenReturn(empty{{cookiecutter.RESOURCE_NAME}});

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> response = controller.findEntityById(bogusName);
        });
  }

  @Test
  public void add{{cookiecutter.RESOURCE_NAME}}Test() throws Exception {

    createMapperStubs();
    createResponseMapperStubs();
    Mockito.when(manager.add(resource)).thenReturn(output);

    ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> response =
        controller.addEntity(request);

    assertThat(response.getStatusCodeValue()).isEqualTo(201);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getPii()).isEqualTo(pii);
    assertThat(response.getBody().getFullName()).isEqualTo(fullName);
    assertThat(response.getBody().getId()).isEqualTo(identifier);
  }

  @Test
  public void findByIdTest() throws Exception {

    createResponseMapperStubs();
    Mockito.when(manager.findById(identifier)).thenReturn(optionalOutput);

    ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> response =
        controller.findEntityById(identifier);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getPii()).isEqualTo(pii);
    assertThat(response.getBody().getFullName()).isEqualTo(fullName);
    assertThat(response.getBody().getId()).isEqualTo(identifier);
  }

  @Test
  public void findByIdFailedTest() throws Exception {

    Mockito.when(manager.findById(bogusName)).thenReturn(empty{{cookiecutter.RESOURCE_NAME}});

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> response =
              controller.findEntityById(bogusName);
        });
  }

  @Test
  public void findAllTest() throws Exception {

    createListMapperStubs();
    Mockito.when(manager.findAll(pageable)).thenReturn(outputPage);

    ResponseEntity<PagedResponse<{{cookiecutter.RESOURCE_NAME}}Response>> response =
        controller.findEntities(pageable);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getItems().size()).isEqualTo(2);
    // Todo: check contents of the list objects
  }

  @Test
  public void findAllEmptyTest() throws Exception {

    createEmptyListMapperStubs();
    Mockito.when(manager.findAll(pageable)).thenReturn(emptyOutputPage);

    ResponseEntity<PagedResponse<{{cookiecutter.RESOURCE_NAME}}Response>> response =
        controller.findEntities(pageable);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getItems().size()).isEqualTo(0);
  }

  @Test
  public void updateTest() throws Exception {

    createMapperStubs();
    createResponseMapperStubs();
    Mockito.when(manager.updateById(identifier, resource)).thenReturn(optionalOutput);

    ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> response =
        controller.updateEntityById(identifier, request);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getPii()).isEqualTo(pii);
    assertThat(response.getBody().getFullName()).isEqualTo(fullName);
    assertThat(response.getBody().getId()).isEqualTo(identifier);
  }

  @Test
  public void updateFailedTest() throws Exception {

    createMapperStubs();
    Mockito.when(manager.updateById(bogusName, resource)).thenReturn(empty{{cookiecutter.RESOURCE_NAME}});

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> response =
              controller.updateEntityById(bogusName, request);
        });
  }

  @Test
  public void deleteTest() throws Exception {

    createResponseMapperStubs();
    Mockito.when(manager.deleteById(identifier)).thenReturn(optionalOutput);

    ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> response =
        controller.deleteEntityById(identifier);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getPii()).isEqualTo(pii);
    assertThat(response.getBody().getFullName()).isEqualTo(fullName);
    assertThat(response.getBody().getId()).isEqualTo(identifier);
  }

  @Test
  public void deleteFailedTest() throws Exception {

    Mockito.when(manager.deleteById(bogusName)).thenReturn(empty{{cookiecutter.RESOURCE_NAME}});

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> response =
              controller.deleteEntityById(bogusName);
        });
  }

{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}

  @Test
  public void findBy{{cookiecutter.SUB_RESOURCE_NAME}}IdFailTest() throws Exception {

    Mockito.when(manager.get{{cookiecutter.SUB_RESOURCE_NAME}}(identifier, bogusName))
        .thenReturn(empty{{cookiecutter.SUB_RESOURCE_NAME}});

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> response =
              controller.get{{cookiecutter.SUB_RESOURCE_NAME}}(identifier, bogusName);
        });
  }

  @Test
  public void add{{cookiecutter.SUB_RESOURCE_NAME}}Test() throws Exception {

    create{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs();
    create{{cookiecutter.SUB_RESOURCE_NAME}}ResponseMapperStubs();
    Mockito.when(manager.add{{cookiecutter.SUB_RESOURCE_NAME}}(identifier, subResource))
        .thenReturn(subOutput);

    ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> response =
        controller.add{{cookiecutter.SUB_RESOURCE_NAME}}(identifier, subRequest);

    assertThat(response.getStatusCodeValue()).isEqualTo(201);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getId()).isEqualTo(subIdentifier);
  }

  @Test
  public void find{{cookiecutter.SUB_RESOURCE_NAME}}ByIdTest() throws Exception {

    create{{cookiecutter.SUB_RESOURCE_NAME}}ResponseMapperStubs();
    Mockito.when(manager.get{{cookiecutter.SUB_RESOURCE_NAME}}(identifier, subIdentifier))
        .thenReturn(optionalSubOutput);

    ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> response =
        controller.get{{cookiecutter.SUB_RESOURCE_NAME}}(identifier, subIdentifier);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getId()).isEqualTo(subIdentifier);
  }

  @Test
  public void find{{cookiecutter.SUB_RESOURCE_NAME}}ByIdFailedTest() throws Exception {

    Mockito.when(manager.get{{cookiecutter.SUB_RESOURCE_NAME}}(identifier, bogusName))
        .thenReturn(empty{{cookiecutter.SUB_RESOURCE_NAME}});

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> response =
              controller.get{{cookiecutter.SUB_RESOURCE_NAME}}(identifier, bogusName);
        });
  }

  @Test
  public void findAll{{cookiecutter.SUB_RESOURCE_NAME}}Test() throws Exception {

    create{{cookiecutter.SUB_RESOURCE_NAME}}ListMapperStubs();
    Mockito.when(manager.get{{cookiecutter.SUB_RESOURCE_NAME}}s(identifier, pageable))
        .thenReturn(subOutputPage);

    ResponseEntity<PagedResponse<{{cookiecutter.SUB_RESOURCE_NAME}}Response>> response =
        controller.get{{cookiecutter.SUB_RESOURCE_NAME}}s(identifier, pageable);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getItems().size()).isEqualTo(2);
    // Todo: check contents of the list objects
  }

  @Test
  public void findAll{{cookiecutter.SUB_RESOURCE_NAME}}EmptyTest() throws Exception {

    createEmpty{{cookiecutter.SUB_RESOURCE_NAME}}ListMapperStubs();
    Mockito.when(manager.get{{cookiecutter.SUB_RESOURCE_NAME}}s(identifier, pageable))
        .thenReturn(emptySubOutputPage);

    ResponseEntity<PagedResponse<{{cookiecutter.SUB_RESOURCE_NAME}}Response>> response =
        controller.get{{cookiecutter.SUB_RESOURCE_NAME}}s(identifier, pageable);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getItems().size()).isEqualTo(0);
  }

  @Test
  public void update{{cookiecutter.SUB_RESOURCE_NAME}}Test() throws Exception {

    create{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs();
    create{{cookiecutter.SUB_RESOURCE_NAME}}ResponseMapperStubs();
    Mockito.when(manager.update{{cookiecutter.SUB_RESOURCE_NAME}}(identifier, subIdentifier, subResource))
        .thenReturn(optionalSubOutput);

    ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> response =
        controller.update{{cookiecutter.SUB_RESOURCE_NAME}}(identifier, subIdentifier, subRequest);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getId()).isEqualTo(subIdentifier);
  }

  @Test
  public void update{{cookiecutter.SUB_RESOURCE_NAME}}FailedTest() throws Exception {

    create{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs();
    Mockito.when(manager.update{{cookiecutter.SUB_RESOURCE_NAME}}(identifier, bogusName, subResource))
        .thenReturn(empty{{cookiecutter.SUB_RESOURCE_NAME}});

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> response =
              controller.update{{cookiecutter.SUB_RESOURCE_NAME}}(identifier, bogusName, subRequest);
        });
  }

  @Test
  public void delete{{cookiecutter.SUB_RESOURCE_NAME}}Test() throws Exception {

    create{{cookiecutter.SUB_RESOURCE_NAME}}ResponseMapperStubs();
    Mockito.when(manager.delete{{cookiecutter.SUB_RESOURCE_NAME}}(identifier, subIdentifier))
        .thenReturn(optionalSubOutput);

    ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> response =
        controller.delete{{cookiecutter.SUB_RESOURCE_NAME}}(identifier, subIdentifier);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getId()).isEqualTo(subIdentifier);
  }

  @Test
  public void delete{{cookiecutter.SUB_RESOURCE_NAME}}FailedTest() throws Exception {

    Mockito.when(manager.delete{{cookiecutter.SUB_RESOURCE_NAME}}(identifier, bogusName))
        .thenReturn(empty{{cookiecutter.SUB_RESOURCE_NAME}});

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> response =
              controller.delete{{cookiecutter.SUB_RESOURCE_NAME}}(identifier, bogusName);
        });
  }

{%- endif %}

  /**
   * helper function to validate standard values.
   *
   * @param resource the object to validate
   */
  protected void verify{{cookiecutter.RESOURCE_NAME}}({{cookiecutter.RESOURCE_NAME}} resource) {
    assertThat(resource.getUserName().equals(userName));
    assertThat(resource.getPii().equals(pii));
    assertThat(resource.getFirstName().equals(firstName));
    assertThat(resource.getLastName().equals(lastName));
    assertThat(resource.getId()).isNotEqualTo(identifier);
  }

{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}

  /**
   * helper function to validate standard values.
   *
   * @param resource the object to validate
   */
  protected void verify{{cookiecutter.SUB_RESOURCE_NAME}}({{cookiecutter.SUB_RESOURCE_NAME}} resource) {
    assertThat(resource.getUserName().equals(userName));
    assertThat(resource.getFirstName().equals(firstName));
    assertThat(resource.getLastName().equals(lastName));
    assertThat(resource.getId()).isNotEqualTo(identifier);
  }
{%- endif %}

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  private void verify{{cookiecutter.RESOURCE_NAME}}Response({{cookiecutter.RESOURCE_NAME}}Response response) {
    assertThat(response.getUserName().equals(userName));
    assertThat(response.getPii().equals(pii));
    assertThat(response.getFullName().equals(fullName));
    assertThat(response.getId()).isEqualTo(identifier);
  }

{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  protected void verify{{cookiecutter.SUB_RESOURCE_NAME}}Response({{cookiecutter.SUB_RESOURCE_NAME}}Response response) {
    assertThat(response.getId()).isEqualTo(identifier);
  }
{%- endif %}
}
