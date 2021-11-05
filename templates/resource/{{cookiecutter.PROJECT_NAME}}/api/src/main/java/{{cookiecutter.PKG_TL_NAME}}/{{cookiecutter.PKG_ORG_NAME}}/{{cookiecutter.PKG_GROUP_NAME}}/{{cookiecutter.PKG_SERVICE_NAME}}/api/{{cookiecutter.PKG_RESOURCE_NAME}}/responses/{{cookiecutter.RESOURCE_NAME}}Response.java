package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@AllArgsConstructor
// @RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Schema(
    name = "{{cookiecutter.RESOURCE_NAME}}Response",
    description = "Metadata describing an {{cookiecutter.RESOURCE_NAME}} resource and unique identifier")
public class {{cookiecutter.RESOURCE_NAME}}Response {

  @NonNull
  @Schema(
      description = "unique id of the {{cookiecutter.RESOURCE_NAME}} resource",
      example = "dd373780-79fb-4285-8c9b-bf48a8014a68")
  private final String id;

  @NonNull
  @Schema(description = "username of the {{cookiecutter.RESOURCE_NAME}} holder", example = "lvanpelt")
  private final String userName;

  @NonNull
  @Schema(description = "Representative PII of the {{cookiecutter.RESOURCE_NAME}} holder", example = "123-456-7890")
  private final String pii;

  @NonNull
  @Schema(description = "Given name of the {{cookiecutter.RESOURCE_NAME}} holder", example = "Lucy")
  private final String firstName;

  @NonNull
  @Schema(description = "Family name of the {{cookiecutter.RESOURCE_NAME}} holder", example = "van Pelt")
  private final String lastName;

  @NonNull
  @Schema(
      description = "Constructed full name (given + family) of the {{cookiecutter.RESOURCE_NAME}} holder",
      example = "Lucy van Pelt")
  private final String fullName;

{%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %}
  @NonNull
  @Schema(
    description = "Parent {{cookiecutter.PARENT_RESOURCE_VAR_NAME}}Id of the {{cookiecutter.RESOURCE_NAME}} holder",
    example = "uuid-123456789-abcd")
  private final String {{cookiecutter.PARENT_RESOURCE_VAR_NAME}}Id;
{%- endif %}
}
