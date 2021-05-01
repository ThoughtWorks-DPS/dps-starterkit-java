package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.provider.{{cookiecutter.PKG_RESOURCE_NAME}}.mapper;

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.persistence.model.{{cookiecutter.RESOURCE_NAME}}Entity;
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

public class {{cookiecutter.RESOURCE_NAME}}EntityMapperTest {

  private {{cookiecutter.RESOURCE_NAME}}EntityMapper mapper;

  private final String username = "jsmith";
  private final String pii = "123-45-6789";
  private final String firstName = "Joe";
  private final String lastName = "Smith";
  private final String identifier = "12345";
{%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %}
  private final String parentIdentifier = "abcde";
{%- endif %}

  @BeforeEach
  public void setup() {
    mapper = Mappers.getMapper({{cookiecutter.RESOURCE_NAME}}EntityMapper.class);
  }

  @Test
  public void mapperNew{{cookiecutter.RESOURCE_NAME}}Test() {
    {{cookiecutter.RESOURCE_NAME}} resource = create{{cookiecutter.RESOURCE_NAME}}(null);

    {{cookiecutter.RESOURCE_NAME}}Entity response = mapper.toEntity(resource);

    verify{{cookiecutter.RESOURCE_NAME}}Entity(response, false
    {%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %}, false{%- endif -%}
    );
  }

  @Test
  public void mapper{{cookiecutter.RESOURCE_NAME}}Test() {
    {{cookiecutter.RESOURCE_NAME}} resource = create{{cookiecutter.RESOURCE_NAME}}(identifier);

    {{cookiecutter.RESOURCE_NAME}}Entity response = mapper.toEntity(resource);

    verify{{cookiecutter.RESOURCE_NAME}}Entity(response
    {%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %}, true, false{%- endif -%}
    );
  }

  @Test
  public void mapperEntityTest() {
    {{cookiecutter.RESOURCE_NAME}}Entity entity = create{{cookiecutter.RESOURCE_NAME}}Entity();

    {{cookiecutter.RESOURCE_NAME}} response = mapper.toModel(entity);

    verify{{cookiecutter.RESOURCE_NAME}}(response);
  }

  @Test
  public void mapperOptionalEntityTest() {
    Optional<{{cookiecutter.RESOURCE_NAME}}Entity> entity = Optional.of(create{{cookiecutter.RESOURCE_NAME}}Entity());

    Optional<{{cookiecutter.RESOURCE_NAME}}> response = mapper.toModel(entity);

    assertThat(response.isPresent());
    verify{{cookiecutter.RESOURCE_NAME}}(response.get());
  }

  @Test
  public void mapperOptionalTest() {
    Optional<{{cookiecutter.RESOURCE_NAME}}> resource = Optional.of(create{{cookiecutter.RESOURCE_NAME}}(null));

    Optional<{{cookiecutter.RESOURCE_NAME}}Entity> response = mapper.toEntity(resource);

    assertThat(response.isPresent());
    verify{{cookiecutter.RESOURCE_NAME}}Entity(response.get(), false
    {%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %}, false{%- endif -%}
    );
  }

  @Test
  public void mapperOptionalNullTest() {
    Optional<{{cookiecutter.RESOURCE_NAME}}> resource = Optional.ofNullable(null);

    Optional<{{cookiecutter.RESOURCE_NAME}}Entity> response = mapper.toEntity(resource);

    assertThat(response.isEmpty());
  }

  @Test
  public void mapperOptionalEmptyTest() {
    Optional<{{cookiecutter.RESOURCE_NAME}}> resource = Optional.empty();

    Optional<{{cookiecutter.RESOURCE_NAME}}Entity> response = mapper.toEntity(resource);

    assertThat(response.isEmpty());
  }

