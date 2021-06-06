package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.controller.{{cookiecutter.PKG_RESOURCE_NAME}};

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.exception.ResourceNotFoundException;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.notifier.lifecycle.entity.provider.MemoizedTimestampProvider;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.notifier.lifecycle.entity.provider.NoopEntityLifecycleNotifier;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.notifier.lifecycle.entity.spi.EntityLifecycleNotifier;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.test.data.spi.DataFactory;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.requests.{{cookiecutter.RESOURCE_NAME}}Request;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.responses.{{cookiecutter.RESOURCE_NAME}}Response;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.responses.PagedResponse;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.controller.{{cookiecutter.PKG_RESOURCE_NAME}}.mapper.{{cookiecutter.RESOURCE_NAME}}RequestMapper;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.RESOURCE_NAME}}Data;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_RESOURCE_NAME}}.provider.{{cookiecutter.RESOURCE_NAME}}DataFactory;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_RESOURCE_NAME}}.provider.{{cookiecutter.RESOURCE_NAME}}DataProperties;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_RESOURCE_NAME}}.provider.{{cookiecutter.RESOURCE_NAME}}TestData;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.{{cookiecutter.RESOURCE_NAME}}Service;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.RESOURCE_NAME}};
{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.responses.{{cookiecutter.SUB_RESOURCE_NAME}}Response;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.requests.{{cookiecutter.SUB_RESOURCE_NAME}}Request;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}}Data;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.provider.{{cookiecutter.SUB_RESOURCE_NAME}}DataFactory;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.provider.{{cookiecutter.SUB_RESOURCE_NAME}}DataProperties;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.provider.{{cookiecutter.SUB_RESOURCE_NAME}}TestData;
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

@Slf4j
@ExtendWith(MockitoExtension.class)
public class {{cookiecutter.RESOURCE_NAME}}ControllerTest {

  private {{cookiecutter.RESOURCE_NAME}}Controller controller;

  @Mock private {{cookiecutter.RESOURCE_NAME}}Service manager;
  @Mock private {{cookiecutter.RESOURCE_NAME}}RequestMapper mapper;

  private EntityLifecycleNotifier notifier =
      new NoopEntityLifecycleNotifier(new MemoizedTimestampProvider(ZonedDateTime.now()));

