package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class {{cookiecutter.RESOURCE_NAME}} {

  private String id;

  @NonNull
  private String userName;
  @NonNull
  private String pii;
  @NonNull
  private String firstName;
  @NonNull
  private String lastName;
{%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %}
  private String {{cookiecutter.PARENT_RESOURCE_VAR_NAME}}Id;
{%- endif %}
}
