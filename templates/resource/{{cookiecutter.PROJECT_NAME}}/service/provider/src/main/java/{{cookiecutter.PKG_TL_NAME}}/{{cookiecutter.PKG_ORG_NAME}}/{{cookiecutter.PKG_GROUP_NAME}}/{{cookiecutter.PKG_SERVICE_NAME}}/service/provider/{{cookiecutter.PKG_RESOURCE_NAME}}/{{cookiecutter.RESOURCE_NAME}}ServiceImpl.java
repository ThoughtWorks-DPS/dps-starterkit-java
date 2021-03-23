package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.provider.{{cookiecutter.PKG_RESOURCE_NAME}};

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.exception.RequestValidationException;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.persistence.model.{{cookiecutter.RESOURCE_NAME}}EntityRepository;
{%- if cookiecutter.CREATE_SUBRESOURCE == "y" %}
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.persistence.model.{{cookiecutter.SUB_RESOURCE_NAME}}EntityRepository;
{%- endif %}
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.provider.{{cookiecutter.PKG_RESOURCE_NAME}}.mapper.{{cookiecutter.RESOURCE_NAME}}EntityMapper;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.{{cookiecutter.RESOURCE_NAME}}Service;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.RESOURCE_NAME}};
{%- if cookiecutter.CREATE_SUBRESOURCE == "y" %}
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}};
{%- endif %}
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class {{cookiecutter.RESOURCE_NAME}}ServiceImpl implements {{cookiecutter.RESOURCE_NAME}}Service {

  private {{cookiecutter.RESOURCE_NAME}}EntityRepository repository;
  private {{cookiecutter.RESOURCE_NAME}}EntityMapper mapper;

{%- if cookiecutter.CREATE_SUBRESOURCE == "y" -%}
  private {{cookiecutter.SUB_RESOURCE_NAME}}EntityRepository subResourceRepository;
  private {{cookiecutter.SUB_RESOURCE_NAME}}EntityMapper subResourceMapper;
{%- endif %}

  {{cookiecutter.RESOURCE_NAME}}ServiceImpl({{cookiecutter.RESOURCE_NAME}}EntityRepository repository,
{%- if cookiecutter.CREATE_SUBRESOURCE == "y" %}
                     {{cookiecutter.RESOURCE_NAME}}EntityRepository subResourceRepository,
                     {{cookiecutter.RESOURCE_NAME}}EntityMapper subResourceMapper,
{%- endif %}
                     {{cookiecutter.RESOURCE_NAME}}EntityMapper mapper) {
    this.repository = repository;
{%- if cookiecutter.CREATE_SUBRESOURCE == "y" %}
    this.subResourceRepository = subResourceRepository;
    this.subResourceMapping = subResourceMapping;
{%- endif %}
    this.mapper = mapper;
  }

  /**
   * add a new {{cookiecutter.RESOURCE_NAME}} entity.
   *
   * @param resource resource info to add (id should be null)
   * @return new resource object with valid id
   */
  public {{cookiecutter.RESOURCE_NAME}} add({{cookiecutter.RESOURCE_NAME}} resource) throws RequestValidationException {
    {{cookiecutter.RESOURCE_NAME}} saved = mapper.toModel(repository.save(mapper.toEntity(resource)));
    return saved;
  }

  /**
   * find all resources matching last name.
   *
   * @param lastName criteria for match
   * @return list of matching {{cookiecutter.RESOURCE_NAME}} records
   */
  public Page<{{cookiecutter.RESOURCE_NAME}}> findByLastName(String lastName, Pageable pageable) {
    log.info("looking up by lastname of:{}", lastName);
    Page<{{cookiecutter.RESOURCE_NAME}}> responseList = mapper.toModelPage(repository.findByLastName(lastName, pageable));
    log.info("Response list size:{}", responseList.getContent().size());
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
  public Page<{{cookiecutter.RESOURCE_NAME}}> findAll(Pageable pageable) {
    Page<{{cookiecutter.RESOURCE_NAME}}> resource = mapper.toModelPage(repository.findAll(pageable));
    return resource;
  }

  @Override
  public Optional<{{cookiecutter.RESOURCE_NAME}}> updateById(String id, {{cookiecutter.RESOURCE_NAME}} record) throws RequestValidationException {
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

{%- if cookiecutter.CREATE_SUBRESOURCE == "y" -%}
  /**
   * add a new {{cookiecutter.SUB_RESOURCE_NAME}} entity.
   *
   * @param resource resource info to add (id should be null)
   * @return new resource object with valid id
   */
  public {{cookiecutter.SUB_RESOURCE_NAME}} add{{cookiecutter.SUB_RESOURCE_NAME}}(String id, {{cookiecutter.SUB_RESOURCE_NAME}} subResource) throws RequestValidationException {
    {{cookiecutter.SUB_RESOURCE_NAME}}Entity entity = entityMapper.to{{cookiecutter.SUB_RESOURCE_NAME}}Entity(subResource);
    entity.setVersionId(id);
    {{cookiecutter.SUB_RESOURCE_NAME}} saved = entityMapper.to{{cookiecutter.SUB_RESOURCE_NAME}}(subResourceRepository.save(entity));
    return saved;
  }

  /**
   * find a {{cookiecutter.SUB_RESOURCE_NAME}} resource by resource id
   *
   * @param id parent resource id
   * @param subResourceId id of the {{cookiecutter.SUB_RESOURCE_NAME}}
   * @return matching record, or null
   */
  @Override
  public Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> get{{cookiecutter.SUB_RESOURCE_NAME}}(String id, String subResourceId) {
    Optional<{{cookiecutter.RESOURCE_NAME}}> resource = mapper.toModel(subResourceRepository.findById(subResourceId));
    return resource;
  }

  /**
   * find all {{cookiecutter.SUB_RESOURCE_NAME}} resources related to {{cookiecutter.RESOURCE_NAME}}
   *
   * @param id {{cookiecutter.RESOURCE_NAME}} resource id
   * @return list of {{cookiecutter.SUB_RESOURCE_NAME}} resources
   */
  @Override
  public Page<{{cookiecutter.SUB_RESOURCE_NAME}}> get{{cookiecutter.SUB_RESOURCE_NAME}}s(String id, Pageable pageable) {
    Page<{{cookiecutter.SUB_RESOURCE_NAME}}> resources = mapper.toModelPage(subResourceRepository.findAllBy{{cookiecutter.RESOURCE_NAME}}Id(id, pageable));
    return resources;
  }


  /**
   * update a {{cookiecutter.SUB_RESOURCE_NAME}} resource based on id
   *
   * @param id {{cookiecutter.RESOURCE_NAME}} resource id
   * @param subResourceId {{cookiecutter.SUB_RESOURCE_NAME}} resource id
   * @param subResource {{cookiecutter.SUB_RESOURCE_NAME}} resource data
   * @return Optional<> reference to updated {{cookiecutter.SUB_RESOURCE_NAME}} resource
  */
  @Override
  public Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> update{{cookiecutter.SUB_RESOURCE_NAME}}(String id, String subResourceId, {{cookiecutter.SUB_RESOURCE_NAME}} subResource) throws RequestValidationException {
    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> resource = mapper.toModel(
        subResourceRepository.findById(subResourceId)
            .map((obj) -> mapper.updateMetadata(record, obj))
            .map((obj) -> repository.save(obj)));

    return resource;
    }

  /**
   * delete a {{cookiecutter.SUB_RESOURCE_NAME}} resource based on id
   *
   * @param id {{cookiecutter.RESOURCE_NAME}} resource id
   * @param subResourceId {{cookiecutter.SUB_RESOURCE_NAME}} resource id
   * @return subResource {{cookiecutter.SUB_RESOURCE_NAME}} resource data
   */
  @Override
  public Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> delete{{cookiecutter.SUB_RESOURCE_NAME}}(String id, String subResourceId) {
    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> result = get{{cookiecutter.SUB_RESOURCE_NAME}}(id, subResourceId);
    {{cookiecutter.SUB_RESOURCE_NAME}}Repository.deleteById(subResourceId);
    return result;
  }
{%- endif %}
}
