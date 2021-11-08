{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.controller.{{cookiecutter.PKG_RESOURCE_NAME}};

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.exception.ResourceNotFoundException;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.notifier.lifecycle.entity.provider.MemoizedTimestampProvider;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.notifier.lifecycle.entity.provider.NoopEntityLifecycleNotifier;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.notifier.lifecycle.entity.spi.EntityLifecycleNotifier;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.test.data.provider.NamedDataFactory;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.responses.PagedResponse;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.responses.{{cookiecutter.SUB_RESOURCE_NAME}}Response;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.requests.{{cookiecutter.SUB_RESOURCE_NAME}}Request;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.controller.{{cookiecutter.PKG_RESOURCE_NAME}}.mapper.{{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}RequestMapper;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}}Data;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.provider.{{cookiecutter.SUB_RESOURCE_NAME}}DataFactory;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.provider.{{cookiecutter.SUB_RESOURCE_NAME}}DataProperties;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.provider.{{cookiecutter.SUB_RESOURCE_NAME}}TestData;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}};
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.{{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}Service;
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
public class {{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}ControllerTest {

  private {{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}Controller controller;

  @Mock private {{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}Service manager;
  @Mock private {{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}RequestMapper mapper;

  private EntityLifecycleNotifier notifier =
      new NoopEntityLifecycleNotifier(new MemoizedTimestampProvider(ZonedDateTime.now()));

  private {{cookiecutter.SUB_RESOURCE_NAME}}TestData subResourceTestDataLoader = new {{cookiecutter.SUB_RESOURCE_NAME}}TestData();
  private {{cookiecutter.SUB_RESOURCE_NAME}}DataFactory subResourceTestData= new {{cookiecutter.SUB_RESOURCE_NAME}}DataFactory(
    subResourceTestDataLoader);

  private {{cookiecutter.SUB_RESOURCE_NAME}}Data subReference;
  private {{cookiecutter.SUB_RESOURCE_NAME}}Data subBogus;

  private final String parentIdentifier = "uuid-parent";
  private final Pageable pageable = Pageable.unpaged();

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

  /** setup data for each test. */
  @BeforeEach
  public void setup() {

    controller = new {{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}Controller(manager, mapper, notifier);

    subReference = subResourceTestData.createBySpec(NamedDataFactory.DEFAULT_SPEC);
    subBogus = subResourceTestData.createBySpec("bogus");

    // use the real mapper to generate consistent objects to use in mapper stubs
    {{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}RequestMapper real = Mappers.getMapper({{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}RequestMapper.class);

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
  }

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

  @Test
  public void findBy{{cookiecutter.SUB_RESOURCE_NAME}}IdFailTest() throws Exception {

    Mockito.when(manager.findById(parentIdentifier, subBogus.getId())).thenReturn(empty{{cookiecutter.SUB_RESOURCE_NAME}});

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> response =
              controller.getSubEntity(parentIdentifier, subBogus.getId());
        });
  }

  @Test
  public void addSubEntityTest() throws Exception {

    create{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs();
    create{{cookiecutter.SUB_RESOURCE_NAME}}ResponseMapperStubs();
    Mockito.when(manager.add(parentIdentifier, subResource)).thenReturn(subOutput);

    ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> response = controller.addSubEntity(parentIdentifier, subRequest);

    assertThat(response.getStatusCodeValue()).isEqualTo(201);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getId()).isEqualTo(subReference.getId());
  }

  @Test
  public void find{{cookiecutter.SUB_RESOURCE_NAME}}ByIdTest() throws Exception {

    create{{cookiecutter.SUB_RESOURCE_NAME}}ResponseMapperStubs();
    Mockito.when(manager.findById(parentIdentifier, subReference.getId())).thenReturn(optionalSubOutput);

    ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> response =
        controller.getSubEntity(parentIdentifier, subReference.getId());

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getId()).isEqualTo(subReference.getId());
  }

  @Test
  public void find{{cookiecutter.SUB_RESOURCE_NAME}}ByIdFailedTest() throws Exception {

    Mockito.when(manager.findById(parentIdentifier, subBogus.getId())).thenReturn(empty{{cookiecutter.SUB_RESOURCE_NAME}});

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> response =
              controller.getSubEntity(parentIdentifier, subBogus.getId());
        });
  }

  @Test
  public void findAll{{cookiecutter.SUB_RESOURCE_NAME}}Test() throws Exception {

    create{{cookiecutter.SUB_RESOURCE_NAME}}ListMapperStubs();
    Mockito.when(manager.findAll(parentIdentifier, pageable)).thenReturn(subOutputPage);

    ResponseEntity<PagedResponse<{{cookiecutter.SUB_RESOURCE_NAME}}Response>> response =
        controller.getSubEntities(parentIdentifier, pageable);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getItems().size()).isEqualTo(2);
    // Todo: check contents of the list objects
  }

  @Test
  public void findAll{{cookiecutter.SUB_RESOURCE_NAME}}EmptyTest() throws Exception {

    createEmpty{{cookiecutter.SUB_RESOURCE_NAME}}ListMapperStubs();
    Mockito.when(manager.findAll(parentIdentifier, pageable)).thenReturn(emptySubOutputPage);

    ResponseEntity<PagedResponse<{{cookiecutter.SUB_RESOURCE_NAME}}Response>> response =
        controller.getSubEntities(parentIdentifier, pageable);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getItems().size()).isEqualTo(0);
  }

  @Test
  public void update{{cookiecutter.SUB_RESOURCE_NAME}}Test() throws Exception {

    create{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs();
    create{{cookiecutter.SUB_RESOURCE_NAME}}ResponseMapperStubs();
    Mockito.when(manager.updateById(parentIdentifier, subReference.getId(), subResource))
        .thenReturn(optionalSubOutput);

    ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> response =
        controller.updateSubEntity(parentIdentifier, subReference.getId(), subRequest);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getId()).isEqualTo(subReference.getId());
  }

  @Test
  public void update{{cookiecutter.SUB_RESOURCE_NAME}}FailedTest() throws Exception {

    create{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs();
    Mockito.when(manager.updateById(parentIdentifier, subBogus.getId(), subResource))
        .thenReturn(empty{{cookiecutter.SUB_RESOURCE_NAME}});

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> response =
              controller.updateSubEntity(parentIdentifier, subBogus.getId(), subRequest);
        });
  }

  @Test
  public void delete{{cookiecutter.SUB_RESOURCE_NAME}}Test() throws Exception {

    create{{cookiecutter.SUB_RESOURCE_NAME}}ResponseMapperStubs();
    Mockito.when(manager.deleteById(parentIdentifier, subReference.getId())).thenReturn(optionalSubOutput);

    ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> response =
        controller.deleteSubEntity(parentIdentifier, subReference.getId());

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getId()).isEqualTo(subReference.getId());
  }

  @Test
  public void delete{{cookiecutter.SUB_RESOURCE_NAME}}FailedTest() throws Exception {

    Mockito.when(manager.deleteById(parentIdentifier, subBogus.getId())).thenReturn(empty{{cookiecutter.SUB_RESOURCE_NAME}});

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> response =
              controller.deleteSubEntity(parentIdentifier, subBogus.getId());
        });
  }

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

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  protected void verify{{cookiecutter.SUB_RESOURCE_NAME}}Response({{cookiecutter.SUB_RESOURCE_NAME}}Response response) {
    assertThat(response.getId()).isEqualTo(subReference.getId());
  }

}
{%- endif %}
