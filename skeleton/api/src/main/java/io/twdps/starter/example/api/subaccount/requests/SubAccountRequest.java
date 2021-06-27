package io.twdps.starter.example.api.subaccount.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
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

  /**
   * Create object from json.
   *
   * @param userName username of SubAccount holder
   * @param pii private information of SubAccount holder
   * @param firstName firstname of SubAccount holder
   * @param lastName lastname of SubAccount holder
   */
  @JsonCreator
  public SubAccountRequest(
      @NonNull @JsonProperty("userName") String userName,
      @NonNull @JsonProperty("pii") String pii,
      @NonNull @JsonProperty("firstName") String firstName,
      @NonNull @JsonProperty("lastName") String lastName) {

    this.userName = userName;
    this.pii = pii;
    this.firstName = firstName;
    this.lastName = lastName;
  }
}
