package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.controller.{{cookiecutter.PKG_RESOURCE_NAME}}.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.test.data.provider.NamedDataFactory;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.requests.{{cookiecutter.RESOURCE_NAME}}Request;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.responses.{{cookiecutter.RESOURCE_NAME}}Response;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.responses.PagedResponse;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.RESOURCE_NAME}}Data;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_RESOURCE_NAME}}.provider.{{cookiecutter.RESOURCE_NAME}}DataFactory;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_RESOURCE_NAME}}.provider.{{cookiecutter.RESOURCE_NAME}}DataProperties;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_RESOURCE_NAME}}.provider.{{cookiecutter.RESOURCE_NAME}}TestData;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.RESOURCE_NAME}};
{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.requests.{{cookiecutter.SUB_RESOURCE_NAME}}Request;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.responses.{{cookiecutter.SUB_RESOURCE_NAME}}Response;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}}Data;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.provider.{{cookiecutter.SUB_RESOURCE_NAME}}DataFactory;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.provider.{{cookiecutter.SUB_RESOURCE_NAME}}DataProperties;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.provider.{{cookiecutter.SUB_RESOURCE_NAME}}TestData;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}};
{%- endif %}
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class {{cookiecutter.RESOURCE_NAME}}RequestMapperTest {

  private {{cookiecutter.RESOURCE_NAME}}RequestMapper mapper;

  private {{cookiecutter.RESOURCE_NAME}}TestData resourceTestDataLoader = new {{cookiecutter.RESOURCE_NAME}}TestData();
  private {{cookiecutter.RESOURCE_NAME}}DataFactory resourceTestData = new {{cookiecutter.RESOURCE_NAME}}DataFactory(resourceTestDataLoader);
{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
  private {{cookiecutter.SUB_RESOURCE_NAME}}TestData subResourceTestDataLoader = new {{cookiecutter.SUB_RESOURCE_NAME}}TestData();
  private {{cookiecutter.SUB_RESOURCE_NAME}}DataFactory subResourceTestData= new {{cookiecutter.SUB_RESOURCE_NAME}}DataFactory(
      subResourceTestDataLoader);
{%- endif %}

  private {{cookiecutter.RESOURCE_NAME}}Data reference;
{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
  private {{cookiecutter.SUB_RESOURCE_NAME}}Data subReference;
{%- endif %}

  /** Setup mapper and test data factory before each test. */
  @BeforeEach
  public void setup() {
    mapper = Mappers.getMapper({{cookiecutter.RESOURCE_NAME}}RequestMapper.class);

    reference = resourceTestData.createBySpec(NamedDataFactory.DEFAULT_SPEC);
{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
    subReference = subResourceTestData.createBySpec(NamedDataFactory.DEFAULT_SPEC);
{%- endif %}
  }

  @Test
  public void mapperNew{{cookiecutter.RESOURCE_NAME}}Test() {
    {{cookiecutter.RESOURCE_NAME}}Request resource = create{{cookiecutter.RESOURCE_NAME}}Request();

    {{cookiecutter.RESOURCE_NAME}} response = mapper.toModel(resource);

    verify{{cookiecutter.RESOURCE_NAME}}(response);
  }

  @Test
  public void mapper{{cookiecutter.RESOURCE_NAME}}ResponseTest() {
    {{cookiecutter.RESOURCE_NAME}} resource = create{{cookiecutter.RESOURCE_NAME}}(reference.getId());

    {{cookiecutter.RESOURCE_NAME}}Response response = mapper.to{{cookiecutter.RESOURCE_NAME}}Response(resource);

    verify{{cookiecutter.RESOURCE_NAME}}Response(response);
  }

  @Test
  public void mapperOptionalTest() {
    Optional<{{cookiecutter.RESOURCE_NAME}}> resource = Optional.of(create{{cookiecutter.RESOURCE_NAME}}(reference.getId()));

    {{cookiecutter.RESOURCE_NAME}}Response response = mapper.to{{cookiecutter.RESOURCE_NAME}}Response(resource);

    assertThat(response).isNotNull();
    verify{{cookiecutter.RESOURCE_NAME}}Response(response);
  }

  @Test
  public void mapperOptionalNullTest() {
    Optional<{{cookiecutter.RESOURCE_NAME}}> resource = Optional.ofNullable(null);

    {{cookiecutter.RESOURCE_NAME}}Response response = mapper.to{{cookiecutter.RESOURCE_NAME}}Response(resource);

    assertThat(response).isNull();
  }

  @Test
  public void mapperOptionalEmptyTest() {
    Optional<{{cookiecutter.RESOURCE_NAME}}> resource = Optional.empty();

    {{cookiecutter.RESOURCE_NAME}}Response response = mapper.to{{cookiecutter.RESOURCE_NAME}}Response(resource);

    assertThat(response).isNull();
  }

  @Test
  public void mapperEntityListTest() {
    List<{{cookiecutter.RESOURCE_NAME}}> resources = Arrays.asList(create{{cookiecutter.RESOURCE_NAME}}(reference.getId()), create{{cookiecutter.RESOURCE_NAME}}(reference.getId()));

    List<{{cookiecutter.RESOURCE_NAME}}Response> response = mapper.to{{cookiecutter.RESOURCE_NAME}}ResponseList(resources);

    assertThat(response.size()).isEqualTo(2);
    verify{{cookiecutter.RESOURCE_NAME}}Response(response.get(0));
    verify{{cookiecutter.RESOURCE_NAME}}Response(response.get(1));
  }

  @Test
  public void mapperEntityPageTest() {
    Pageable pageable = PageRequest.of(0, 1);
    Page<{{cookiecutter.RESOURCE_NAME}}> resources =
        new PageImpl<>(Arrays.asList(create{{cookiecutter.RESOURCE_NAME}}(reference.getId())), pageable, 100);
    PagedResponse<{{cookiecutter.RESOURCE_NAME}}Response> response = mapper.to{{cookiecutter.RESOURCE_NAME}}ResponsePage(resources);

    assertThat(response.getItems().size()).isEqualTo(1);
    assertThat(response.getTotalItems()).isEqualTo(100);
    assertThat(response.getPageNumber()).isEqualTo(0);
    assertThat(response.getPageSize()).isEqualTo(1);
    assertThat(response.getTotalPages()).isEqualTo(100);
    verify{{cookiecutter.RESOURCE_NAME}}Response(response.getItems().get(0));
  }

{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}

  @Test
  public void mapperNew{{cookiecutter.SUB_RESOURCE_NAME}}Test() {
    {{cookiecutter.SUB_RESOURCE_NAME}}Request resource = create{{cookiecutter.SUB_RESOURCE_NAME}}Request();

    {{cookiecutter.SUB_RESOURCE_NAME}} response = mapper.toModel(resource);

    verify{{cookiecutter.SUB_RESOURCE_NAME}}(response);
  }

  @Test
  public void mapper{{cookiecutter.SUB_RESOURCE_NAME}}ResponseTest() {
    {{cookiecutter.SUB_RESOURCE_NAME}} resource = create{{cookiecutter.SUB_RESOURCE_NAME}}(reference.getId());

    {{cookiecutter.SUB_RESOURCE_NAME}}Response response = mapper.to{{cookiecutter.SUB_RESOURCE_NAME}}Response(resource);

    verify{{cookiecutter.SUB_RESOURCE_NAME}}Response(response);
  }

  @Test
  public void mapperOptional{{cookiecutter.SUB_RESOURCE_NAME}}Test() {
    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> resource = Optional.of(create{{cookiecutter.SUB_RESOURCE_NAME}}(reference.getId()));

    {{cookiecutter.SUB_RESOURCE_NAME}}Response response = mapper.to{{cookiecutter.SUB_RESOURCE_NAME}}Response(resource);

    assertThat(response).isNotNull();
    verify{{cookiecutter.SUB_RESOURCE_NAME}}Response(response);
  }

  @Test
  public void mapperOptional{{cookiecutter.SUB_RESOURCE_NAME}}NullTest() {
    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> resource = Optional.ofNullable(null);

    {{cookiecutter.SUB_RESOURCE_NAME}}Response response = mapper.to{{cookiecutter.SUB_RESOURCE_NAME}}Response(resource);

    assertThat(response).isNull();
  }

  @Test
  public void mapperOptional{{cookiecutter.SUB_RESOURCE_NAME}}EmptyTest() {
    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> resource = Optional.empty();

    {{cookiecutter.SUB_RESOURCE_NAME}}Response response = mapper.to{{cookiecutter.SUB_RESOURCE_NAME}}Response(resource);

    assertThat(response).isNull();
  }

  @Test
  public void mapperSubEntityListTest() {
    List<{{cookiecutter.SUB_RESOURCE_NAME}}> resources =
        Arrays.asList(create{{cookiecutter.SUB_RESOURCE_NAME}}(reference.getId()), create{{cookiecutter.SUB_RESOURCE_NAME}}(reference.getId()));

    List<{{cookiecutter.SUB_RESOURCE_NAME}}Response> response = mapper.to{{cookiecutter.SUB_RESOURCE_NAME}}ResponseList(resources);

    assertThat(response.size()).isEqualTo(2);
    verify{{cookiecutter.SUB_RESOURCE_NAME}}Response(response.get(0));
    verify{{cookiecutter.SUB_RESOURCE_NAME}}Response(response.get(1));
  }
{%- endif %}

  /**
   * convenience function to create resource object.
   *
   * @param id whether to create with identifier (null if not)
   * @return {{cookiecutter.RESOURCE_NAME}} object
   */
  private {{cookiecutter.RESOURCE_NAME}} create{{cookiecutter.RESOURCE_NAME}}(String id) {
    return new {{cookiecutter.RESOURCE_NAME}}(id,
        reference.getUserName(),
        reference.getPii(),
        reference.getFirstName(),
        reference.getLastName()
{%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %},
        reference.get{{cookiecutter.PARENT_RESOURCE_NAME}}Id()
{%- endif %});
  }

{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}

  /**
   * convenience function to create subresource object.
   *
   * @param id whether to create with identifier (null if not)
   * @return {{cookiecutter.SUB_RESOURCE_NAME}} object
   */
  private {{cookiecutter.SUB_RESOURCE_NAME}} create{{cookiecutter.SUB_RESOURCE_NAME}}(String id) {
    return new {{cookiecutter.SUB_RESOURCE_NAME}}(id,
        subReference.getUserName(),
        subReference.getFirstName(),
        subReference.getLastName());
  }
{%- endif %}

  /**
   * convenience function to create resource request object.
   *
   * @return {{cookiecutter.RESOURCE_NAME}}Request object
   */
  private {{cookiecutter.RESOURCE_NAME}}Request create{{cookiecutter.RESOURCE_NAME}}Request() {
    return new {{cookiecutter.RESOURCE_NAME}}Request(reference.getUserName(),
        reference.getPii(),
        reference.getFirstName(),
        reference.getLastName()
{%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %},
        reference.get{{cookiecutter.PARENT_RESOURCE_NAME}}Id()
{%- endif %});
  }

{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}

  /**
   * convenience function to create subresource request object.
   *
   * @return {{cookiecutter.SUB_RESOURCE_NAME}}Request object
   */
  private {{cookiecutter.SUB_RESOURCE_NAME}}Request create{{cookiecutter.SUB_RESOURCE_NAME}}Request() {
    return new {{cookiecutter.SUB_RESOURCE_NAME}}Request(subReference.getUserName(),
        subReference.getFirstName(),
        subReference.getLastName());
  }
{%- endif %}

  /**
   * helper function to validate standard values.
   *
   * @param resource the object to validate
   */
  protected void verify{{cookiecutter.RESOURCE_NAME}}({{cookiecutter.RESOURCE_NAME}} resource) {
    assertThat(resource.getUserName()).isEqualTo(reference.getUserName());
    assertThat(resource.getPii()).isEqualTo(reference.getPii());
    assertThat(resource.getFirstName()).isEqualTo(reference.getFirstName());
    assertThat(resource.getLastName()).isEqualTo(reference.getLastName());
    assertThat(resource.getId()).isNotEqualTo(reference.getId());
{%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %}
    assertThat(resource.get{{cookiecutter.PARENT_RESOURCE_NAME}}Id()).isEqualTo(reference.get{{cookiecutter.PARENT_RESOURCE_NAME}}Id());
{%- endif %}
  }

{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}

  /**
   * helper function to validate standard values.
   *
   * @param resource the object to validate
   */
  protected void verify{{cookiecutter.SUB_RESOURCE_NAME}}({{cookiecutter.SUB_RESOURCE_NAME}} resource) {
    assertThat(resource.getUserName()).isEqualTo(subReference.getUserName());
    assertThat(resource.getFirstName()).isEqualTo(subReference.getFirstName());
    assertThat(resource.getLastName()).isEqualTo(subReference.getLastName());
    assertThat(resource.getId()).isNotEqualTo(subReference.getId());
  }
{%- endif %}

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  private void verify{{cookiecutter.RESOURCE_NAME}}Response({{cookiecutter.RESOURCE_NAME}}Response response) {
    assertThat(response.getUserName()).isEqualTo(reference.getUserName());
    assertThat(response.getPii()).isEqualTo(reference.getPii());
    assertThat(response.getFullName()).isEqualTo(reference.getFullName());
    assertThat(response.getId()).isEqualTo(reference.getId());
{%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %}
    assertThat(response.get{{cookiecutter.PARENT_RESOURCE_NAME}}Id()).isEqualTo(reference.get{{cookiecutter.PARENT_RESOURCE_NAME}}Id());
{%- endif %}
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
