package io.twdps.starter.api.requests;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AddEntityRequest {

  private final String userName;
  private final String firstName;
  private final String lastName;

  @JsonCreator
  public AddEntityRequest(@JsonProperty("userName") String userName,
      @JsonProperty("firstName") String firstName,
      @JsonProperty("lastName") String lastName) {

    this.userName = userName;
    this.firstName = firstName;
    this.lastName = lastName;
  }

}