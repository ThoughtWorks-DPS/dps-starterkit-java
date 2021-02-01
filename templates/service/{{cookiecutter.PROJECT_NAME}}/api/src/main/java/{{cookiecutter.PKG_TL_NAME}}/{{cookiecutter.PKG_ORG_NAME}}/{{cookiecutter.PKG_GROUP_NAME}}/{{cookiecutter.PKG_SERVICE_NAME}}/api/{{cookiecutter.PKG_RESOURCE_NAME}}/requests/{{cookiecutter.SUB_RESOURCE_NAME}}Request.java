package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class {{cookiecutter.SUB_RESOURCE_NAME}}Request {

  @NonNull
  private final String userName;
  @NonNull
  private final String firstName;
  @NonNull
  private final String lastName;

  /** Create object from json.
   *
 * @param userName  username of {{cookiecutter.RESOURCE_NAME}} holder
 * @param firstName firstname of {{cookiecutter.RESOURCE_NAME}} holder
 * @param lastName lastname of {{cookiecutter.RESOURCE_NAME}} holder
 */
@JsonCreator
public {{cookiecutter.RESOURCE_NAME}}Request(
@JsonProperty("userName") String userName,
@JsonProperty("firstName") String firstName,
@JsonProperty("lastName") String lastName) {

    this.userName = userName;
    this.firstName = firstName;
    this.lastName = lastName;
    }

}
