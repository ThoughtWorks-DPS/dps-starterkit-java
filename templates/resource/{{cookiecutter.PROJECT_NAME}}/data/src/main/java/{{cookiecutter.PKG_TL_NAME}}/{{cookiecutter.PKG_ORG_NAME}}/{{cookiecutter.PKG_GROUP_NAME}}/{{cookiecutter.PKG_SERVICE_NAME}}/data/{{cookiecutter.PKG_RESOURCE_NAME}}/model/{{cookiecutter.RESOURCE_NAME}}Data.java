package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_RESOURCE_NAME}}.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class {{cookiecutter.RESOURCE_NAME}}Data {

  // TODO: Refactor {{cookiecutter.RESOURCE_NAME}}Data by renaming variable names, migrate types
  private String id;
  private String userName;
  private String pii;
  private String firstName;
  private String lastName;
  private String fullName;
  // TODO: Additional {{cookiecutter.RESOURCE_NAME}}Data data elements
{%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %}
  private String {{cookiecutter.PARENT_RESOURCE_VAR_NAME}}Id;
{%- endif %}
}
