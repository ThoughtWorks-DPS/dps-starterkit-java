package io.twdps.starter.api.account.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
//@RequiredArgsConstructor
@Getter
public class {{cookiecutter.RESOURCE_NAME}}Response {

  @NonNull
  private final String id;
  @NonNull
  private final String userName;
  @NonNull
  private final String firstName;
  @NonNull
  private final String lastName;
  @NonNull
  private final String fullName;
}
