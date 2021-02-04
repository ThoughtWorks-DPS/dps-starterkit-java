package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.persistence.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface {{cookiecutter.RESOURCE_NAME}}EntityRepository extends CrudRepository<{{cookiecutter.RESOURCE_NAME}}Entity, String> {

  Optional<{{cookiecutter.RESOURCE_NAME}}Entity> findByUserName(String userName);

  List<{{cookiecutter.RESOURCE_NAME}}Entity> findByLastName(String lastName);

}
