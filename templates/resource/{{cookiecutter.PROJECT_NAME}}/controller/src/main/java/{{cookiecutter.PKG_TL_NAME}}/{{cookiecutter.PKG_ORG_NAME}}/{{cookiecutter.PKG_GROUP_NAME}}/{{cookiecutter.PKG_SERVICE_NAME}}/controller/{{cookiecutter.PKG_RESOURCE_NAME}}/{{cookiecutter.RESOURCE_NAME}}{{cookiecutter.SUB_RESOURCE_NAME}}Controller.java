{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.controller.{{cookiecutter.PKG_RESOURCE_NAME}};

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.exception.RequestValidationException;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.exception.ResourceNotFoundException;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.notifier.lifecycle.entity.spi.EntityLifecycleNotifier;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.resources.{{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}Resource;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.controller.{{cookiecutter.PKG_RESOURCE_NAME}}.mapper.{{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}RequestMapper;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.requests.{{cookiecutter.SUB_RESOURCE_NAME}}Request;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.responses.{{cookiecutter.SUB_RESOURCE_NAME}}Response;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.responses.PagedResponse;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}};
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.{{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Optional;

@Slf4j
@RestController
public class {{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}Controller implements {{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}Resource {

  private final {{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}Service manager;
  private final {{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}RequestMapper mapper;
  private final EntityLifecycleNotifier notifier;
  // TODO: Need to find a better way to determine version of entity
  private final String entityVersion = "0.0.1";

  /**
   * constructor.
   *
   * @param manager instance of {{cookiecutter.RESOURCE_NAME}} manager
   * @param mapper instance of {{cookiecutter.RESOURCE_NAME}} request mappper
   */
  public {{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}Controller(
    {{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}Service manager, {{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}RequestMapper mapper, EntityLifecycleNotifier notifier) {
    this.manager = manager;
    this.mapper = mapper;
    this.notifier = notifier;
  }

  @Override
  // CSOFF: LineLength
  public ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> addSubEntity(
      String id, {{cookiecutter.SUB_RESOURCE_NAME}}Request addEntityRequest)
      // CSON: LineLength
      throws RequestValidationException {

    log.info("username->{}", addEntityRequest.getUserName());
    {{cookiecutter.SUB_RESOURCE_NAME}} resource = mapper.toModel(addEntityRequest);
    {{cookiecutter.SUB_RESOURCE_NAME}} saved = manager.add(id, resource);
    {{cookiecutter.SUB_RESOURCE_NAME}}Response response = mapper.to{{cookiecutter.SUB_RESOURCE_NAME}}Response(saved);
    notifier.created(saved, entityVersion, URI.create("user:anonymous"));
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @Override
  // CSOFF: LineLength
  public ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> getSubEntity(String id, String subResourceId)
      // CSON: LineLength
      throws ResourceNotFoundException {

    log.info("id->{}", id);
    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> found = manager.findById(id, subResourceId);
    return new ResponseEntity<>(
        found
            .map(r -> mapper.to{{cookiecutter.SUB_RESOURCE_NAME}}Response(r))
            .orElseThrow(() -> new ResourceNotFoundException(id)),
        HttpStatus.OK);
  }

  @Override
  // CSOFF: LineLength
  public ResponseEntity<PagedResponse<{{cookiecutter.SUB_RESOURCE_NAME}}Response>> getSubEntities(
      String id, Pageable pageable) {
    // CSON: LineLength
    Page<{{cookiecutter.SUB_RESOURCE_NAME}}> resources = manager.findAll(id, pageable);

    return new ResponseEntity<>(mapper.to{{cookiecutter.SUB_RESOURCE_NAME}}ResponsePage(resources), HttpStatus.OK);
  }

  @Override
  // CSOFF: LineLength
  public ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> updateSubEntity(
      String id, String subResourceId, {{cookiecutter.SUB_RESOURCE_NAME}}Request request)
      // CSON: LineLength
      throws ResourceNotFoundException, RequestValidationException {

    log.info("id->{}", id);
    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> found = manager.updateById(id, subResourceId, mapper.toModel(request));
    if (found.isPresent()) {
      notifier.updated(found.get(), entityVersion, URI.create("user:anonymous"));
    }
    return new ResponseEntity<>(
        found
            .map(r -> mapper.to{{cookiecutter.SUB_RESOURCE_NAME}}Response(r))
            .orElseThrow(() -> new ResourceNotFoundException(id)),
        HttpStatus.OK);
  }

  @Override
  // CSOFF: LineLength
  public ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> deleteSubEntity(String id, String subResourceId)
      // CSON: LineLength
      throws ResourceNotFoundException {

    log.info("id->{}", id);
    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> found = manager.deleteById(id, subResourceId);
    if (found.isPresent()) {
      notifier.deleted(found.get(), entityVersion, URI.create("user:anonymous"));
    }
    return new ResponseEntity<>(
        found
            .map(r -> mapper.to{{cookiecutter.SUB_RESOURCE_NAME}}Response(r))
            .orElseThrow(() -> new ResourceNotFoundException(id)),
        HttpStatus.OK);
  }

}
{%- endif %}
