package io.twdps.starter.example.data.account.provider;

import io.twdps.starter.boot.test.data.provider.GenericDataLoader;
import io.twdps.starter.example.data.account.model.AccountData;

import java.util.Arrays;

public class AccountTestData extends GenericDataLoader<AccountData> {

  /** construct hard-coded test data. */
  public AccountTestData() {
    getData()
        .put(
            "default",
            AccountData.builder()
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
            AccountData.builder()
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
                AccountData.builder()
                    .firstName("Agent")
                    .lastName("Smith")
                    .userName("asmith")
                    .fullName("Agent Smith")
                    .pii("eigenvalue")
                    .id("uuid-unit-vector")
                    .build(),
                AccountData.builder()
                    .firstName("Neo")
                    .lastName("None")
                    .userName("neo")
                    .fullName("Neo None")
                    .pii("sunglasses")
                    .id("Reeves")
                    .build()));
  }
}