  private {{cookiecutter.RESOURCE_NAME}}TestData resourceTestDataLoader = new {{cookiecutter.RESOURCE_NAME}}TestData();
  private {{cookiecutter.RESOURCE_NAME}}DataFactory resourceTestData = new {{cookiecutter.RESOURCE_NAME}}DataFactory(resourceTestDataLoader);
{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
  private {{cookiecutter.SUB_RESOURCE_NAME}}TestData subResourceTestDataLoader = new {{cookiecutter.SUB_RESOURCE_NAME}}TestData();
  private {{cookiecutter.SUB_RESOURCE_NAME}}DataFactory subResourceTestData= new {{cookiecutter.SUB_RESOURCE_NAME}}DataFactory(
    subResourceTestDataLoader);
{%- endif %}

  private {{cookiecutter.RESOURCE_NAME}}Data reference;
  private {{cookiecutter.RESOURCE_NAME}}Data bogus;
{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
  private {{cookiecutter.SUB_RESOURCE_NAME}}Data subReference;
  private {{cookiecutter.SUB_RESOURCE_NAME}}Data subBogus;
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

    reference = resourceTestData.getNamedData(DataFactory.DEFAULT_NAME);
    bogus = resourceTestData.getNamedData("bogus");
{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
    subReference = subResourceTestData.getNamedData(DataFactory.DEFAULT_NAME);
    subBogus = subResourceTestData.getNamedData("bogus");
{%- endif %}

    // use the real mapper to generate consistent objects to use in mapper stubs
    {{cookiecutter.RESOURCE_NAME}}RequestMapper real = Mappers.getMapper({{cookiecutter.RESOURCE_NAME}}RequestMapper.class);

    request = new {{cookiecutter.RESOURCE_NAME}}Request(reference.getUserName(),
        reference.getPii(),
        reference.getFirstName(),
        reference.getLastName());
    resource = real.toModel(request);
    output =
        new {{cookiecutter.RESOURCE_NAME}}(
            reference.getId(),
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

    subRequest = new {{cookiecutter.SUB_RESOURCE_NAME}}Request(subReference.getUserName(),
        subReference.getFirstName(),
        subReference.getLastName());
    subResource = real.toModel(subRequest);
    subOutput =
        new {{cookiecutter.SUB_RESOURCE_NAME}}(
            subReference.getId(),
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

    Mockito.when(manager.findById(bogus.getId())).thenReturn(empty{{cookiecutter.RESOURCE_NAME}});

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> response = controller.findEntityById(bogus.getId());
        });
  }

  @Test
  public void add{{cookiecutter.RESOURCE_NAME}}Test() throws Exception {

    createMapperStubs();
    createResponseMapperStubs();
    Mockito.when(manager.add(resource)).thenReturn(output);

    ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> response = controller.addEntity(request);

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

    ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> response = controller.findEntityById(reference.getId());

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getPii()).isEqualTo(reference.getPii());
    assertThat(response.getBody().getFullName()).isEqualTo(reference.getFullName());
    assertThat(response.getBody().getId()).isEqualTo(reference.getId());
  }

  @Test
  public void findByIdFailedTest() throws Exception {

    Mockito.when(manager.findById(bogus.getId())).thenReturn(empty{{cookiecutter.RESOURCE_NAME}});

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> response = controller.findEntityById(bogus.getId());
        });
  }

  @Test
  public void findAllTest() throws Exception {

    createListMapperStubs();
    Mockito.when(manager.findAll(pageable)).thenReturn(outputPage);

    ResponseEntity<PagedResponse<{{cookiecutter.RESOURCE_NAME}}Response>> response = controller.findEntities(pageable);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getItems().size()).isEqualTo(2);
    // Todo: check contents of the list objects
  }

  @Test
  public void findAllEmptyTest() throws Exception {

    createEmptyListMapperStubs();
    Mockito.when(manager.findAll(pageable)).thenReturn(emptyOutputPage);

    ResponseEntity<PagedResponse<{{cookiecutter.RESOURCE_NAME}}Response>> response = controller.findEntities(pageable);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getItems().size()).isEqualTo(0);
  }

  @Test
  public void updateTest() throws Exception {

    createMapperStubs();
    createResponseMapperStubs();
    Mockito.when(manager.updateById(reference.getId(), resource)).thenReturn(optionalOutput);

    ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> response = controller.updateEntityById(reference.getId(), request);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getPii()).isEqualTo(reference.getPii());
    assertThat(response.getBody().getFullName()).isEqualTo(reference.getFullName());
    assertThat(response.getBody().getId()).isEqualTo(reference.getId());
  }

  @Test
  public void updateFailedTest() throws Exception {

    createMapperStubs();
    Mockito.when(manager.updateById(bogus.getId(), resource)).thenReturn(empty{{cookiecutter.RESOURCE_NAME}});

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> response =
              controller.updateEntityById(bogus.getId(), request);
        });
  }

  @Test
  public void deleteTest() throws Exception {

    createResponseMapperStubs();
    Mockito.when(manager.deleteById(reference.getId())).thenReturn(optionalOutput);

    ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> response = controller.deleteEntityById(reference.getId());

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getPii()).isEqualTo(reference.getPii());
    assertThat(response.getBody().getFullName()).isEqualTo(reference.getFullName());
    assertThat(response.getBody().getId()).isEqualTo(reference.getId());
  }

  @Test
  public void deleteFailedTest() throws Exception {

    Mockito.when(manager.deleteById(bogus.getId())).thenReturn(empty{{cookiecutter.RESOURCE_NAME}});

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> response = controller.deleteEntityById(bogus.getId());
        });
  }

