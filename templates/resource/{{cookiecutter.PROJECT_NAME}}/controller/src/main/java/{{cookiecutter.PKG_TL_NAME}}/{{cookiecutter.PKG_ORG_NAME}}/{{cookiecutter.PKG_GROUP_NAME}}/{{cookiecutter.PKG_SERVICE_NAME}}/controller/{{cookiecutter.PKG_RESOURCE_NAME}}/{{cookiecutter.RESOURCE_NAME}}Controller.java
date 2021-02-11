package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.controller.{{cookiecutter.PKG_RESOURCE_NAME}};

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
  public ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> addEntity({{cookiecutter.RESOURCE_NAME}}Request addEntityRequest) {

    log.info("username->{}", addEntityRequest.getUserName());
    {{cookiecutter.RESOURCE_NAME}} resource = mapper.toModel(addEntityRequest);
    {{cookiecutter.RESOURCE_NAME}} saved = manager.add(resource);
    {{cookiecutter.RESOURCE_NAME}}Response response = mapper.to{{cookiecutter.RESOURCE_NAME}}Response(saved);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> findEntityById(String id) {
    log.info("id->{}", id);
    Optional<{{cookiecutter.RESOURCE_NAME}}> found = manager.findById(id);
    if (found.isPresent()) {
      return new ResponseEntity<>(mapper.to{{cookiecutter.RESOURCE_NAME}}Response(found), HttpStatus.OK);
    } else {
      // TODO: construct error payload
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<ArrayResponse<{{cookiecutter.RESOURCE_NAME}}Response>> findEntities() {
    List<{{cookiecutter.RESOURCE_NAME}}> resources = manager.findAll();

    return new ResponseEntity<>(
        new ArrayResponse<>(mapper.to{{cookiecutter.RESOURCE_NAME}}ResponseList(resources)), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> updateEntityById(String id, {{cookiecutter.RESOURCE_NAME}}Request request) {
    log.info("id->{}", id);
    Optional<{{cookiecutter.RESOURCE_NAME}}> found = manager.updateById(id, mapper.toModel(request));
    if (found.isPresent()) {
      return new ResponseEntity<>(mapper.to{{cookiecutter.RESOURCE_NAME}}Response(found), HttpStatus.OK);
    } else {
      // TODO: construct error payload
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> deleteEntityById(String id) {
    log.info("id->{}", id);
    Optional<{{cookiecutter.RESOURCE_NAME}}> found = manager.deleteById(id);
    if (found.isPresent()) {
      return new ResponseEntity<>(mapper.to{{cookiecutter.RESOURCE_NAME}}Response(found), HttpStatus.OK);
    } else {
      // TODO: construct error payload
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }


{%- if cookiecutter.CREATE_SUBRESOURCE == "y" %}
    Callback add{{cookiecutter.SUB_RESOURCE_NAME}}(String id, {{cookiecutter.SUB_RESOURCE_NAME}} subResource);

    List<{{cookiecutter.SUB_RESOURCE_NAME}}> get{{cookiecutter.SUB_RESOURCE_NAME}}s(String id);

    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> get{{cookiecutter.SUB_RESOURCE_NAME}}(String id, String subResourceId);

    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> update{{cookiecutter.SUB_RESOURCE_NAME}}(String id, String subResourceId, {{cookiecutter.SUB_RESOURCE_NAME}} subResource);

    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> delete{{cookiecutter.SUB_RESOURCE_NAME}}(String id, String subResourceId);
{%- endif %}
}
