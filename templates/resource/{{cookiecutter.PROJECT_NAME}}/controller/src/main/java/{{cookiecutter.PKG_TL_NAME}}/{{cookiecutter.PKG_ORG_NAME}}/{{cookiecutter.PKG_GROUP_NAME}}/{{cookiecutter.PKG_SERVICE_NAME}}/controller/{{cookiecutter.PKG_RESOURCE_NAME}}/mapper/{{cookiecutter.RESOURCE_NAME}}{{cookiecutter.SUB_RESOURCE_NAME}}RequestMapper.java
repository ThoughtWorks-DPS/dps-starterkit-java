{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.controller.{{cookiecutter.PKG_RESOURCE_NAME}}.mapper;

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.requests.{{cookiecutter.SUB_RESOURCE_NAME}}Request;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.responses.{{cookiecutter.SUB_RESOURCE_NAME}}Response;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.responses.PagedResponse;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}};
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface {{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}RequestMapper {

  @Mapping(constant = "UNKNOWN_ID", target = "id")
  {{cookiecutter.SUB_RESOURCE_NAME}} toModel({{cookiecutter.SUB_RESOURCE_NAME}}Request request);

  {{cookiecutter.SUB_RESOURCE_NAME}}Response to{{cookiecutter.SUB_RESOURCE_NAME}}Response({{cookiecutter.SUB_RESOURCE_NAME}} src);

  default {{cookiecutter.SUB_RESOURCE_NAME}}Response to{{cookiecutter.SUB_RESOURCE_NAME}}Response(Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> src) {
    return to{{cookiecutter.SUB_RESOURCE_NAME}}Response(src.orElse(null));
  }

  List<{{cookiecutter.SUB_RESOURCE_NAME}}Response> to{{cookiecutter.SUB_RESOURCE_NAME}}ResponseList(List<{{cookiecutter.SUB_RESOURCE_NAME}}> src);

  /**
   * convert to PagedResponse<>.
   *
   * @param src Page<> object
   * @return PagedResponse<>
   */
  default PagedResponse<{{cookiecutter.SUB_RESOURCE_NAME}}Response> to{{cookiecutter.SUB_RESOURCE_NAME}}ResponsePage(Page<{{cookiecutter.SUB_RESOURCE_NAME}}> src) {
    return new PagedResponse<>(
        to{{cookiecutter.SUB_RESOURCE_NAME}}ResponseList(src.getContent()),
        src.getTotalPages(),
        src.getTotalElements(),
        src.getNumber(),
        src.getNumberOfElements());
  }
}
{%- endif %}
