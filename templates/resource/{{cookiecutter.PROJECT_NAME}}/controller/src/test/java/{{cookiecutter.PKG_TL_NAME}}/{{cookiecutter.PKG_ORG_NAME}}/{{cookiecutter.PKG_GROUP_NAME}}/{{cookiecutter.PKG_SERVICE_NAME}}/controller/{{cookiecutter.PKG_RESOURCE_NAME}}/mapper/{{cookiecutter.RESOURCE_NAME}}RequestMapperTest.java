package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.controller.{{cookiecutter.PKG_RESOURCE_NAME}}.mapper;

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.requests.{{cookiecutter.RESOURCE_NAME}}Request;
{%- if cookiecutter.CREATE_SUBRESOURCE == "y" %}
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.requests.{{cookiecutter.SUB_RESOURCE_NAME}}Request;
{%- endif %}
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.responses.{{cookiecutter.RESOURCE_NAME}}Response;
{%- if cookiecutter.CREATE_SUBRESOURCE == "y" %}
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.responses.{{cookiecutter.SUB_RESOURCE_NAME}}Response;
{%- endif %}
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.responses.PagedResponse;
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

import static org.assertj.core.api.Assertions.assertThat;

public class {{cookiecutter.RESOURCE_NAME}}RequestMapperTest {

  private {{cookiecutter.RESOURCE_NAME}}RequestMapper mapper;

  private final String username = "jsmith";
  private final String pii = "123-45-6789";
  private final String firstName = "Joe";
  private final String lastName = "Smith";
  private final String identifier = "12345";
  private final String fullName = "Joe Smith";

  @BeforeEach
  public void setup() {
    mapper = Mappers.getMapper({{cookiecutter.RESOURCE_NAME}}RequestMapper.class);
  }

  @Test
  public void mapperNew{{cookiecutter.RESOURCE_NAME}}Test() {
    {{cookiecutter.RESOURCE_NAME}}Request resource = create{{cookiecutter.RESOURCE_NAME}}Request();

    {{cookiecutter.RESOURCE_NAME}} response = mapper.toModel(resource);

    verify{{cookiecutter.RESOURCE_NAME}}(response);
  }

  @Test
  public void mapper{{cookiecutter.RESOURCE_NAME}}ResponseTest() {
    {{cookiecutter.RESOURCE_NAME}} resource = create{{cookiecutter.RESOURCE_NAME}}(identifier);

    {{cookiecutter.RESOURCE_NAME}}Response response = mapper.to{{cookiecutter.RESOURCE_NAME}}Response(resource);

    verify{{cookiecutter.RESOURCE_NAME}}Response(response);
  }

  @Test
  public void mapperOptionalTest() {
    Optional<{{cookiecutter.RESOURCE_NAME}}> resource = Optional.of(create{{cookiecutter.RESOURCE_NAME}}(identifier));

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
    List<{{cookiecutter.RESOURCE_NAME}}> resources = Arrays.asList(create{{cookiecutter.RESOURCE_NAME}}(identifier), create{{cookiecutter.RESOURCE_NAME}}(identifier));

    List<{{cookiecutter.RESOURCE_NAME}}Response> response = mapper.to{{cookiecutter.RESOURCE_NAME}}ResponseList(resources);

    assertThat(response.size()).isEqualTo(2);
    verify{{cookiecutter.RESOURCE_NAME}}Response(response.get(0));
    verify{{cookiecutter.RESOURCE_NAME}}Response(response.get(1));
  }

