package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@AllArgsConstructor
//@RequiredArgsConstructor
@Getter
public class ArrayResponse<T> {

  // TODO: Replace this one with PageableData
  @NonNull
  private final List<T> data;
}