  @Test
  public void mapperEntityListTest() {
    List<{{cookiecutter.RESOURCE_NAME}}Entity> entities = Arrays.asList(
        create{{cookiecutter.RESOURCE_NAME}}Entity(),
        create{{cookiecutter.RESOURCE_NAME}}Entity());

    List<{{cookiecutter.RESOURCE_NAME}}> response = mapper.toModelList(entities);

    assertThat(response.size()).isEqualTo(2);
    verify{{cookiecutter.RESOURCE_NAME}}(response.get(0));
    verify{{cookiecutter.RESOURCE_NAME}}(response.get(1));
  }

  @Test
  public void mapperEntityPageTest() {
    Pageable pageable = PageRequest.of(0, 3);
    Page<{{cookiecutter.RESOURCE_NAME}}Entity> entities =
        new PageImpl<>(
            Arrays.asList(
                create{{cookiecutter.RESOURCE_NAME}}Entity(),
                create{{cookiecutter.RESOURCE_NAME}}Entity(),
                create{{cookiecutter.RESOURCE_NAME}}Entity()),
            pageable,
            100);

    Page<{{cookiecutter.RESOURCE_NAME}}> response = mapper.toModelPage(entities);

    assertThat(response.getContent().size()).isEqualTo(3);
    assertThat(response.getTotalElements()).isEqualTo(100);
    assertThat(response.getNumber()).isEqualTo(0);
    assertThat(response.getNumberOfElements()).isEqualTo(3);

    verify{{cookiecutter.RESOURCE_NAME}}(response.toList().get(0));
    verify{{cookiecutter.RESOURCE_NAME}}(response.toList().get(1));
    verify{{cookiecutter.RESOURCE_NAME}}(response.toList().get(2));
  }

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
   * convenience function to create resource entity object.
   *
   * @return {{cookiecutter.RESOURCE_NAME}}Entity object
   */
  private {{cookiecutter.RESOURCE_NAME}}Entity create{{cookiecutter.RESOURCE_NAME}}Entity() {
    return new {{cookiecutter.RESOURCE_NAME}}Entity(identifier, username, pii, firstName, lastName
{%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %}, parentIdentifier{%- endif -%}
    );
  }

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  protected void verify{{cookiecutter.RESOURCE_NAME}}({{cookiecutter.RESOURCE_NAME}} response) {
    assertThat(response.getUserName()).isEqualTo(username);
    assertThat(response.getPii()).isEqualTo(pii);
    assertThat(response.getFirstName()).isEqualTo(firstName);
    assertThat(response.getLastName()).isEqualTo(lastName);
    assertThat(response.getId()).isEqualTo(identifier);
  }

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  private void verify{{cookiecutter.RESOURCE_NAME}}Entity({{cookiecutter.RESOURCE_NAME}}Entity response) {
    verify{{cookiecutter.RESOURCE_NAME}}Entity(response, true
    {%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %}, true{%- endif -%}
    );
  }

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  // CSOFF: LineLength
  private void verify{{cookiecutter.RESOURCE_NAME}}Entity({{cookiecutter.RESOURCE_NAME}}Entity response, boolean hasId
    {%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %}, boolean hasParentId{%- endif -%}
    ) {
    // CSON: LineLength
    assertThat(response.getUserName()).isEqualTo(username);
    assertThat(response.getPii()).isEqualTo(pii);
    assertThat(response.getFirstName()).isEqualTo(firstName);
    assertThat(response.getLastName()).isEqualTo(lastName);
{%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %}
    if (hasParentId) {
      assertThat(response.get{{cookiecutter.PARENT_RESOURCE_NAME}}Id()).isEqualTo(parentIdentifier);
    } else {
      assertThat(response.get{{cookiecutter.PARENT_RESOURCE_NAME}}Id()).isNotEqualTo(parentIdentifier);
    }
{%- endif %}
    if (hasId) {
      assertThat(response.getId()).isEqualTo(identifier);
    } else {
      assertThat(response.getId()).isNotEqualTo(identifier);
    }
  }
}
