package io.twdps.starter.example.api.subaccount.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@AllArgsConstructor
// @RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Schema(
    name = "SubAccountResponse",
    description = "Metadata describing an SubAccount resource and unique identifier")
public class SubAccountResponse {

  // TODO: Refactor SubAccountResponse by renaming variable names, migrate types
  @NonNull
  @Schema(
      description = "unique id of the SubAccount resource",
      example = "dd373780-79fb-4285-8c9b-bf48a8014a68")
  private final String id;

  @NonNull
  @Schema(description = "username of the SubAccount holder", example = "lvanpelt")
  private final String userName;

  @NonNull
  @Schema(description = "Representative PII of the SubAccount holder", example = "123-456-7890")
  private final String pii;

  @NonNull
  @Schema(description = "Given name of the SubAccount holder", example = "Lucy")
  private final String firstName;

  @NonNull
  @Schema(description = "Family name of the SubAccount holder", example = "van Pelt")
  private final String lastName;

  @NonNull
  @Schema(
      description = "Constructed full name (given + family) of the SubAccount holder",
      example = "Lucy van Pelt")
  private final String fullName;

  // TODO: Additional SubAccountResponse data elements

  @NonNull
  @Schema(
      description = "Parent accountId of the SubAccount holder",
      example = "uuid-123456789-abcd")
  private final String accountId;
}
