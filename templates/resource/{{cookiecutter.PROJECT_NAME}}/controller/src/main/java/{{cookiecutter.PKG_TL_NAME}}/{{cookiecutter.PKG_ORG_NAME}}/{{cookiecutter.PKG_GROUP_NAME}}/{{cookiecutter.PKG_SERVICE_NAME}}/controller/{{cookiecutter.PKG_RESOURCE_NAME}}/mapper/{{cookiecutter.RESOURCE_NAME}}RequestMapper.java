package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.controller.account.mapper;

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.requests.{{cookiecutter.RESOURCE_NAME}}Request;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.responses.{{cookiecutter.RESOURCE_NAME}}Response;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.responses.Add{{cookiecutter.RESOURCE_NAME}}Response;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.RESOURCE_NAME}};
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface {{cookiecutter.RESOURCE_NAME}}RequestMapper {

  @Mapping(target = "response", expression = "java(\"Hello \" + src.getFirstName())")
  Add{{cookiecutter.RESOURCE_NAME}}Response toAdd{{cookiecutter.RESOURCE_NAME}}Response({{cookiecutter.RESOURCE_NAME}} src);

  @Mapping(constant = "UNKNOWN_ID", target = "id")
  {{cookiecutter.RESOURCE_NAME}} toModel({{cookiecutter.RESOURCE_NAME}}Request request);

  @Mapping(
      target = "fullName",
      expression = "java(String.format(\"%s %s\",src.getFirstName(),src.getLastName()))")
  {{cookiecutter.RESOURCE_NAME}}Response to{{cookiecutter.RESOURCE_NAME}}Response({{cookiecutter.RESOURCE_NAME}} src);

  default {{cookiecutter.RESOURCE_NAME}}Response to{{cookiecutter.RESOURCE_NAME}}Response(Optional<{{cookiecutter.RESOURCE_NAME}}> src) {
    return to{{cookiecutter.RESOURCE_NAME}}Response(src.orElse(null));
  }

  List<{{cookiecutter.RESOURCE_NAME}}Response> toResponseList(List<{{cookiecutter.RESOURCE_NAME}}> src);
}
