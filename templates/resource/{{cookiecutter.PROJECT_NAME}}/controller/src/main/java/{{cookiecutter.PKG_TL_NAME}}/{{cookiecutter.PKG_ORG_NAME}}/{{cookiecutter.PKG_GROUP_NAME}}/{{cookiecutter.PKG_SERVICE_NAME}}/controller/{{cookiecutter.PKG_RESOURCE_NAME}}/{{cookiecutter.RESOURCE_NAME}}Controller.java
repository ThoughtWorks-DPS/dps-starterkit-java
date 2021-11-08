package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.controller.{{cookiecutter.PKG_RESOURCE_NAME}};

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.exception.RequestValidationException;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.exception.ResourceNotFoundException;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.notifier.lifecycle.entity.spi.EntityLifecycleNotifier;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.requests.{{cookiecutter.RESOURCE_NAME}}Request;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.resources.{{cookiecutter.RESOURCE_NAME}}Resource;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.responses.{{cookiecutter.RESOURCE_NAME}}Response;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.responses.PagedResponse;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.controller.{{cookiecutter.PKG_RESOURCE_NAME}}.mapper.{{cookiecutter.RESOURCE_NAME}}RequestMapper;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.{{cookiecutter.RESOURCE_NAME}}Service;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.RESOURCE_NAME}};
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
public class {{cookiecutter.RESOURCE_NAME}}Controller implements {{cookiecutter.RESOURCE_NAME}}Resource {

  private final {{cookiecutter.RESOURCE_NAME}}Service manager;
  private final {{cookiecutter.RESOURCE_NAME}}RequestMapper mapper;
  private final EntityLifecycleNotifier notifier;
  // TODO: Need to find a better way to determine version of entity
  private final String entityVersion = "0.0.1";

  /**
   * constructor.
   *
   * @param manager instance of {{cookiecutter.RESOURCE_NAME}} manager
   * @param mapper instance of {{cookiecutter.RESOURCE_NAME}} request mappper
   */
  public {{cookiecutter.RESOURCE_NAME}}Controller(
      {{cookiecutter.RESOURCE_NAME}}Service manager, {{cookiecutter.RESOURCE_NAME}}RequestMapper mapper, EntityLifecycleNotifier notifier) {
    this.manager = manager;
    this.mapper = mapper;
    this.notifier = notifier;
  }

  @Override
  public ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> addEntity({{cookiecutter.RESOURCE_NAME}}Request addEntityRequest)
      throws RequestValidationException {

    log.info("username->{}", addEntityRequest.getUserName());
    {{cookiecutter.RESOURCE_NAME}} resource = mapper.toModel(addEntityRequest);
    {{cookiecutter.RESOURCE_NAME}} saved = manager.add(resource);
    {{cookiecutter.RESOURCE_NAME}}Response response = mapper.to{{cookiecutter.RESOURCE_NAME}}Response(saved);
    notifier.created(saved, entityVersion, URI.create("user:anonymous"));
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> findEntityById(String id)
      throws ResourceNotFoundException {

    log.info("id->{}", id);
    Optional<{{cookiecutter.RESOURCE_NAME}}> found = manager.findById(id);
    return new ResponseEntity<>(
        found
            .map(r -> mapper.to{{cookiecutter.RESOURCE_NAME}}Response(r))
            .orElseThrow(() -> new ResourceNotFoundException(id)),
        HttpStatus.OK);
  }

  @Override
  public ResponseEntity<PagedResponse<{{cookiecutter.RESOURCE_NAME}}Response>> findEntities(Pageable pageable) {
    Page<{{cookiecutter.RESOURCE_NAME}}> resources = manager.findAll(pageable);

    return new ResponseEntity<>(mapper.to{{cookiecutter.RESOURCE_NAME}}ResponsePage(resources), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> updateEntityById(String id, {{cookiecutter.RESOURCE_NAME}}Request request)
      throws ResourceNotFoundException, RequestValidationException {

    log.info("id->{}", id);
    Optional<{{cookiecutter.RESOURCE_NAME}}> found = manager.updateById(id, mapper.toModel(request));
    if (found.isPresent()) {
      notifier.updated(found.get(), entityVersion, URI.create("user:anonymous"));
    }
    return new ResponseEntity<>(
        found
            .map(r -> mapper.to{{cookiecutter.RESOURCE_NAME}}Response(r))
            .orElseThrow(() -> new ResourceNotFoundException(id)),
        HttpStatus.OK);
  }

  @Override
  public ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> deleteEntityById(String id)
      throws ResourceNotFoundException {

    log.info("id->{}", id);
    Optional<{{cookiecutter.RESOURCE_NAME}}> found = manager.deleteById(id);
    if (found.isPresent()) {
      notifier.deleted(found.get(), entityVersion, URI.create("user:anonymous"));
    }
    return new ResponseEntity<>(
        found
            .map(r -> mapper.to{{cookiecutter.RESOURCE_NAME}}Response(r))
            .orElseThrow(() -> new ResourceNotFoundException(id)),
        HttpStatus.OK);
  }


}
