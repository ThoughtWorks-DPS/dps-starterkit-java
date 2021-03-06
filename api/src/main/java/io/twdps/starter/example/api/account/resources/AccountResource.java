package io.twdps.starter.example.api.account.resources;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.twdps.starter.boot.exception.RequestValidationException;
import io.twdps.starter.boot.exception.ResourceNotFoundException;
import io.twdps.starter.example.api.account.requests.AccountRequest;
import io.twdps.starter.example.api.account.responses.AccountResponse;
import io.twdps.starter.example.api.responses.ArrayResponse;
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

@RequestMapping(value = "/v1/example/accounts", produces = "application/json")
@Tag(name = "Account API", description = "Starter Kit template API, essentially CRUD access")
@SecurityRequirement(name = "oauth2")
public interface AccountResource {

  @Operation(summary = "Create a new account")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Created a new account",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = AccountResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid data provided",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class)))
      })
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  ResponseEntity<AccountResponse> addEntity(
      @Parameter(
              description = "metadata for new Account resource. Cannot null or empty.",
              required = true,
              schema = @Schema(implementation = AccountRequest.class))
          @Valid
          @RequestBody
          AccountRequest request)
      throws RequestValidationException;

  @Operation(summary = "Find an account based on accountId")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the account",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = AccountResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid id supplied",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Not authorized",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class)))
      })
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<AccountResponse> findEntityById(
      @Parameter(
              description = "unique identifier for Account resource. Cannot null or empty.",
              example = "uuid",
              required = true)
          @NotNull
          @PathVariable(value = "id")
          String id)
      throws ResourceNotFoundException;

  @Operation(summary = "Get all accounts")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "All existing accounts",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema =
                      @Schema(
                          implementation = ArrayResponse.class,
                          subTypes = {AccountResponse.class}))
            })
      })
  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<ArrayResponse<AccountResponse>> findEntities();

  @Operation(summary = "Update an existing account")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Updated account info",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = AccountResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid id supplied",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Account not found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class)))
      })
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<AccountResponse> updateEntityById(
      @Parameter(
              description = "unique identifier for Account resource. Cannot be null or empty",
              example = "uuid",
              required = true)
          @NotNull
          @PathVariable(value = "id")
          String id,
      @Parameter(
              description = "updated metadata Account resource. Cannot null or empty.",
              required = true,
              schema = @Schema(implementation = AccountRequest.class))
          @Valid
          @RequestBody
          AccountRequest request)
      throws ResourceNotFoundException, RequestValidationException;

  @Operation(summary = "Delete an existing account")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Deleted account info",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = AccountResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid id supplied",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Account not found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class)))
      })
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<AccountResponse> deleteEntityById(
      @Parameter(
              description = "unique identifier for Account resource. Cannot null or empty.",
              example = "uuid",
              required = true)
          @NotNull
          @PathVariable(value = "id")
          String id)
      throws ResourceNotFoundException;
}