{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}

  @Test
  public void findBy{{cookiecutter.SUB_RESOURCE_NAME}}IdFailTest() throws Exception {

    Mockito.when(manager.get{{cookiecutter.SUB_RESOURCE_NAME}}(reference.getId(), subBogus.getId())).thenReturn(empty{{cookiecutter.SUB_RESOURCE_NAME}});

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> response =
              controller.get{{cookiecutter.SUB_RESOURCE_NAME}}(reference.getId(), subBogus.getId());
        });
  }

  @Test
  public void add{{cookiecutter.SUB_RESOURCE_NAME}}Test() throws Exception {

    create{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs();
    create{{cookiecutter.SUB_RESOURCE_NAME}}ResponseMapperStubs();
    Mockito.when(manager.add{{cookiecutter.SUB_RESOURCE_NAME}}(reference.getId(), subResource)).thenReturn(subOutput);

    ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> response = controller.add{{cookiecutter.SUB_RESOURCE_NAME}}(reference.getId(), subRequest);

    assertThat(response.getStatusCodeValue()).isEqualTo(201);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getId()).isEqualTo(subReference.getId());
  }

  @Test
  public void find{{cookiecutter.SUB_RESOURCE_NAME}}ByIdTest() throws Exception {

    create{{cookiecutter.SUB_RESOURCE_NAME}}ResponseMapperStubs();
    Mockito.when(manager.get{{cookiecutter.SUB_RESOURCE_NAME}}(reference.getId(), subReference.getId())).thenReturn(optionalSubOutput);

    ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> response =
        controller.get{{cookiecutter.SUB_RESOURCE_NAME}}(reference.getId(), subReference.getId());

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getId()).isEqualTo(subReference.getId());
  }

  @Test
  public void find{{cookiecutter.SUB_RESOURCE_NAME}}ByIdFailedTest() throws Exception {

    Mockito.when(manager.get{{cookiecutter.SUB_RESOURCE_NAME}}(reference.getId(), subBogus.getId())).thenReturn(empty{{cookiecutter.SUB_RESOURCE_NAME}});

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> response =
              controller.get{{cookiecutter.SUB_RESOURCE_NAME}}(reference.getId(), subBogus.getId());
        });
  }

  @Test
  public void findAll{{cookiecutter.SUB_RESOURCE_NAME}}Test() throws Exception {

    create{{cookiecutter.SUB_RESOURCE_NAME}}ListMapperStubs();
    Mockito.when(manager.get{{cookiecutter.SUB_RESOURCE_NAME}}s(reference.getId(), pageable)).thenReturn(subOutputPage);

    ResponseEntity<PagedResponse<{{cookiecutter.SUB_RESOURCE_NAME}}Response>> response =
        controller.get{{cookiecutter.SUB_RESOURCE_NAME}}s(reference.getId(), pageable);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getItems().size()).isEqualTo(2);
    // Todo: check contents of the list objects
  }

  @Test
  public void findAll{{cookiecutter.SUB_RESOURCE_NAME}}EmptyTest() throws Exception {

    createEmpty{{cookiecutter.SUB_RESOURCE_NAME}}ListMapperStubs();
    Mockito.when(manager.get{{cookiecutter.SUB_RESOURCE_NAME}}s(reference.getId(), pageable)).thenReturn(emptySubOutputPage);

    ResponseEntity<PagedResponse<{{cookiecutter.SUB_RESOURCE_NAME}}Response>> response =
        controller.get{{cookiecutter.SUB_RESOURCE_NAME}}s(reference.getId(), pageable);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getItems().size()).isEqualTo(0);
  }

  @Test
  public void update{{cookiecutter.SUB_RESOURCE_NAME}}Test() throws Exception {

    create{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs();
    create{{cookiecutter.SUB_RESOURCE_NAME}}ResponseMapperStubs();
    Mockito.when(manager.update{{cookiecutter.SUB_RESOURCE_NAME}}(reference.getId(), subReference.getId(), subResource))
        .thenReturn(optionalSubOutput);

    ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> response =
        controller.update{{cookiecutter.SUB_RESOURCE_NAME}}(reference.getId(), subReference.getId(), subRequest);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getId()).isEqualTo(subReference.getId());
  }

  @Test
  public void update{{cookiecutter.SUB_RESOURCE_NAME}}FailedTest() throws Exception {

    create{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs();
    Mockito.when(manager.update{{cookiecutter.SUB_RESOURCE_NAME}}(reference.getId(), subBogus.getId(), subResource))
        .thenReturn(empty{{cookiecutter.SUB_RESOURCE_NAME}});

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> response =
              controller.update{{cookiecutter.SUB_RESOURCE_NAME}}(reference.getId(), subBogus.getId(), subRequest);
        });
  }

  @Test
  public void delete{{cookiecutter.SUB_RESOURCE_NAME}}Test() throws Exception {

    create{{cookiecutter.SUB_RESOURCE_NAME}}ResponseMapperStubs();
    Mockito.when(manager.delete{{cookiecutter.SUB_RESOURCE_NAME}}(reference.getId(), subReference.getId())).thenReturn(optionalSubOutput);

    ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> response =
        controller.delete{{cookiecutter.SUB_RESOURCE_NAME}}(reference.getId(), subReference.getId());

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getId()).isEqualTo(subReference.getId());
  }

  @Test
  public void delete{{cookiecutter.SUB_RESOURCE_NAME}}FailedTest() throws Exception {

    Mockito.when(manager.delete{{cookiecutter.SUB_RESOURCE_NAME}}(reference.getId(), subBogus.getId())).thenReturn(empty{{cookiecutter.SUB_RESOURCE_NAME}});

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> response =
              controller.delete{{cookiecutter.SUB_RESOURCE_NAME}}(reference.getId(), subBogus.getId());
        });
  }

