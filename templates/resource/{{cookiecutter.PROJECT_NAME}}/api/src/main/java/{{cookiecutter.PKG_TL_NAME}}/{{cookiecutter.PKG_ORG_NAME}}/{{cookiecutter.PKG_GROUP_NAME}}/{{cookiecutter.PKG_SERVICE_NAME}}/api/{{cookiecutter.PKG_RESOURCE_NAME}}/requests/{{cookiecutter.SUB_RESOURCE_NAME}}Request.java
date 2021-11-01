package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(name = "{{cookiecutter.SUB_RESOURCE_NAME}}Request", description = "Metadata describing an {{cookiecutter.SUB_RESOURCE_NAME}} resource")
public class {{cookiecutter.SUB_RESOURCE_NAME}}Request {

  @NonNull
  @Schema(description = "username of the {{cookiecutter.SUB_RESOURCE_NAME}} holder", example = "lvanpelt")
  private final String userName;

  @NonNull
  @Schema(description = "Given name of the {{cookiecutter.SUB_RESOURCE_NAME}} holder", example = "Lucille")
  private final String firstName;

  @NonNull
  @Schema(description = "Family name of the {{cookiecutter.SUB_RESOURCE_NAME}} holder", example = "Van Pelt")
  private final String lastName;

  /**
   * Create object from json.
   *
   * @param userName  username of {{cookiecutter.RESOURCE_NAME}} holder
   * @param firstName firstname of {{cookiecutter.RESOURCE_NAME}} holder
   * @param lastName lastname of {{cookiecutter.RESOURCE_NAME}} holder
   */
  @JsonCreator
  public {{cookiecutter.SUB_RESOURCE_NAME}}Request(
      @NonNull @JsonProperty("userName") String userName,
      @NonNull @JsonProperty("firstName") String firstName,
      @NonNull @JsonProperty("lastName") String lastName) {

    this.userName = userName;
    this.firstName = firstName;
    this.lastName = lastName;
  }

}
