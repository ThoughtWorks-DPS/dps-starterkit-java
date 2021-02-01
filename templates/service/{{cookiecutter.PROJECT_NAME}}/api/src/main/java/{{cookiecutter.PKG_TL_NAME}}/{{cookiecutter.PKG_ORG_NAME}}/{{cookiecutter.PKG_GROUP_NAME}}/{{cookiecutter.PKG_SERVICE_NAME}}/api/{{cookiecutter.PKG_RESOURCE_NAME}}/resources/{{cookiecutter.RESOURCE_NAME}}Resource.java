package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.resources;

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.requests.{{cookiecutter.RESOURCE_NAME}}Request;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.responses.{{cookiecutter.RESOURCE_NAME}}Response;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.responses.Add{{cookiecutter.RESOURCE_NAME}}Response;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.responses.ArrayResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping(value = "/v1/{{cookiecutter.SERVICE_URL}}", produces = "application/json")
public interface {{cookiecutter.RESOURCE_NAME}}Resource {

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  ResponseEntity<Add{{cookiecutter.RESOURCE_NAME}}Response> addEntity(@RequestBody {{cookiecutter.RESOURCE_NAME}}Request request);

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> findEntityById(@PathVariable(value = "id") String id);

  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<ArrayResponse<{{cookiecutter.RESOURCE_NAME}}Response>> findEntities();

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> updateEntityById(@PathVariable(value = "id") String id,
                                                         @RequestBody {{cookiecutter.RESOURCE_NAME}}Request request);

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<{{cookiecutter.RESOURCE_NAME}}Response> deleteEntityById(@PathVariable(value = "id") String id);

  // TODO: Need to provide the sub-resource endpoints
  @PostMapping("/{id}/{{cookiecutter.SUB_RESOURCE_URL}}")
  @ResponseStatus(HttpStatus.CREATED)
  ResponseEntity<CallbackResponse> add{{cookiecutter.SUB_RESOURCE_NAME}}(@PathVariable(value = "id") String id,
                                               @RequestBody Add{{cookiecutter.SUB_RESOURCE_NAME}}Request add{{cookiecutter.SUB_RESOURCE_NAME}}Request);

  @GetMapping("/{id}/{{cookiecutter.SUB_RESOURCE_URL}}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<ArrayResponse<{{cookiecutter.SUB_RESOURCE_NAME}}Response>> get{{cookiecutter.SUB_RESOURCE_NAME}}s(@PathVariable(value = "id") String id);

  @GetMapping("/{id}/{{cookiecutter.SUB_RESOURCE_URL}}/{{{cookiecutter.SUB_RESOURCE_URL}}Id}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> get{{cookiecutter.SUB_RESOURCE_NAME}}(@PathVariable(value = "id") String id,
                                               @PathVariable(value = "{{cookiecutter.SUB_RESOURCE_URL}}Id") String {{cookiecutter.SUB_RESOURCE_URL}}Id);

  @PutMapping("/{id}/{{cookiecutter.SUB_RESOURCE_URL}}/{{{cookiecutter.SUB_RESOURCE_URL}}Id}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<ArrayResponse<{{cookiecutter.SUB_RESOURCE_NAME}}Response>> update{{cookiecutter.SUB_RESOURCE_NAME}}(@PathVariable(value = "id") String id,
                                                                 @PathVariable(value = "{{cookiecutter.SUB_RESOURCE_URL}}Id") String {{cookiecutter.SUB_RESOURCE_URL}}Id);

  @DeleteMapping("/{id}/{{cookiecutter.SUB_RESOURCE_URL}}/{{{cookiecutter.SUB_RESOURCE_URL}}Id}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<ArrayResponse<{{cookiecutter.SUB_RESOURCE_NAME}}Response>> delete{{cookiecutter.SUB_RESOURCE_NAME}}(@PathVariable(value = "id") String id,
                                                                 @PathVariable(value = "{{cookiecutter.SUB_RESOURCE_URL}}Id") String {{cookiecutter.SUB_RESOURCE_URL}}Id);

}
