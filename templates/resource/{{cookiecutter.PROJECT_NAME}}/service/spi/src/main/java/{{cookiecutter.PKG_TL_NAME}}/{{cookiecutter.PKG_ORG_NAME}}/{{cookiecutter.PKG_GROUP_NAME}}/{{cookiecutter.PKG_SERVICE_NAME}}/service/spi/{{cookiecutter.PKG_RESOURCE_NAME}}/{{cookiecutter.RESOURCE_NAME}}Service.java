package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}};

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.exception.RequestValidationException;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.RESOURCE_NAME}};
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface {{cookiecutter.RESOURCE_NAME}}Service {

  {{cookiecutter.RESOURCE_NAME}} add({{cookiecutter.RESOURCE_NAME}} resource)
      throws RequestValidationException;

  Page<{{cookiecutter.RESOURCE_NAME}}> findByLastName(String lastName, Pageable pageable);

  Optional<{{cookiecutter.RESOURCE_NAME}}> findByUserName(String userName);

  Optional<{{cookiecutter.RESOURCE_NAME}}> findById(String id);

  Page<{{cookiecutter.RESOURCE_NAME}}> findAll(Pageable pageable);

{%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %}
  Page<{{cookiecutter.RESOURCE_NAME}}> findAllBy{{cookiecutter.PARENT_RESOURCE_NAME}}Id(String id, Pageable pageable);
{%- endif %}

  Optional<{{cookiecutter.RESOURCE_NAME}}> updateById(String id, {{cookiecutter.RESOURCE_NAME}} record)
      throws RequestValidationException;

  Optional<{{cookiecutter.RESOURCE_NAME}}> deleteById(String id);

}
