package io.twdps.starter.example.data.subaccount.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SubAccountData {

  // TODO: Refactor SubAccountData by renaming variable names, migrate types
  private String id;
  private String userName;
  private String pii;
  private String firstName;
  private String lastName;
  private String fullName;
  // TODO: Additional SubAccountData data elements
  private String accountId;
}
