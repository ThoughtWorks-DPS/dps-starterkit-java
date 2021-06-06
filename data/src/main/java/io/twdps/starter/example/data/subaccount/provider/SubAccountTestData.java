package io.twdps.starter.example.data.subaccount.provider;

import io.twdps.starter.boot.test.data.provider.GenericDataLoader;
import io.twdps.starter.example.data.subaccount.model.SubAccountData;

import java.util.Arrays;

public class SubAccountTestData extends GenericDataLoader<SubAccountData> {

  /** construct hard-coded test data. */
  public SubAccountTestData() {
    getData()
        .put(
            "default",
            SubAccountData.builder()
                .firstName("Agent")
                .lastName("Smith")
                .userName("asmith")
                .fullName("Agent Smith")
                .pii("eigenvalue")
                .id("uuid-unit-vector")
                .build());
    getData()
        .put(
            "bogus",
            SubAccountData.builder()
                .firstName("Ted")
                .lastName("Logan")
                .userName("theodore")
                .fullName("Ted Logan")
                .pii("wyldstallyns")
                .id("Reeves")
                .build());
    getCollections()
        .put(
            "default",
            Arrays.asList(
                SubAccountData.builder()
                    .firstName("Agent")
                    .lastName("Smith")
                    .userName("asmith")
                    .fullName("Agent Smith")
                    .pii("eigenvalue")
                    .id("uuid-unit-vector")
                    .build(),
                SubAccountData.builder()
                    .firstName("Neo")
                    .lastName("None")
                    .userName("neo")
                    .fullName("Neo None")
                    .pii("sunglasses")
                    .id("Reeves")
                    .build()));
  }
}
