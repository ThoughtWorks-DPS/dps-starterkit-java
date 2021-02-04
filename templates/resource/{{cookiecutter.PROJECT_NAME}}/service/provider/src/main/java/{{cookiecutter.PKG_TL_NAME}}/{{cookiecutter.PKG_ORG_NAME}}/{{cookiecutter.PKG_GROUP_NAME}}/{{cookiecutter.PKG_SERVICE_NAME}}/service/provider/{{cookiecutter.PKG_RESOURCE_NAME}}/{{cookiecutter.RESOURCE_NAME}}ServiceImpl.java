package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.provider.{{cookiecutter.PKG_RESOURCE_NAME}};

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.persistence.model.{{cookiecutter.RESOURCE_NAME}}EntityRepository;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.provider.{{cookiecutter.PKG_RESOURCE_NAME}}.mapper.{{cookiecutter.RESOURCE_NAME}}EntityMapper;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.{{cookiecutter.RESOURCE_NAME}}Service;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.RESOURCE_NAME}};
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class {{cookiecutter.RESOURCE_NAME}}ServiceImpl implements {{cookiecutter.RESOURCE_NAME}}Service {

  private {{cookiecutter.RESOURCE_NAME}}EntityRepository repository;
  private {{cookiecutter.RESOURCE_NAME}}EntityMapper mapper;

  {{cookiecutter.RESOURCE_NAME}}ServiceImpl({{cookiecutter.RESOURCE_NAME}}EntityRepository repository,
                     {{cookiecutter.RESOURCE_NAME}}EntityMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }
  /**
   * add a new {{cookiecutter.RESOURCE_NAME}} entity.
   *
   * @param resource resource info to add (id should be null)
   * @return new resource object with valid id
   */
  public {{cookiecutter.RESOURCE_NAME}} add({{cookiecutter.RESOURCE_NAME}} resource) {
    {{cookiecutter.RESOURCE_NAME}} saved = mapper.toModel(repository.save(mapper.toEntity(resource)));
    return saved;
  }

  /**
   * find all resources matching last name.
   *
   * @param lastName criteria for match
   * @return list of matching {{cookiecutter.RESOURCE_NAME}} records
   */
  public List<{{cookiecutter.RESOURCE_NAME}}> findByLastName(String lastName) {
    log.info("looking up by lastname of:{}", lastName);
    List<{{cookiecutter.RESOURCE_NAME}}> responseList = mapper.toModelList(repository.findByLastName(lastName));
    log.info("Response list size:{}", responseList.size());
    return responseList;
  }

  /**
   * find resource by user name.
   *
   * @param userName username criteria to match
   * @return matching record, or null
   */
  public Optional<{{cookiecutter.RESOURCE_NAME}}> findByUserName(String userName) {
    log.info("looking up by username:{}", userName);
    Optional<{{cookiecutter.RESOURCE_NAME}}> resource = mapper.toModel(repository.findByUserName(userName));
    return resource;
  }

  @Override
  public Optional<{{cookiecutter.RESOURCE_NAME}}> findById(String id) {
    Optional<{{cookiecutter.RESOURCE_NAME}}> resource = mapper.toModel(repository.findById(id));
    return resource;
  }

  @Override
  public List<{{cookiecutter.RESOURCE_NAME}}> findAll() {
    List<{{cookiecutter.RESOURCE_NAME}}> resource = mapper.toModelList(repository.findAll());
    return resource;
  }

  @Override
  public Optional<{{cookiecutter.RESOURCE_NAME}}> updateById(String id, {{cookiecutter.RESOURCE_NAME}} record) {
    Optional<{{cookiecutter.RESOURCE_NAME}}> resource =
        mapper.toModel(
            repository
                .findById(id)
                .map((obj) -> mapper.updateMetadata(record, obj))
                .map((obj) -> repository.save(obj)));

    return resource;
  }

  @Override
  public Optional<{{cookiecutter.RESOURCE_NAME}}> deleteById(String id) {
    Optional<{{cookiecutter.RESOURCE_NAME}}> resource = findById(id);
    repository.deleteById(id);
    return resource;
  }
}
