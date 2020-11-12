package io.twdps.starter.api.responses;

import java.util.List;

public class ResponseDataInArray<T> {

  //TODO: Replace this one with PageableData
  private final List<T> data;

  public ResponseDataInArray(List<T> data) {
    this.data = data;
  }

  public List<T> getData() {
    return data;
  }
}