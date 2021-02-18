package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.controller.account;

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.boot.exception.ResourceNotFoundException;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.requests.{{cookiecutter.RESOURCE_NAME}}Request;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.responses.{{cookiecutter.RESOURCE_NAME}}Response;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.responses.ArrayResponse;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.controller.{{cookiecutter.PKG_RESOURCE_NAME}}.mapper.{{cookiecutter.RESOURCE_NAME}}RequestMapper;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.{{cookiecutter.RESOURCE_NAME}}Service;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.RESOURCE_NAME}};
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
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class {{cookiecutter.RESOURCE_NAME}}ControllerTest {

  private {{cookiecutter.RESOURCE_NAME}}Controller controller;

  @Mock private {{cookiecutter.RESOURCE_NAME}}Service manager;
  @Mock private {{cookiecutter.RESOURCE_NAME}}RequestMapper mapper;

  private final String username = "jsmith";
  private final String pii = "123-45-6789";
  private final String bogusName = "bogus";
  private final String firstName = "Joe";
  private final String lastName = "Smith";
  private final String identifier = "12345";
  private final String fullName = "Joe Smith";

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

  /** setup data for each test. */
  @BeforeEach
  public void setup() {

    controller = new {{cookiecutter.RESOURCE_NAME}}Controller(manager, mapper);

    // use the real mapper to generate consistent objects to use in mapper stubs
    {{cookiecutter.RESOURCE_NAME}}RequestMapper real = Mappers.getMapper({{cookiecutter.RESOURCE_NAME}}RequestMapper.class);

    request = new {{cookiecutter.RESOURCE_NAME}}Request(username, pii, firstName, lastName);
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
  }

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

    ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> response = controller.addEntity(request);

    assertThat(response.getStatusCodeValue()).isEqualTo(201);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getPii()).isEqualTo(pii);
    assertThat(response.getBody().getFullName()).isEqualTo(fullName);
    assertThat(response.getBody().getId()).isEqualTo(identifier);
  }

  /*
  @Test
  public void add{{cookiecutter.SUB_RESOURCE_NAME}}Test() {
    // TODO: This all needs work
    createMapperStubs();
    Mockito.when(manager.add(resource)).thenReturn(output);

    ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> response = controller.addSubEntity(request);

    assertThat(response.getBody().getResponse()).isEqualTo("Hello Joe");
    assertThat(response.getBody().getId()).isEqualTo(identifier);
    assertThat(response.getStatusCodeValue()).isEqualTo(201);
  }*/

  /*
  @Test
  public void findByUserNameTest() {

    createOptionalMapperStubs();
    Mockito.when(manager.findByUserName(username)).thenReturn(optionalOutput);

    ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> response = controller.findEntityByUsername(username);

    assertThat(response.getBody().getUserName()).isEqualTo(username);
    assertThat(response.getBody().getId()).isEqualTo(identifier);
    assertThat(response.getStatusCodeValue()).isEqualTo(200);
  }

  @Test
  public void findByUserNameFailedTest() {

    Mockito.when(manager.findByUserName(bogusName)).thenReturn(empty{{cookiecutter.RESOURCE_NAME}});

    ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> response = controller.findEntityByUsername(bogusName);

    assertThat(response.getStatusCodeValue()).isEqualTo(404);
  }*/

  @Test
  public void findByIdTest() throws Exception {

    createResponseMapperStubs();
    Mockito.when(manager.findById(identifier)).thenReturn(optionalOutput);

    ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> response = controller.findEntityById(identifier);

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
          ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> response = controller.findEntityById(bogusName);
        });
  }

  @Test
  public void findAllTest() throws Exception {

    createListMapperStubs();
    Mockito.when(manager.findAll()).thenReturn(outputList);

    ResponseEntity<ArrayResponse<{{cookiecutter.RESOURCE_NAME}}Response>> response = controller.findEntities();

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getData().size()).isEqualTo(2);
    // Todo: check contents of the list objects
  }

  @Test
  public void findAllEmptyTest() throws Exception {

    createEmptyListMapperStubs();
    Mockito.when(manager.findAll()).thenReturn(emptyOutputList);

    ResponseEntity<ArrayResponse<{{cookiecutter.RESOURCE_NAME}}Response>> response = controller.findEntities();

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getData().size()).isEqualTo(0);
  }

  @Test
  public void updateTest() throws Exception {

    createMapperStubs();
    createResponseMapperStubs();
    Mockito.when(manager.updateById(identifier, resource)).thenReturn(optionalOutput);

    ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> response = controller.updateEntityById(identifier, request);

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

    ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> response = controller.deleteEntityById(identifier);

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
          ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> response = controller.deleteEntityById(bogusName);
        });
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
    Mockito.when(mapper.to{{cookiecutter.RESOURCE_NAME}}ResponseList(outputList)).thenReturn(responseList);
  }

  private void createEmptyListMapperStubs() {
    Mockito.when(mapper.to{{cookiecutter.RESOURCE_NAME}}ResponseList(emptyOutputList)).thenReturn(emptyResponseList);
  }

  /** helper function to validate standard values.
   *
   * @param response the object to validate
   */
  protected void verify{{cookiecutter.RESOURCE_NAME}}({{cookiecutter.RESOURCE_NAME}} resource) {
    assertThat(resource.getUserName().equals(username));
    assertThat(resource.getPii().equals(pii));
    assertThat(resource.getFirstName().equals(firstName));
    assertThat(resource.getLastName().equals(lastName));
    assertThat(resource.getId()).isNotEqualTo(identifier);
  }

{% if cookiecutter.CREATE_SUBRESOURCE == "y" %}
  /** helper function to validate standard values.
   *
   * @param response the object to validate
   */
  protected void verify{{cookiecutter.SUB_RESOURCE_NAME}}({{cookiecutter.SUB_RESOURCE_NAME}} resource) {
    assertThat(resource.getUserName().equals(username));
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
    assertThat(response.getUserName().equals(username));
    assertThat(response.getPii().equals(pii));
    assertThat(response.getFullName().equals(fullName));
    assertThat(response.getId()).isEqualTo(identifier);
  }

{% if cookiecutter.CREATE_SUBRESOURCE == "y" %}
  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  protected void verify{{cookiecutter.SUB_RESOURCE_NAME}}Response({{cookiecutter.SUB_RESOURCE_NAME}}Response response) {
    assertThat(response.getResponse().equals(username));
    assertThat(response.getId()).isEqualTo(identifier);
  }
{%- endif %}

}
