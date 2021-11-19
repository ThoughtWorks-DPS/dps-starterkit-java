package io.twdps.starter.example.service.spi.account.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Account {

  private String id;

  // TODO: Refactor Account by renaming variable names, migrate types
  @NonNull private String userName;
  @NonNull private String pii;
  @NonNull private String firstName;
  @NonNull private String lastName;
  // TODO: Additional Account data elements
}
