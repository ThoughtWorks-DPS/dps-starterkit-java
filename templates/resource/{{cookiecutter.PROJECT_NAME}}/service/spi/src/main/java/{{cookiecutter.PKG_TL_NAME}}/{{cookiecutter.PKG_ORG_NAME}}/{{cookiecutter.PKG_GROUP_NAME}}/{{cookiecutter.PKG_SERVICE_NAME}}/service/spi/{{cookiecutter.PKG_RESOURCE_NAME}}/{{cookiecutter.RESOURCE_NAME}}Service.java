package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}};

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.RESOURCE_NAME}};

import java.util.List;
import java.util.Optional;

public interface {{cookiecutter.RESOURCE_NAME}}Service {

  {{cookiecutter.RESOURCE_NAME}} add({{cookiecutter.RESOURCE_NAME}} resource);

  List<{{cookiecutter.RESOURCE_NAME}}> findByLastName(String lastName);

  Optional<{{cookiecutter.RESOURCE_NAME}}> findByUserName(String userName);

  Optional<{{cookiecutter.RESOURCE_NAME}}> findById(String id);

  List<{{cookiecutter.RESOURCE_NAME}}> findAll();

  Optional<{{cookiecutter.RESOURCE_NAME}}> updateById(String id, {{cookiecutter.RESOURCE_NAME}} record);

  Optional<{{cookiecutter.RESOURCE_NAME}}> deleteById(String id);
}
