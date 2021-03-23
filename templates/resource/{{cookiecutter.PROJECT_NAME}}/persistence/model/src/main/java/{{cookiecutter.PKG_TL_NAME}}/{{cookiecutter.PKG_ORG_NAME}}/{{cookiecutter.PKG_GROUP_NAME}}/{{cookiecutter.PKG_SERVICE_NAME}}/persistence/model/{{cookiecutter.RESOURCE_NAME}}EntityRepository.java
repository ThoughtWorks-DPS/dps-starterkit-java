package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.persistence.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface {{cookiecutter.RESOURCE_NAME}}EntityRepository extends PagingAndSortingRepository<{{cookiecutter.RESOURCE_NAME}}Entity, String> {

  Optional<{{cookiecutter.RESOURCE_NAME}}Entity> findByUserName(String userName);

  Page<{{cookiecutter.RESOURCE_NAME}}Entity> findByLastName(String lastName, Pageable pageable);

}
