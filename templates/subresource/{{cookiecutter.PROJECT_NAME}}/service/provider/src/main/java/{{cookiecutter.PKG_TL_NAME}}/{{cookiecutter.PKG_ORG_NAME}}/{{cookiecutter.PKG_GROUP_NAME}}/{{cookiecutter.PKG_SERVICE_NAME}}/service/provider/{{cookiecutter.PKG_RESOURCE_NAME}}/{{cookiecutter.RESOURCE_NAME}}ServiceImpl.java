package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.provider.{{cookiecutter.PKG_RESOURCE_NAME}};

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.exception.RequestValidationException;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.persistence.model.{{cookiecutter.RESOURCE_NAME}}EntityRepository;
{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.{{cookiecutter.SUB_RESOURCE_NAME}}Service;
{%- endif %}
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.provider.{{cookiecutter.PKG_RESOURCE_NAME}}.mapper.{{cookiecutter.RESOURCE_NAME}}EntityMapper;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.{{cookiecutter.RESOURCE_NAME}}Service;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.RESOURCE_NAME}};
{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}};
{%- endif %}
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class {{cookiecutter.RESOURCE_NAME}}ServiceImpl implements {{cookiecutter.RESOURCE_NAME}}Service {

  private {{cookiecutter.RESOURCE_NAME}}EntityRepository repository;
  private {{cookiecutter.RESOURCE_NAME}}EntityMapper mapper;

{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
  private {{cookiecutter.SUB_RESOURCE_NAME}}Service subResourceService;
{%- endif %}

  {{cookiecutter.RESOURCE_NAME}}ServiceImpl(
      {{cookiecutter.RESOURCE_NAME}}EntityRepository repository,
      {{cookiecutter.RESOURCE_NAME}}EntityMapper mapper
{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %},
      {{cookiecutter.SUB_RESOURCE_NAME}}Service subResourceService
{%- endif -%}
    ) {
    this.repository = repository;
    this.mapper = mapper;
    {%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
    this.subResourceService = subResourceService;
    {%- endif %}
  }

  /**
   * add a new {{cookiecutter.RESOURCE_NAME}} entity.
   *
   * @param resource resource info to add (id should be null)
   * @return new resource object with valid id
   */
  @Override
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
  @Override
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
  @Override
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

{%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %}
  @Override
  public Page<{{cookiecutter.RESOURCE_NAME}}> findAllBy{{cookiecutter.PARENT_RESOURCE_NAME}}Id(String id, Pageable pageable) {
    Page<{{cookiecutter.RESOURCE_NAME}}> resource = mapper.toModelPage(repository.findAllBy{{cookiecutter.PARENT_RESOURCE_NAME}}Id(id, pageable));
    return resource;
  }
{%- endif %}

  @Override
  // CSOFF: LineLength
  public Optional<{{cookiecutter.RESOURCE_NAME}}> updateById(String id, {{cookiecutter.RESOURCE_NAME}} record)
      // CSON: LineLength
      throws RequestValidationException {
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

{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}

  /**
   * add a new {{cookiecutter.SUB_RESOURCE_NAME}} entity.
   *
   * @param id {{cookiecutter.RESOURCE_NAME}} resource id
   * @param subResource resource info to add (id should be null)
   * @return new resource object with valid id
   */
  @Override
  // CSOFF: LineLength
  public {{cookiecutter.SUB_RESOURCE_NAME}} add{{cookiecutter.SUB_RESOURCE_NAME}}(String id, {{cookiecutter.SUB_RESOURCE_NAME}} subResource)
      // CSON: LineLength
      throws RequestValidationException {
    {{cookiecutter.SUB_RESOURCE_NAME}} result =
        mapper.fromService{{cookiecutter.SUB_RESOURCE_NAME}}(
            subResourceService.add(mapper.toService{{cookiecutter.SUB_RESOURCE_NAME}}(subResource, id)));
    return result;
  }

  /**
   * find a {{cookiecutter.SUB_RESOURCE_NAME}} resource by resource id.
   *
   * @param id {{cookiecutter.RESOURCE_NAME}} resource id
   * @param subResourceId id of the {{cookiecutter.SUB_RESOURCE_NAME}}
   * @return matching record, or null
   */
  @Override
  // CSOFF: LineLength
  public Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> get{{cookiecutter.SUB_RESOURCE_NAME}}(String id, String subResourceId) {
    // CSON: LineLength
    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> result =
        mapper.fromService{{cookiecutter.SUB_RESOURCE_NAME}}(
            subResourceService
                .findById(subResourceId)
                // TODO: In lieu of JPA Specifications, we filter the result based on matching
                // parent resource
                .filter((r) -> ((null != r.get{{cookiecutter.RESOURCE_NAME}}Id()) && r.get{{cookiecutter.RESOURCE_NAME}}Id().equals(id))));
    return result;
  }

  /**
   * find all {{cookiecutter.SUB_RESOURCE_NAME}} resources related to {{cookiecutter.RESOURCE_NAME}}.
   *
   * @param id {{cookiecutter.RESOURCE_NAME}} resource id
   * @return list of {{cookiecutter.SUB_RESOURCE_NAME}} resources
   */
  @Override
  // CSOFF: LineLength
  public Page<{{cookiecutter.SUB_RESOURCE_NAME}}> get{{cookiecutter.SUB_RESOURCE_NAME}}s(String id, Pageable pageable) {
    // CSON: LineLength
    Page<{{cookiecutter.SUB_RESOURCE_NAME}}> resources =
        mapper.fromService{{cookiecutter.SUB_RESOURCE_NAME}}Page(subResourceService.findAllBy{{cookiecutter.RESOURCE_NAME}}Id(id, pageable));
    return resources;
  }

  /**
   * update a {{cookiecutter.SUB_RESOURCE_NAME}} resource based on id.
   *
   * @param id {{cookiecutter.RESOURCE_NAME}} resource id
   * @param subResourceId {{cookiecutter.SUB_RESOURCE_NAME}} resource id
   * @param record {{cookiecutter.SUB_RESOURCE_NAME}} resource data
   * @return Optional<> reference to updated {{cookiecutter.SUB_RESOURCE_NAME}} resource
   */
  @Override
  // CSOFF: LineLength
  public Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> update{{cookiecutter.SUB_RESOURCE_NAME}}(String id, String subResourceId, {{cookiecutter.SUB_RESOURCE_NAME}} record)
      // CSON: LineLength
      throws RequestValidationException {
    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> resource =
        mapper.fromService{{cookiecutter.SUB_RESOURCE_NAME}}(
            subResourceService.updateById(subResourceId, mapper.toService{{cookiecutter.SUB_RESOURCE_NAME}}(record, id)));

    return resource;
  }

  /**
   * delete a {{cookiecutter.SUB_RESOURCE_NAME}} resource based on id.
   *
   * @param id {{cookiecutter.RESOURCE_NAME}} resource id
   * @param subResourceId {{cookiecutter.SUB_RESOURCE_NAME}} resource id
   * @return subResource {{cookiecutter.SUB_RESOURCE_NAME}} resource data
   */
  @Override
  // CSOFF: LineLength
  public Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> delete{{cookiecutter.SUB_RESOURCE_NAME}}(String id, String subResourceId) {
    // CSON: LineLength
    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> result =
        mapper.fromService{{cookiecutter.SUB_RESOURCE_NAME}}(subResourceService.deleteById(subResourceId));
    return result;
  }
{%- endif %}
}
