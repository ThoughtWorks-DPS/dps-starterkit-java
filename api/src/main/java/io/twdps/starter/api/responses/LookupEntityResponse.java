package io.twdps.starter.api.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LookupEntityResponse {

  private String userName;
  private String fullName;
}
