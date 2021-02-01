package io.twdps.starter.api.account.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
//@RequiredArgsConstructor
@Getter
public class AddAccountResponse {

  @NonNull
  private final String id;
  @NonNull
  private final String response;
}
