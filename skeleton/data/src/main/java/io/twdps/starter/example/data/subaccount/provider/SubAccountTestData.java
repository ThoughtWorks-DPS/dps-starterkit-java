package io.twdps.starter.example.data.subaccount.provider;

import io.twdps.starter.boot.test.data.provider.GenericDataLoader;
import io.twdps.starter.example.data.subaccount.model.SubAccountData;

import java.util.Arrays;

public class SubAccountTestData extends GenericDataLoader<SubAccountData> {

  /** construct hard-coded test data. */
  // TODO: Additional SubAccountData data values.  See also ./resources/application-subaccount.yml
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
                .accountId("uuid-123456789-abcdef")
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
                .accountId("uuid-123456789-abcdef")
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
                    .accountId("uuid-123456789-abcdef")
                    .build(),
                SubAccountData.builder()
                    .firstName("Oops")
                    .lastName("Smith")
                    .userName("osmith")
                    .fullName("Oops Smith")
                    .pii("for-your-eyes-only")
                    .id("uuid-license-to-fail")
                    .accountId("uuid-123456789-abcdef")
                    .build(),
                SubAccountData.builder()
                    .firstName("Neo")
                    .lastName("None")
                    .userName("neo")
                    .fullName("Neo None")
                    .pii("sunglasses")
                    .id("Reeves")
                    .accountId("uuid-123456789-abcdef")
                    .build()));
  }
}
