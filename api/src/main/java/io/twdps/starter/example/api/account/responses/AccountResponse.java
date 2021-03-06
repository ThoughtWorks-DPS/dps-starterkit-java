package io.twdps.starter.example.api.account.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
// @RequiredArgsConstructor
@Getter
@Schema(
    name = "AccountResponse",
    description = "Metadata describing an Account resource and unique identifier")
public class AccountResponse {

  @NonNull
  @Schema(description = "unique id of the Account resource")
  private final String id;

  @NonNull
  @Schema(description = "username of the Account holder")
  private final String userName;

  @NonNull
  @Schema(description = "Representative PII of the Account holder")
  private final String pii;

  @NonNull
  @Schema(description = "Given name of the Account holder")
  private final String firstName;

  @NonNull
  @Schema(description = "Family name of the Account holder")
  private final String lastName;

  @NonNull
  @Schema(description = "Constructed full name (given + family) of the Account holder")
  private final String fullName;
}
