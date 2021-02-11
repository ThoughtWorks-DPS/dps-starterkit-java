package io.twdps.starter.example.api.account.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
//@RequiredArgsConstructor
@Getter
public class AccountResponse {

  @NonNull
  private final String id;
  @NonNull
  private final String userName;
  @NonNull
  private final String pii;
  @NonNull
  private final String firstName;
  @NonNull
  private final String lastName;
  @NonNull
  private final String fullName;
}
