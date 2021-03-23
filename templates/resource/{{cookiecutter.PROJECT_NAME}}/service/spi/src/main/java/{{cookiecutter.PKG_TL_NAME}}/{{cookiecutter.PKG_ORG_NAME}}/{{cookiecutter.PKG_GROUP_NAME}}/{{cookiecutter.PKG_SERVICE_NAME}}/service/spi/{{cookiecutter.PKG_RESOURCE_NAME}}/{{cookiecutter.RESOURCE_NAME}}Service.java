package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}};

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.exception.RequestValidationException;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.RESOURCE_NAME}};
{%- if cookiecutter.CREATE_SUBRESOURCE == "y" %}
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}};
{%- endif %}
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface {{cookiecutter.RESOURCE_NAME}}Service {

  {{cookiecutter.RESOURCE_NAME}} add({{cookiecutter.RESOURCE_NAME}} resource) throws RequestValidationException;

  Page<{{cookiecutter.RESOURCE_NAME}}> findByLastName(String lastName, Pageable pageable);

  Optional<{{cookiecutter.RESOURCE_NAME}}> findByUserName(String userName);

  Optional<{{cookiecutter.RESOURCE_NAME}}> findById(String id);

  Page<{{cookiecutter.RESOURCE_NAME}}> findAll(Pageable pageable);

  Optional<{{cookiecutter.RESOURCE_NAME}}> updateById(String id, {{cookiecutter.RESOURCE_NAME}} record) throws RequestValidationException;

  Optional<{{cookiecutter.RESOURCE_NAME}}> deleteById(String id);

{%- if cookiecutter.CREATE_SUBRESOURCE == "y" %}

  {{cookiecutter.SUB_RESOURCE_NAME}} add{{cookiecutter.SUB_RESOURCE_NAME}}(String id, {{cookiecutter.SUB_RESOURCE_NAME}} subResource) throws RequestValidationException;

  Page<{{cookiecutter.SUB_RESOURCE_NAME}}> get{{cookiecutter.SUB_RESOURCE_NAME}}s(String id, Pageable pageable);

  Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> get{{cookiecutter.SUB_RESOURCE_NAME}}(String id, String subResourceId);

  Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> update{{cookiecutter.SUB_RESOURCE_NAME}}(String id, String subResourceId, {{cookiecutter.SUB_RESOURCE_NAME}} subResource) throws RequestValidationException;

  Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> delete{{cookiecutter.SUB_RESOURCE_NAME}}(String id, String subResourceId);

{%- endif %}
}
