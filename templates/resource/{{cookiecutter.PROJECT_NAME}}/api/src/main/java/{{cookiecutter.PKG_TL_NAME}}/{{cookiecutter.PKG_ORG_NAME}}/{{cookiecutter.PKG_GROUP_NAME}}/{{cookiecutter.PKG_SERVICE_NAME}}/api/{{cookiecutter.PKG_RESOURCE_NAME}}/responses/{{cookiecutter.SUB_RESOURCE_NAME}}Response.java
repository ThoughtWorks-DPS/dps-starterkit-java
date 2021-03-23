package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
//@RequiredArgsConstructor
@Getter
@Schema(
    name = "{{cookiecutter.SUB_RESOURCE_NAME}}Response",
    description = "Metadata describing an Account resource and unique identifier")
public class {{cookiecutter.SUB_RESOURCE_NAME}}Response {

  @NonNull
  @Schema(description = "unique id of the {{cookiecutter.SUB_RESOURCE_NAME}} resource")
  private final String id;

  @NonNull
  @Schema(description = "username of the {{cookiecutter.SUB_RESOURCE_NAME}} holder")
  private final String userName;

  @NonNull
  @Schema(description = "Given name of the {{cookiecutter.SUB_RESOURCE_NAME}} holder")
  private final String firstName;

  @NonNull
  @Schema(description = "Family name of the {{cookiecutter.SUB_RESOURCE_NAME}} holder")
  private final String lastName;
}
