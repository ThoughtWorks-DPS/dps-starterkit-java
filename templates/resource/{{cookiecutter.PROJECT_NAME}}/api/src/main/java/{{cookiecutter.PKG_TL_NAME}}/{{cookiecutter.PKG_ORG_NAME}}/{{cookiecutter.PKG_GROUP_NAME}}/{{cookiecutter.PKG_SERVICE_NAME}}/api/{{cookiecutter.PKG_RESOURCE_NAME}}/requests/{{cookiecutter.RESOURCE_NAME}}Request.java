package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class {{cookiecutter.RESOURCE_NAME}}Request {

  @NonNull
  private final String userName;
  @NonNull
  private final String pii;
  @NonNull
  private final String firstName;
  @NonNull
  private final String lastName;

  /** Create object from json.
   *
   * @param userName  username of {{cookiecutter.RESOURCE_NAME}} holder
   * @param pii private information of {{cookiecutter.RESOURCE_NAME}} holder
   * @param firstName firstname of {{cookiecutter.RESOURCE_NAME}} holder
   * @param lastName lastname of {{cookiecutter.RESOURCE_NAME}} holder
   */
  @JsonCreator
  public {{cookiecutter.RESOURCE_NAME}}Request(
      @NonNull @JsonProperty("userName") String userName,
      @NonNull @JsonProperty("pii") String pii,
      @NonNull @JsonProperty("firstName") String firstName,
      @NonNull @JsonProperty("lastName") String lastName) {

    this.userName = userName;
    this.pii = pii;
    this.firstName = firstName;
    this.lastName = lastName;
  }
}
