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

  private {{cookiecutter.RESOURCE_NAME}}Data reference;

  /** Setup mapper and test data factory before each test. */
  @BeforeEach
  public void setup() {
    mapper = Mappers.getMapper({{cookiecutter.RESOURCE_NAME}}RequestMapper.class);

    reference = resourceTestData.createBySpec(NamedDataFactory.DEFAULT_SPEC);
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
        // TODO: Additional {{cookiecutter.RESOURCE_NAME}} data elements
        reference.get{{cookiecutter.PARENT_RESOURCE_NAME}}Id()
{%- else %}
        // TODO: Additional {{cookiecutter.RESOURCE_NAME}} data elements
{%- endif %}
    );
  }

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
        // TODO: Additional {{cookiecutter.RESOURCE_NAME}}Request data elements
        reference.get{{cookiecutter.PARENT_RESOURCE_NAME}}Id()
{%- else %}
        // TODO: Additional {{cookiecutter.RESOURCE_NAME}}Request data elements
{%- endif %}
    );
  }

  /**
   * helper function to validate standard values.
   *
   * @param resource the object to validate
   * @param reference what to compare with
   */
  protected void verify{{cookiecutter.RESOURCE_NAME}}({{cookiecutter.RESOURCE_NAME}} resource, {{cookiecutter.RESOURCE_NAME}}Data reference) {
    assertThat(resource.getUserName()).isEqualTo(reference.getUserName());
    assertThat(resource.getPii()).isEqualTo(reference.getPii());
    assertThat(resource.getFirstName()).isEqualTo(reference.getFirstName());
    assertThat(resource.getLastName()).isEqualTo(reference.getLastName());
    // TODO: Add assertions for additional {{cookiecutter.RESOURCE_NAME}} fields
    assertThat(resource.getId()).isNotEqualTo(reference.getId());
{%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %}
    assertThat(resource.get{{cookiecutter.PARENT_RESOURCE_NAME}}Id()).isEqualTo(reference.get{{cookiecutter.PARENT_RESOURCE_NAME}}Id());
{%- endif %}
  }

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   * @param reference what to compare with
   */
  private void verify{{cookiecutter.RESOURCE_NAME}}Response({{cookiecutter.RESOURCE_NAME}}Response response, {{cookiecutter.RESOURCE_NAME}}Data reference) {
    assertThat(response.getUserName()).isEqualTo(reference.getUserName());
    assertThat(response.getPii()).isEqualTo(reference.getPii());
    assertThat(response.getFullName()).isEqualTo(reference.getFullName());
    // TODO: Add assertions for additional {{cookiecutter.RESOURCE_NAME}}Response fields
    assertThat(response.getId()).isEqualTo(reference.getId());
{%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %}
    assertThat(response.get{{cookiecutter.PARENT_RESOURCE_NAME}}Id()).isEqualTo(reference.get{{cookiecutter.PARENT_RESOURCE_NAME}}Id());
{%- endif %}
  }

  /**
   * helper function to validate standard values.
   *
   * @param resource the object to validate
   */
  protected void verify{{cookiecutter.RESOURCE_NAME}}({{cookiecutter.RESOURCE_NAME}} resource) {
    verify{{cookiecutter.RESOURCE_NAME}}(resource, reference);
  }

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  private void verify{{cookiecutter.RESOURCE_NAME}}Response({{cookiecutter.RESOURCE_NAME}}Response response) {
    verify{{cookiecutter.RESOURCE_NAME}}Response(response, reference);
  }

}
