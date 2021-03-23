package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.provider.{{cookiecutter.PKG_RESOURCE_NAME}}.mapper;

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.persistence.model.{{cookiecutter.RESOURCE_NAME}}Entity;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.RESOURCE_NAME}};
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

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
  {{cookiecutter.RESOURCE_NAME}}Entity updateMetadata({{cookiecutter.RESOURCE_NAME}} src, @MappingTarget {{cookiecutter.RESOURCE_NAME}}Entity dst);

}
