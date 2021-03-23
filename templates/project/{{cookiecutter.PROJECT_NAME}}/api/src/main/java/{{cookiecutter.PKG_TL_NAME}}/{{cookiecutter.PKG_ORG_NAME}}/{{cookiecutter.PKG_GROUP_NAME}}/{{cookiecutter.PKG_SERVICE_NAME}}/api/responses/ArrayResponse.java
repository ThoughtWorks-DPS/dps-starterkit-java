package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@AllArgsConstructor
// @RequiredArgsConstructor
@Getter
@Schema(name = "ArrayResponse", description = "Bundled list of resources")
public class ArrayResponse<T> {

  // TODO: Replace this one with PageableData
  @NonNull
  @Schema(description = "List of found resources")
  private final List<T> data;
}
