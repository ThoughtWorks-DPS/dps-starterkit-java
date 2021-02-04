package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.controller.{{cookiecutter.PKG_RESOURCE_NAME}}.mapper;

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.requests.{{cookiecutter.RESOURCE_NAME}}Request;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.responses.{{cookiecutter.RESOURCE_NAME}}Response;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.responses.Add{{cookiecutter.RESOURCE_NAME}}Response;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.RESOURCE_NAME}};
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class {{cookiecutter.RESOURCE_NAME}}RequestMapperTest {

  private {{cookiecutter.RESOURCE_NAME}}RequestMapper mapper;

  private final String username = "jsmith";
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
    {{cookiecutter.RESOURCE_NAME}}Request resource = createAdd{{cookiecutter.RESOURCE_NAME}}Request();

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
  public void mapperAdd{{cookiecutter.RESOURCE_NAME}}ResponseTest() {
    {{cookiecutter.RESOURCE_NAME}} resource = create{{cookiecutter.RESOURCE_NAME}}(identifier);

    Add{{cookiecutter.RESOURCE_NAME}}Response response = mapper.toAdd{{cookiecutter.RESOURCE_NAME}}Response(resource);

    verifyAdd{{cookiecutter.RESOURCE_NAME}}Response(response);
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

    List<{{cookiecutter.RESOURCE_NAME}}Response> response = mapper.toResponseList(resources);

    assertThat(response.size()).isEqualTo(2);
    verify{{cookiecutter.RESOURCE_NAME}}Response(response.get(0));
    verify{{cookiecutter.RESOURCE_NAME}}Response(response.get(1));
  }

  /**
   * convenience function to create resource object.
   *
   * @param id whether to create with identifier (null if not)
   * @return {{cookiecutter.RESOURCE_NAME}} object
   */
  private {{cookiecutter.RESOURCE_NAME}} create{{cookiecutter.RESOURCE_NAME}}(String id) {
    return new {{cookiecutter.RESOURCE_NAME}}(id, username, firstName, lastName);
  }

  /**
   * convenience function to create resource request object.
   *
   * @return Add{{cookiecutter.RESOURCE_NAME}}Request object
   */
  private {{cookiecutter.RESOURCE_NAME}}Request createAdd{{cookiecutter.RESOURCE_NAME}}Request() {
    return new {{cookiecutter.RESOURCE_NAME}}Request(username, firstName, lastName);
  }

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  protected void verify{{cookiecutter.RESOURCE_NAME}}({{cookiecutter.RESOURCE_NAME}} response) {
    assertThat(response.getUserName().equals(username));
    assertThat(response.getFirstName().equals(firstName));
    assertThat(response.getLastName().equals(lastName));
    assertThat(response.getId()).isNotEqualTo(identifier);
  }

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  protected void verifyAdd{{cookiecutter.RESOURCE_NAME}}Response(Add{{cookiecutter.RESOURCE_NAME}}Response response) {
    assertThat(response.getResponse().equals(username));
    assertThat(response.getId()).isEqualTo(identifier);
  }

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  private void verify{{cookiecutter.RESOURCE_NAME}}Response({{cookiecutter.RESOURCE_NAME}}Response response) {
    assertThat(response.getUserName().equals(username));
    assertThat(response.getFullName().equals(fullName));
    assertThat(response.getId()).isEqualTo(identifier);
  }
}
