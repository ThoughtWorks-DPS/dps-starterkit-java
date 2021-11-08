{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.provider.{{cookiecutter.PKG_RESOURCE_NAME}}.mapper;

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}};
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface {{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}EntityMapper {

  @Mapping(target = "{{cookiecutter.RESOURCE_VAR_NAME}}Id", source = "{{cookiecutter.RESOURCE_VAR_NAME}}Id")
  @Mapping(target = "pii", constant = "FIXME")
  io.twdps.starter.example.service.spi.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}} toServiceModel(
      {{cookiecutter.SUB_RESOURCE_NAME}} src, String {{cookiecutter.RESOURCE_VAR_NAME}}Id);

  {{cookiecutter.SUB_RESOURCE_NAME}} fromServiceModel(
      io.twdps.starter.example.service.spi.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}} src);

  default Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> fromServiceModel(
     Optional<io.twdps.starter.example.service.spi.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}}> src) {
   return Optional.ofNullable(fromServiceModel(src.orElse(null)));
  }

  default Page<{{cookiecutter.SUB_RESOURCE_NAME}}> fromServiceModelPage(
     Page<io.twdps.starter.example.service.spi.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}}> src) {
   return src.map(this::fromServiceModel);
  }

}
{%- endif %}
