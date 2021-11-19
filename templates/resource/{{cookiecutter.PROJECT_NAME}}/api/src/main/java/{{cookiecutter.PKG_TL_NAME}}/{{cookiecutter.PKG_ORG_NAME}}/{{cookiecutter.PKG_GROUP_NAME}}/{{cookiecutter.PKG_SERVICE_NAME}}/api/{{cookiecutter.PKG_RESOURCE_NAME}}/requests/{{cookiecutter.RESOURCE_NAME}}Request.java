package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(name = "{{cookiecutter.RESOURCE_NAME}}Request", description = "Metadata describing an {{cookiecutter.RESOURCE_NAME}} resource")
public class {{cookiecutter.RESOURCE_NAME}}Request {

   // TODO: Refactor {{cookiecutter.RESOURCE_NAME}}Request by renaming variable names, migrate types
  @NonNull
  @Schema(description = "username of the {{cookiecutter.RESOURCE_NAME}} holder", example = "lvanpelt")
  private final String userName;

  @NonNull
  @Schema(description = "Representative PII of the {{cookiecutter.RESOURCE_NAME}} holder", example = "123-45-6789")
  private final String pii;

  @NonNull
  @Schema(description = "Given name of the {{cookiecutter.RESOURCE_NAME}} holder", example = "Lucille")
  private final String firstName;

  @NonNull
  @Schema(description = "Family name of the {{cookiecutter.RESOURCE_NAME}} holder", example = "Van Pelt")
  private final String lastName;

  // TODO: Additional {{cookiecutter.RESOURCE_NAME}}Request data elements
{%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %}

  @NonNull
  @Schema(
      description = "Parent {{cookiecutter.PARENT_RESOURCE_VAR_NAME}}Id of the {{cookiecutter.RESOURCE_NAME}} holder",
      example = "uuid-123456789-abcd")
  private final String {{cookiecutter.PARENT_RESOURCE_VAR_NAME}}Id;
{%- endif %}

/**
 * Create object from json.
 *
 * @param userName username of {{cookiecutter.RESOURCE_NAME}} holder
 * @param pii private information of {{cookiecutter.RESOURCE_NAME}} holder
 * @param firstName firstname of {{cookiecutter.RESOURCE_NAME}} holder
 * @param lastName lastname of {{cookiecutter.RESOURCE_NAME}} holder
{%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %}
 * @param {{cookiecutter.PARENT_RESOURCE_VAR_NAME}}Id id of {{cookiecutter.RESOURCE_NAME}} parent
{%- endif %}
 */
  @JsonCreator
  public {{cookiecutter.RESOURCE_NAME}}Request(
      @NonNull @JsonProperty("userName") String userName,
      @NonNull @JsonProperty("pii") String pii,
      @NonNull @JsonProperty("firstName") String firstName,
      @NonNull @JsonProperty("lastName") String lastName
{%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %},
      @NonNull @JsonProperty("{{cookiecutter.PARENT_RESOURCE_VAR_NAME}}Id") String {{cookiecutter.PARENT_RESOURCE_VAR_NAME}}Id
{%- endif %}) {

    this.userName = userName;
    this.pii = pii;
    this.firstName = firstName;
    this.lastName = lastName;
{%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %}
    this.{{cookiecutter.PARENT_RESOURCE_VAR_NAME}}Id = {{cookiecutter.PARENT_RESOURCE_VAR_NAME}}Id;
{%- endif %}
  }
}
