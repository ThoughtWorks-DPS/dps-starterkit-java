package io.twdps.starter.example.api.account.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AccountRequest {

  @NonNull
  private final String userName;
  @NonNull
  private final String pii;
  @NonNull
  private final String firstName;
  @NonNull
  private final String lastName;

  /** Create object from json.
   *
   * @param userName  username of Account holder
   * @param pii private information of Account holder
   * @param firstName firstname of Account holder
   * @param lastName lastname of Account holder
   */
  @JsonCreator
  public AccountRequest(
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
