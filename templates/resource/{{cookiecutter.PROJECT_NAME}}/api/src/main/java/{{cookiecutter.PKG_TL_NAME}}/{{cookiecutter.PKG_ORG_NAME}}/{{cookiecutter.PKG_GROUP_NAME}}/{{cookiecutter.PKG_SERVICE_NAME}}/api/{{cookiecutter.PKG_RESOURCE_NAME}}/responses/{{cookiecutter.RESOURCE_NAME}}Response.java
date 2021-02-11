package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
//@RequiredArgsConstructor
@Getter
public class {{cookiecutter.RESOURCE_NAME}}Response {

  @NonNull
  private final String id;
  @NonNull
  private final String userName;
  @NonNull
  private final String pii;
  @NonNull
  private final String firstName;
  @NonNull
  private final String lastName;
  @NonNull
  private final String fullName;
}