  @Test
  public void mapperEntityPageTest() {
    Pageable pageable = PageRequest.of(0, 1);
    Page<{{cookiecutter.RESOURCE_NAME}}> resources = new PageImpl<>(Arrays.asList(create{{cookiecutter.RESOURCE_NAME}}(identifier)),
        pageable, 100);
    PagedResponse<{{cookiecutter.RESOURCE_NAME}}Response> response = mapper.to{{cookiecutter.RESOURCE_NAME}}ResponsePage(resources);

    assertThat(response.getItems().size()).isEqualTo(1);
    assertThat(response.getTotalItems()).isEqualTo(100);
    assertThat(response.getPageNumber()).isEqualTo(0);
    assertThat(response.getPageSize()).isEqualTo(1);
    assertThat(response.getTotalPages()).isEqualTo(100);
    verify{{cookiecutter.RESOURCE_NAME}}Response(response.getItems().get(0));
  }


{%- if cookiecutter.CREATE_SUBRESOURCE == "y" %}
  @Test
  public void mapperNew{{cookiecutter.SUB_RESOURCE_NAME}}Test() {
    {{cookiecutter.SUB_RESOURCE_NAME}}Request resource = create{{cookiecutter.SUB_RESOURCE_NAME}}Request();

    {{cookiecutter.SUB_RESOURCE_NAME}} response = mapper.toModel(resource);

    verify{{cookiecutter.SUB_RESOURCE_NAME}}(response);
  }

  @Test
  public void mapper{{cookiecutter.SUB_RESOURCE_NAME}}ResponseTest() {
    {{cookiecutter.SUB_RESOURCE_NAME}} resource = create{{cookiecutter.SUB_RESOURCE_NAME}}(identifier);

    {{cookiecutter.SUB_RESOURCE_NAME}}Response response = mapper.to{{cookiecutter.SUB_RESOURCE_NAME}}Response(resource);

    verify{{cookiecutter.SUB_RESOURCE_NAME}}Response(response);
  }

  @Test
  public void mapper{{cookiecutter.SUB_RESOURCE_NAME}}ResponseTest() {
    {{cookiecutter.SUB_RESOURCE_NAME}} resource = create{{cookiecutter.SUB_RESOURCE_NAME}}(identifier);

    {{cookiecutter.SUB_RESOURCE_NAME}}Response response = mapper.to{{cookiecutter.SUB_RESOURCE_NAME}}Response(resource);

    verify{{cookiecutter.SUB_RESOURCE_NAME}}Response(response);
  }

  @Test
  public void mapperOptionalTest() {
    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> resource = Optional.of(create{{cookiecutter.SUB_RESOURCE_NAME}}(identifier));

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
    List<{{cookiecutter.SUB_RESOURCE_NAME}}> resources = Arrays.asList(create{{cookiecutter.SUB_RESOURCE_NAME}}(identifier), create{{cookiecutter.SUB_RESOURCE_NAME}}(identifier));

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
    return new {{cookiecutter.RESOURCE_NAME}}(id, username, pii, firstName, lastName);
  }

  /**
   * convenience function to create resource request object.
   *
   * @return {{cookiecutter.RESOURCE_NAME}}Request object
   */
  private {{cookiecutter.RESOURCE_NAME}}Request create{{cookiecutter.RESOURCE_NAME}}Request() {
    return new {{cookiecutter.RESOURCE_NAME}}Request(username, pii, firstName, lastName);
  }

{%- if cookiecutter.CREATE_SUBRESOURCE == "y" %}
  /**
   * convenience function to create resource request object.
   *
   * @return {{cookiecutter.RESOURCE_NAME}}Request object
   */
  private {{cookiecutter.SUB_RESOURCE_NAME}}Request create{{cookiecutter.SUB_RESOURCE_NAME}}Request() {
    return new {{cookiecutter.SUB_RESOURCE_NAME}}Request(username, firstName, lastName);
  }
{%- endif %}

  /**
   * helper function to validate standard values.
   *
   * @param resource the object to validate
   */
  protected void verify{{cookiecutter.RESOURCE_NAME}}({{cookiecutter.RESOURCE_NAME}} resource) {
    assertThat(resource.getUserName().equals(username));
    assertThat(resource.getPii().equals(pii));
    assertThat(resource.getFirstName().equals(firstName));
    assertThat(resource.getLastName().equals(lastName));
    assertThat(resource.getId()).isNotEqualTo(identifier);
  }

{%- if cookiecutter.CREATE_SUBRESOURCE == "y" %}
  /**
   * helper function to validate standard values.
   *
   * @param resource the object to validate
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

{%- if cookiecutter.CREATE_SUBRESOURCE == "y" %}
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
