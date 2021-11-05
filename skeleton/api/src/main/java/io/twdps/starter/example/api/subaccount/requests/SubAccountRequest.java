package io.twdps.starter.example.api.subaccount.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(name = "SubAccountRequest", description = "Metadata describing an SubAccount resource")
public class SubAccountRequest {

  @NonNull
  @Schema(description = "username of the SubAccount holder", example = "lvanpelt")
  private final String userName;

  @NonNull
  @Schema(description = "Representative PII of the SubAccount holder", example = "123-45-6789")
  private final String pii;

  @NonNull
  @Schema(description = "Given name of the SubAccount holder", example = "Lucille")
  private final String firstName;

  @NonNull
  @Schema(description = "Family name of the SubAccount holder", example = "Van Pelt")
  private final String lastName;

  @NonNull
  @Schema(
      description = "Parent accountId of the SubAccount holder",
      example = "uuid-123456789-abcd")
  private final String accountId;

  /**
   * Create object from json.
   *
   * @param userName username of SubAccount holder
   * @param pii private information of SubAccount holder
   * @param firstName firstname of SubAccount holder
   * @param lastName lastname of SubAccount holder
   * @param accountId id of SubAccount parent
   */
  @JsonCreator
  public SubAccountRequest(
      @NonNull @JsonProperty("userName") String userName,
      @NonNull @JsonProperty("pii") String pii,
      @NonNull @JsonProperty("firstName") String firstName,
      @NonNull @JsonProperty("lastName") String lastName,
      @NonNull @JsonProperty("accountId") String accountId) {

    this.userName = userName;
    this.pii = pii;
    this.firstName = firstName;
    this.lastName = lastName;
    this.accountId = accountId;
  }
}
