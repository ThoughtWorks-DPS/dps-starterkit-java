{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}};

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.exception.RequestValidationException;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}};
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface {{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}Service {

  {{cookiecutter.SUB_RESOURCE_NAME}} add(String parentId, {{cookiecutter.SUB_RESOURCE_NAME}} resource)
      throws RequestValidationException;

  Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> findById(String parentId, String id);

  Page<{{cookiecutter.SUB_RESOURCE_NAME}}> findAll(String parentId, Pageable pageable);

    // CSOFF: LineLength
  Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> updateById(String parentId, String id, {{cookiecutter.SUB_RESOURCE_NAME}} record)
      throws RequestValidationException;
  // CSON: LineLength

  Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> deleteById(String parentId, String id);

}
{%- endif %}
