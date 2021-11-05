package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.provider.{{cookiecutter.PKG_RESOURCE_NAME}}.mapper;

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.persistence.model.{{cookiecutter.RESOURCE_NAME}}Entity;
{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.persistence.model.{{cookiecutter.SUB_RESOURCE_NAME}}Entity;
{%- endif %}
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.RESOURCE_NAME}};
{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}};
{%- endif %}
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface {{cookiecutter.RESOURCE_NAME}}EntityMapper {

  {{cookiecutter.RESOURCE_NAME}}Entity toEntity({{cookiecutter.RESOURCE_NAME}} src);

  default Optional<{{cookiecutter.RESOURCE_NAME}}Entity> toEntity(Optional<{{cookiecutter.RESOURCE_NAME}}> src) {
    return Optional.ofNullable(toEntity(src.orElse(null)));
  }

  List<{{cookiecutter.RESOURCE_NAME}}Entity> toEntityList(List<{{cookiecutter.RESOURCE_NAME}}> src);

  {{cookiecutter.RESOURCE_NAME}} toModel({{cookiecutter.RESOURCE_NAME}}Entity src);

  default Optional<{{cookiecutter.RESOURCE_NAME}}> toModel(Optional<{{cookiecutter.RESOURCE_NAME}}Entity> src) {
    return Optional.ofNullable(toModel(src.orElse(null)));
  }

  default Page<{{cookiecutter.RESOURCE_NAME}}> toModelPage(Page<{{cookiecutter.RESOURCE_NAME}}Entity> src) {
    return src.map(this::toModel);
  }

  List<{{cookiecutter.RESOURCE_NAME}}> toModelList(Iterable<{{cookiecutter.RESOURCE_NAME}}Entity> src);

  @Mapping(target = "id", ignore = true)
{%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %}
  @Mapping(target = "{{cookiecutter.PARENT_RESOURCE_VAR_NAME}}Id", ignore = true)
{%- endif %}
  {{cookiecutter.RESOURCE_NAME}}Entity updateMetadata({{cookiecutter.RESOURCE_NAME}} src, @MappingTarget {{cookiecutter.RESOURCE_NAME}}Entity dst);

{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}

  @Mapping(target = "pii", constant = "FIXME")
  @Mapping(target = "{{cookiecutter.RESOURCE_VAR_NAME}}Id", ignore = true)
  {{cookiecutter.SUB_RESOURCE_NAME}}Entity to{{cookiecutter.SUB_RESOURCE_NAME}}Entity({{cookiecutter.SUB_RESOURCE_NAME}} src);

  default Optional<{{cookiecutter.SUB_RESOURCE_NAME}}Entity> to{{cookiecutter.SUB_RESOURCE_NAME}}Entity(Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> src) {
    return Optional.ofNullable(to{{cookiecutter.SUB_RESOURCE_NAME}}Entity(src.orElse(null)));
  }

  List<{{cookiecutter.SUB_RESOURCE_NAME}}Entity> to{{cookiecutter.SUB_RESOURCE_NAME}}EntityList(List<{{cookiecutter.SUB_RESOURCE_NAME}}> src);

  {{cookiecutter.SUB_RESOURCE_NAME}} to{{cookiecutter.SUB_RESOURCE_NAME}}Model({{cookiecutter.SUB_RESOURCE_NAME}}Entity src);

  default Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> to{{cookiecutter.SUB_RESOURCE_NAME}}Model(Optional<{{cookiecutter.SUB_RESOURCE_NAME}}Entity> src) {
    return Optional.ofNullable(to{{cookiecutter.SUB_RESOURCE_NAME}}Model(src.orElse(null)));
  }

  default Page<{{cookiecutter.SUB_RESOURCE_NAME}}> to{{cookiecutter.SUB_RESOURCE_NAME}}ModelPage(Page<{{cookiecutter.SUB_RESOURCE_NAME}}Entity> src) {
    return src.map(this::to{{cookiecutter.SUB_RESOURCE_NAME}}Model);
  }

  List<{{cookiecutter.SUB_RESOURCE_NAME}}> to{{cookiecutter.SUB_RESOURCE_NAME}}ModelList(Iterable<{{cookiecutter.SUB_RESOURCE_NAME}}Entity> src);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "pii", ignore = true)
  @Mapping(target = "{{cookiecutter.RESOURCE_VAR_NAME}}Id", ignore = true)
  {{cookiecutter.SUB_RESOURCE_NAME}}Entity update{{cookiecutter.SUB_RESOURCE_NAME}}Metadata({{cookiecutter.SUB_RESOURCE_NAME}} src, @MappingTarget {{cookiecutter.SUB_RESOURCE_NAME}}Entity dst);

  @Mapping(target = "{{cookiecutter.RESOURCE_VAR_NAME}}Id", source = "{{cookiecutter.RESOURCE_VAR_NAME}}Id")
  @Mapping(target = "pii", constant = "FIXME")
  io.twdps.starter.example.service.spi.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}} toService{{cookiecutter.SUB_RESOURCE_NAME}}(
      {{cookiecutter.SUB_RESOURCE_NAME}} src, String {{cookiecutter.RESOURCE_VAR_NAME}}Id);

  {{cookiecutter.SUB_RESOURCE_NAME}} fromService{{cookiecutter.SUB_RESOURCE_NAME}}(
      io.twdps.starter.example.service.spi.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}} src);

  default Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> fromService{{cookiecutter.SUB_RESOURCE_NAME}}(
     Optional<io.twdps.starter.example.service.spi.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}}> src) {
   return Optional.ofNullable(fromService{{cookiecutter.SUB_RESOURCE_NAME}}(src.orElse(null)));
  }

  default Page<{{cookiecutter.SUB_RESOURCE_NAME}}> fromService{{cookiecutter.SUB_RESOURCE_NAME}}Page(
     Page<io.twdps.starter.example.service.spi.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}}> src) {
   return src.map(this::fromService{{cookiecutter.SUB_RESOURCE_NAME}});
  }


{%- endif %}
}
