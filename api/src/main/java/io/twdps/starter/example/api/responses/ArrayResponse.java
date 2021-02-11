package io.twdps.starter.example.api.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@AllArgsConstructor
//@RequiredArgsConstructor
@Getter
public class ArrayResponse<T> {

  // TODO: Replace this one with PageableData
  @NonNull
  private final List<T> data;
}
