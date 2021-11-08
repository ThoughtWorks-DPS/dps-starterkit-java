{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.resources;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.exception.RequestValidationException;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.exception.ResourceNotFoundException;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.requests.{{cookiecutter.SUB_RESOURCE_NAME}}Request;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.responses.Paged{{cookiecutter.SUB_RESOURCE_NAME}}Response;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.responses.{{cookiecutter.SUB_RESOURCE_NAME}}Response;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.responses.PagedResponse;
import org.springframework.data.domain.Pageable;
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
import org.zalando.problem.Problem;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RequestMapping(value = "/v1/{{cookiecutter.SERVICE_URL}}/{{cookiecutter.RESOURCE_URL}}", produces = "application/json")
@Tag(name = "{{cookiecutter.RESOURCE_NAME}} API", description = "Starter Kit template API, essentially CRUD access")
@SecurityRequirement(name = "bearer-jwt")
@Timed
public interface {{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}Resource {

  @Operation(summary = "Find a specific {{cookiecutter.SUB_RESOURCE_NAME}} based on entity identifier")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Created a new {{cookiecutter.SUB_RESOURCE_NAME}}",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = {{cookiecutter.SUB_RESOURCE_NAME}}Response.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid data provided",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Not authorized",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class)))
      })
  @PostMapping("/{id}/{{cookiecutter.SUB_RESOURCE_URL}}")
  @ResponseStatus(HttpStatus.CREATED)
  @Timed(value = "{{cookiecutter.SERVICE_URL}}.{{cookiecutter.RESOURCE_URL}}.{{cookiecutter.SUB_RESOURCE_URL}}.create")
  ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> addSubEntity(
      @Parameter(
              description = "unique identifier for {{cookiecutter.RESOURCE_NAME}} resource. Cannot null or empty.",
              example = "uuid",
              required = true)
          @NotNull
          @PathVariable(value = "id")
          String id,
      @Parameter(
              description = "metadata for new {{cookiecutter.SUB_RESOURCE_NAME}} resource. Cannot null or empty.",
              required = true,
              schema = @Schema(implementation = {{cookiecutter.SUB_RESOURCE_NAME}}Request.class))
          @Valid
          @RequestBody
          {{cookiecutter.SUB_RESOURCE_NAME}}Request request)
      throws RequestValidationException;

  @Operation(summary = "Find a specific {{cookiecutter.SUB_RESOURCE_NAME}} based on entity identifier")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the {{cookiecutter.SUB_RESOURCE_NAME}}",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = {{cookiecutter.SUB_RESOURCE_NAME}}Response.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid id supplied",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Not authorized",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class)))
      })
  @GetMapping("/{id}/{{cookiecutter.SUB_RESOURCE_URL}}/{subResourceId}")
  @ResponseStatus(HttpStatus.OK)
  @Timed(value = "{{cookiecutter.SERVICE_URL}}.{{cookiecutter.RESOURCE_URL}}.{{cookiecutter.SUB_RESOURCE_URL}}.findById")
  ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> getSubEntity(
      @Parameter(
              description = "unique identifier for {{cookiecutter.RESOURCE_NAME}} resource. Cannot null or empty.",
              example = "uuid",
              required = true)
          @NotNull
          @PathVariable(value = "id")
          String id,
      @Parameter(
              description = "unique identifier for {{cookiecutter.SUB_RESOURCE_NAME}} resource. Cannot null or empty.",
              example = "uuid",
              required = true)
          @NotNull
          @PathVariable(value = "subResourceId")
          String subResourceId)
      throws ResourceNotFoundException;

  @Operation(summary = "Get all {{cookiecutter.SUB_RESOURCE_NAME}}s related to a specific {{cookiecutter.RESOURCE_NAME}}")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "All existing {{cookiecutter.SUB_RESOURCE_NAME}}s",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema =
                  @Schema(implementation = Paged{{cookiecutter.SUB_RESOURCE_NAME}}Response.class))
            }),
        @ApiResponse(
            responseCode = "401",
            description = "Not authorized",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class)))
      })
  @GetMapping("/{id}/{{cookiecutter.SUB_RESOURCE_URL}}")
  @ResponseStatus(HttpStatus.OK)
  @Timed(value = "{{cookiecutter.SERVICE_URL}}.{{cookiecutter.RESOURCE_URL}}.{{cookiecutter.SUB_RESOURCE_URL}}.findAll")
  ResponseEntity<PagedResponse<{{cookiecutter.SUB_RESOURCE_NAME}}Response>> getSubEntities(
      @Parameter(
              description = "unique identifier for {{cookiecutter.RESOURCE_NAME}} resource. Cannot null or empty.",
              example = "uuid",
              required = true)
          @NotNull
          @PathVariable(value = "id")
          String id,
      @Parameter(
              description = "Paging specification for retrieving a subset of the full list.",
              required = false)
          Pageable pageable);

  @Operation(summary = "Update an existing {{cookiecutter.SUB_RESOURCE_NAME}}")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Updated {{cookiecutter.SUB_RESOURCE_NAME}} info",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = {{cookiecutter.SUB_RESOURCE_NAME}}Response.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid id supplied",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Not authorized",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class))),
        @ApiResponse(
            responseCode = "404",
            description = "{{cookiecutter.RESOURCE_NAME}} not found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class)))
      })
  @PutMapping("/{id}/{{cookiecutter.SUB_RESOURCE_URL}}/{subResourceId}")
  @ResponseStatus(HttpStatus.OK)
  @Timed(value = "{{cookiecutter.SERVICE_URL}}.{{cookiecutter.RESOURCE_URL}}.{{cookiecutter.SUB_RESOURCE_URL}}.update")
  ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> updateSubEntity(
      @Parameter(
              description = "unique identifier for {{cookiecutter.RESOURCE_NAME}} resource. Cannot null or empty.",
              example = "uuid",
              required = true)
          @NotNull
          @PathVariable(value = "id")
          String id,
      @Parameter(
              description = "unique identifier for {{cookiecutter.SUB_RESOURCE_NAME}} resource. Cannot null or empty.",
              example = "uuid",
              required = true)
          @NotNull
          @PathVariable(value = "subResourceId")
          String subResourceId,
      @Parameter(
              description = "metadata for new {{cookiecutter.SUB_RESOURCE_NAME}} resource. Cannot null or empty.",
              required = true,
              schema = @Schema(implementation = {{cookiecutter.SUB_RESOURCE_NAME}}Request.class))
          @Valid
          @RequestBody
          {{cookiecutter.SUB_RESOURCE_NAME}}Request request)
      throws ResourceNotFoundException, RequestValidationException;

  @Operation(summary = "Delete an existing {{cookiecutter.SUB_RESOURCE_NAME}}")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Deleted {{cookiecutter.SUB_RESOURCE_NAME}} info",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = {{cookiecutter.SUB_RESOURCE_NAME}}Response.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid id supplied",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Not authorized",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class)))
      })
  @DeleteMapping("/{id}/{{cookiecutter.SUB_RESOURCE_URL}}/{subResourceId}")
  @ResponseStatus(HttpStatus.OK)
  @Timed(value = "{{cookiecutter.SERVICE_URL}}.{{cookiecutter.RESOURCE_URL}}.{{cookiecutter.SUB_RESOURCE_URL}}.delete")
  ResponseEntity<{{cookiecutter.SUB_RESOURCE_NAME}}Response> deleteSubEntity(
      @Parameter(
              description = "unique identifier for {{cookiecutter.RESOURCE_NAME}} resource. Cannot null or empty.",
              example = "uuid",
              required = true)
          @NotNull
          @PathVariable(value = "id")
          String id,
      @Parameter(
              description = "unique identifier for {{cookiecutter.SUB_RESOURCE_NAME}} resource. Cannot null or empty.",
              example = "uuid",
              required = true)
          @NotNull
          @PathVariable(value = "subResourceId")
          String subResourceId)
      throws ResourceNotFoundException;
}
{%- endif %}