{%- endif %}

  /**
   * helper function to validate standard values.
   *
   * @param resource the object to validate
   */
  protected void verify{{cookiecutter.RESOURCE_NAME}}({{cookiecutter.RESOURCE_NAME}} resource) {
    assertThat(resource.getUserName().equals(reference.getUserName()));
    assertThat(resource.getPii().equals(reference.getPii()));
    assertThat(resource.getFirstName().equals(reference.getFirstName()));
    assertThat(resource.getLastName().equals(reference.getLastName()));
    assertThat(resource.getId()).isNotEqualTo(reference.getId());
  }

{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}

  /**
   * helper function to validate standard values.
   *
   * @param resource the object to validate
   */
  protected void verify{{cookiecutter.SUB_RESOURCE_NAME}}({{cookiecutter.SUB_RESOURCE_NAME}} resource) {
    assertThat(resource.getUserName().equals(subReference.getUserName()));
    assertThat(resource.getFirstName().equals(subReference.getFirstName()));
    assertThat(resource.getLastName().equals(subReference.getLastName()));
    assertThat(resource.getId()).isNotEqualTo(subReference.getId());
  }
{%- endif %}

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  private void verify{{cookiecutter.RESOURCE_NAME}}Response({{cookiecutter.RESOURCE_NAME}}Response response) {
    assertThat(response.getUserName().equals(reference.getUserName()));
    assertThat(response.getPii().equals(reference.getPii()));
    assertThat(response.getFirstName().equals(reference.getFirstName()));
    assertThat(response.getFullName().equals(reference.getFullName()));
    assertThat(response.getId()).isEqualTo(reference.getId());
  }

{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  protected void verify{{cookiecutter.SUB_RESOURCE_NAME}}Response({{cookiecutter.SUB_RESOURCE_NAME}}Response response) {
    assertThat(response.getId()).isEqualTo(subReference.getId());
  }
{%- endif %}
}
