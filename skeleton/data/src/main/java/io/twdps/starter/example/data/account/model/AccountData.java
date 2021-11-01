package io.twdps.starter.example.data.account.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccountData {

  private String id;
  private String userName;
  private String pii;
  private String firstName;
  private String lastName;
  private String fullName;
}
