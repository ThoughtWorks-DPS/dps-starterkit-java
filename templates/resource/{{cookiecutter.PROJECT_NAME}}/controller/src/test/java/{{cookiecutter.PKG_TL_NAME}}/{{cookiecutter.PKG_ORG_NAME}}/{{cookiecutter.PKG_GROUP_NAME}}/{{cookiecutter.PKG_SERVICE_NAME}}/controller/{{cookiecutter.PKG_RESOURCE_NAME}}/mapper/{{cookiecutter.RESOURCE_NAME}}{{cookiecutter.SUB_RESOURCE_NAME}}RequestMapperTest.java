{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.controller.{{cookiecutter.PKG_RESOURCE_NAME}}.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.test.data.provider.NamedDataFactory;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.responses.PagedResponse;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.requests.{{cookiecutter.SUB_RESOURCE_NAME}}Request;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.responses.{{cookiecutter.SUB_RESOURCE_NAME}}Response;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}}Data;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.provider.{{cookiecutter.SUB_RESOURCE_NAME}}DataFactory;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.provider.{{cookiecutter.SUB_RESOURCE_NAME}}DataProperties;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.provider.{{cookiecutter.SUB_RESOURCE_NAME}}TestData;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}};
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

public class {{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}RequestMapperTest {

  private {{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}RequestMapper mapper;

  private {{cookiecutter.SUB_RESOURCE_NAME}}TestData resourceTestDataLoader = new {{cookiecutter.SUB_RESOURCE_NAME}}TestData();
  private {{cookiecutter.SUB_RESOURCE_NAME}}DataFactory resourceTestData= new {{cookiecutter.SUB_RESOURCE_NAME}}DataFactory(
      resourceTestDataLoader);

  private {{cookiecutter.SUB_RESOURCE_NAME}}Data reference;

  /** Setup mapper and test data factory before each test. */
  @BeforeEach
  public void setup() {
    mapper = Mappers.getMapper({{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}RequestMapper.class);

    reference = resourceTestData.createBySpec(NamedDataFactory.DEFAULT_SPEC);
  }

  @Test
  public void mapperNew{{cookiecutter.SUB_RESOURCE_NAME}}Test() {
    {{cookiecutter.SUB_RESOURCE_NAME}}Request request = create{{cookiecutter.SUB_RESOURCE_NAME}}Request();

    {{cookiecutter.SUB_RESOURCE_NAME}} resource = mapper.toModel(request);

    verify{{cookiecutter.SUB_RESOURCE_NAME}}(resource);
  }

  @Test
  public void mapper{{cookiecutter.SUB_RESOURCE_NAME}}ResponseTest() {
    {{cookiecutter.SUB_RESOURCE_NAME}} resource = create{{cookiecutter.SUB_RESOURCE_NAME}}(reference.getId());

    {{cookiecutter.SUB_RESOURCE_NAME}}Response response = mapper.to{{cookiecutter.SUB_RESOURCE_NAME}}Response(resource);

    verify{{cookiecutter.SUB_RESOURCE_NAME}}Response(response);
  }

  @Test
  public void mapperOptionalTest() {
    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> resource = Optional.of(create{{cookiecutter.SUB_RESOURCE_NAME}}(reference.getId()));

    {{cookiecutter.SUB_RESOURCE_NAME}}Response response = mapper.to{{cookiecutter.SUB_RESOURCE_NAME}}Response(resource);

    assertThat(response).isNotNull();
    verify{{cookiecutter.SUB_RESOURCE_NAME}}Response(response);
  }

  @Test
  public void mapperOptionalNullTest() {
    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> resource = Optional.ofNullable(null);

    {{cookiecutter.SUB_RESOURCE_NAME}}Response response = mapper.to{{cookiecutter.SUB_RESOURCE_NAME}}Response(resource);

    assertThat(response).isNull();
  }

  @Test
  public void mapperOptionalEmptyTest() {
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

  @Test
  public void mapperSubEntityPageTest() {
    Pageable pageable = PageRequest.of(0, 1);
    Page<{{cookiecutter.SUB_RESOURCE_NAME}}> resources =
        new PageImpl<>(Arrays.asList(create{{cookiecutter.SUB_RESOURCE_NAME}}(reference.getId())), pageable, 100);
    PagedResponse<{{cookiecutter.SUB_RESOURCE_NAME}}Response> response = mapper.to{{cookiecutter.SUB_RESOURCE_NAME}}ResponsePage(resources);

    assertThat(response.getItems().size()).isEqualTo(1);
    assertThat(response.getTotalItems()).isEqualTo(100);
    assertThat(response.getPageNumber()).isEqualTo(0);
    assertThat(response.getPageSize()).isEqualTo(1);
    assertThat(response.getTotalPages()).isEqualTo(100);
    verify{{cookiecutter.SUB_RESOURCE_NAME}}Response(response.getItems().get(0));
  }

  /**
   * convenience function to create subresource object.
   *
   * @param id whether to create with identifier (null if not)
   * @return {{cookiecutter.SUB_RESOURCE_NAME}} object
   */
  private {{cookiecutter.SUB_RESOURCE_NAME}} create{{cookiecutter.SUB_RESOURCE_NAME}}(String id) {
    return new {{cookiecutter.SUB_RESOURCE_NAME}}(id,
        reference.getUserName(),
        reference.getFirstName(),
        reference.getLastName()
        // TODO: Additional {{cookiecutter.SUB_RESOURCE_NAME}} data elements
        );
  }

  /**
   * convenience function to create subresource request object.
   *
   * @return {{cookiecutter.SUB_RESOURCE_NAME}}Request object
   */
  private {{cookiecutter.SUB_RESOURCE_NAME}}Request create{{cookiecutter.SUB_RESOURCE_NAME}}Request() {
    return new {{cookiecutter.SUB_RESOURCE_NAME}}Request(reference.getUserName(),
        reference.getFirstName(),
        reference.getLastName()
        // TODO: Additional {{cookiecutter.SUB_RESOURCE_NAME}}Request data elements
        );
  }

  /**
   * helper function to validate standard values.
   *
   * @param resource the object to validate
   * @param reference what to compare with
   */
  private void verify{{cookiecutter.SUB_RESOURCE_NAME}}({{cookiecutter.SUB_RESOURCE_NAME}} resource, {{cookiecutter.SUB_RESOURCE_NAME}}Data reference) {
    assertThat(resource.getUserName()).isEqualTo(reference.getUserName());
    assertThat(resource.getFirstName()).isEqualTo(reference.getFirstName());
    assertThat(resource.getLastName()).isEqualTo(reference.getLastName());
    // TODO: Add assertions for additional {{cookiecutter.SUB_RESOURCE_NAME}} fields
    assertThat(resource.getId()).isNotEqualTo(reference.getId());
  }

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   * @param reference what to compare with
   */
  private void verify{{cookiecutter.SUB_RESOURCE_NAME}}Response({{cookiecutter.SUB_RESOURCE_NAME}}Response response, {{cookiecutter.SUB_RESOURCE_NAME}}Data reference) {
    assertThat(response.getId()).isEqualTo(reference.getId());
    assertThat(response.getUserName()).isEqualTo(reference.getUserName());
    assertThat(response.getFirstName()).isEqualTo(reference.getFirstName());
    assertThat(response.getLastName()).isEqualTo(reference.getLastName());
    // TODO: Add assertions for additional {{cookiecutter.SUB_RESOURCE_NAME}}Response fields
  }

  /**
   * helper function to validate standard values.
   *
   * @param resource the object to validate
   */
  private void verify{{cookiecutter.SUB_RESOURCE_NAME}}({{cookiecutter.SUB_RESOURCE_NAME}} resource) {
    verify{{cookiecutter.SUB_RESOURCE_NAME}}(resource, reference);
  }

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  private void verify{{cookiecutter.SUB_RESOURCE_NAME}}Response({{cookiecutter.SUB_RESOURCE_NAME}}Response response) {
    verify{{cookiecutter.SUB_RESOURCE_NAME}}Response(response, reference);
  }
}
{%- endif %}
