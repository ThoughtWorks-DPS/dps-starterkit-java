package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.controller.{{cookiecutter.PKG_RESOURCE_NAME}};

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.boot.exception.RequestValidationException;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.boot.exception.ResourceNotFoundException;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.requests.{{cookiecutter.RESOURCE_NAME}}Request;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.resources.{{cookiecutter.RESOURCE_NAME}}Resource;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.responses.{{cookiecutter.RESOURCE_NAME}}Response;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.responses.ArrayResponse;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.controller.{{cookiecutter.PKG_RESOURCE_NAME}}.mapper.{{cookiecutter.RESOURCE_NAME}}RequestMapper;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.{{cookiecutter.RESOURCE_NAME}}Service;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.RESOURCE_NAME}};
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class {{cookiecutter.RESOURCE_NAME}}Controller implements {{cookiecutter.RESOURCE_NAME}}Resource {

  private final {{cookiecutter.RESOURCE_NAME}}Service manager;
  private final {{cookiecutter.RESOURCE_NAME}}RequestMapper mapper;

  /**
   * constructor.
   *
   * @param manager instance of account manager
   * @param mapper instance of account request mappper
   */
  public {{cookiecutter.RESOURCE_NAME}}Controller({{cookiecutter.RESOURCE_NAME}}Service manager, {{cookiecutter.RESOURCE_NAME}}RequestMapper mapper) {
    this.manager = manager;
    this.mapper = mapper;
  }

  @Override
  public ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> addEntity({{cookiecutter.RESOURCE_NAME}}Request addEntityRequest)
      throws RequestValidationException {

    log.info("username->{}", addEntityRequest.getUserName());
    {{cookiecutter.RESOURCE_NAME}} resource = mapper.toModel(addEntityRequest);
    {{cookiecutter.RESOURCE_NAME}} saved = manager.add(resource);
    {{cookiecutter.RESOURCE_NAME}}Response response = mapper.to{{cookiecutter.RESOURCE_NAME}}Response(saved);
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
  public ResponseEntity<ArrayResponse<{{cookiecutter.RESOURCE_NAME}}Response>> findEntities() {
    List<{{cookiecutter.RESOURCE_NAME}}> resources = manager.findAll();

    return new ResponseEntity<>(
        new ArrayResponse<>(mapper.to{{cookiecutter.RESOURCE_NAME}}ResponseList(resources)), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> updateEntityById(String id, {{cookiecutter.RESOURCE_NAME}}Request request)
      throws ResourceNotFoundException, RequestValidationException {

    log.info("id->{}", id);
    Optional<{{cookiecutter.RESOURCE_NAME}}> found = manager.updateById(id, mapper.toModel(request));
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
    return new ResponseEntity<>(
        found
            .map(r -> mapper.to{{cookiecutter.RESOURCE_NAME}}Response(r))
            .orElseThrow(() -> new ResourceNotFoundException(id)),
        HttpStatus.OK);
  }


{%- if cookiecutter.CREATE_SUBRESOURCE == "y" %}
  Callback add{{cookiecutter.SUB_RESOURCE_NAME}}(String id, {{cookiecutter.SUB_RESOURCE_NAME}} subResource);
      throws ResourceNotFoundException {

    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }

  List<{{cookiecutter.SUB_RESOURCE_NAME}}> get{{cookiecutter.SUB_RESOURCE_NAME}}s(String id);

  Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> get{{cookiecutter.SUB_RESOURCE_NAME}}(String id, String subResourceId);

  Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> update{{cookiecutter.SUB_RESOURCE_NAME}}(String id, String subResourceId, {{cookiecutter.SUB_RESOURCE_NAME}} subResource);

  Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> delete{{cookiecutter.SUB_RESOURCE_NAME}}(String id, String subResourceId)
      throws ResourceNotFoundException {

    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }
{%- endif %}
}
