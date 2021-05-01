package io.twdps.starter.example.api.account.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
//@RequiredArgsConstructor
@Getter
@Schema(
    name = "SubAccountResponse",
    description = "Metadata describing an Account resource and unique identifier")
public class SubAccountResponse {

  @NonNull
  @Schema(description = "unique id of the SubAccount resource")
  private final String id;

  @NonNull
  @Schema(description = "username of the SubAccount holder")
  private final String userName;

  @NonNull
  @Schema(description = "Given name of the SubAccount holder")
  private final String firstName;

  @NonNull
  @Schema(description = "Family name of the SubAccount holder")
  private final String lastName;
}
