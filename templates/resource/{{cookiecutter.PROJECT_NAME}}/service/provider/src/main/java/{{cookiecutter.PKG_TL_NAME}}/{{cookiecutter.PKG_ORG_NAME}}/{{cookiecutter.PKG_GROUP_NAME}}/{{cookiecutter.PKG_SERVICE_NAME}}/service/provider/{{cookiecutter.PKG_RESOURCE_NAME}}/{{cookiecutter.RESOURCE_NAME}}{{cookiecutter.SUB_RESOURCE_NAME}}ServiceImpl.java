{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.provider.{{cookiecutter.PKG_RESOURCE_NAME}};

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.exception.RequestValidationException;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.{{cookiecutter.SUB_RESOURCE_NAME}}Service;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}};
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.{{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}Service;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.provider.{{cookiecutter.PKG_RESOURCE_NAME}}.mapper.{{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}EntityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class {{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}ServiceImpl implements {{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}Service {

  private {{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}EntityMapper mapper;
  private {{cookiecutter.SUB_RESOURCE_NAME}}Service subResourceService;

  {{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}ServiceImpl({{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}EntityMapper mapper,{{cookiecutter.SUB_RESOURCE_NAME}}Service subResourceService) {
    this.mapper = mapper;
    this.subResourceService = subResourceService;
  }

  /**
   * add a new {{cookiecutter.SUB_RESOURCE_NAME}} entity.
   *
   * @param parentId {{cookiecutter.RESOURCE_NAME}} resource id
   * @param resource resource info to add (id should be null)
   * @return new resource object with valid id
   */
  @Override
  // CSOFF: LineLength
  public {{cookiecutter.SUB_RESOURCE_NAME}} add(String parentId, {{cookiecutter.SUB_RESOURCE_NAME}} resource)
      // CSON: LineLength
      throws RequestValidationException {
    {{cookiecutter.SUB_RESOURCE_NAME}} result = mapper.fromServiceModel(subResourceService.add(mapper.toServiceModel(resource, parentId)));
    return result;
  }

  /**
   * find a {{cookiecutter.SUB_RESOURCE_NAME}} resource by resource id.
   *
   * @param parentId {{cookiecutter.RESOURCE_NAME}} resource id
   * @param id id of the {{cookiecutter.SUB_RESOURCE_NAME}}
   * @return matching record, or null
   */
  @Override
  // CSOFF: LineLength
  public Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> findById(String parentId, String id) {
    // CSON: LineLength
    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> result =
        mapper.fromServiceModel(
            subResourceService
                .findById(id)
                // TODO: In lieu of JPA Specifications, we filter the result based on matching
                // parent resource
                .filter((r) -> ((null != r.get{{cookiecutter.RESOURCE_NAME}}Id()) && r.get{{cookiecutter.RESOURCE_NAME}}Id().equals(parentId))));
    return result;
  }

  /**
   * find all {{cookiecutter.SUB_RESOURCE_NAME}} resources related to {{cookiecutter.RESOURCE_NAME}}.
   *
   * @param parentId {{cookiecutter.RESOURCE_NAME}} resource id
   * @return list of {{cookiecutter.SUB_RESOURCE_NAME}} resources
   */
  @Override
  // CSOFF: LineLength
  public Page<{{cookiecutter.SUB_RESOURCE_NAME}}> findAll(String parentId, Pageable pageable) {
    // CSON: LineLength
    Page<{{cookiecutter.SUB_RESOURCE_NAME}}> resources =
        mapper.fromServiceModelPage(subResourceService.findAllBy{{cookiecutter.RESOURCE_NAME}}Id(parentId, pageable));
    return resources;
  }

  /**
   * update a {{cookiecutter.SUB_RESOURCE_NAME}} resource based on id.
   *
   * @param parentId {{cookiecutter.RESOURCE_NAME}} resource id
   * @param id {{cookiecutter.SUB_RESOURCE_NAME}} resource id
   * @param record {{cookiecutter.SUB_RESOURCE_NAME}} resource data
   * @return Optional<> reference to updated {{cookiecutter.SUB_RESOURCE_NAME}} resource
   */
  @Override
  // CSOFF: LineLength
  public Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> updateById(String parentId, String id, {{cookiecutter.SUB_RESOURCE_NAME}} record)
      // CSON: LineLength
      throws RequestValidationException {
    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> resource =
        mapper.fromServiceModel(
            subResourceService.updateById(id, mapper.toServiceModel(record, parentId)));

    return resource;
  }

  /**
   * delete a {{cookiecutter.SUB_RESOURCE_NAME}} resource based on id.
   *
   * @param parentId {{cookiecutter.RESOURCE_NAME}} resource id
   * @param id {{cookiecutter.SUB_RESOURCE_NAME}} resource id
   * @return subResource {{cookiecutter.SUB_RESOURCE_NAME}} resource data
   */
  @Override
  // CSOFF: LineLength
  public Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> deleteById(String parentId, String id) {
    // CSON: LineLength
    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> result =
        mapper.fromServiceModel(subResourceService.deleteById(id));
    return result;
  }
}
{%- endif %}
