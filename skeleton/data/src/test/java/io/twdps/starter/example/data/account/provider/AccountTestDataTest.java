package io.twdps.starter.example.data.account.provider;

import static org.assertj.core.api.Assertions.assertThat;

import io.twdps.starter.boot.test.data.provider.NamedDataFactory;
import io.twdps.starter.example.data.account.model.AccountData;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AccountTestDataTest {

  private AccountTestData testData = new AccountTestData();

  // TODO: Align these with the AccountData test data
  private final String firstName = "Agent";
  private final String lastName = "Smith";
  private final String pii = "eigenvalue";
  private final String userName = "asmith";

  private void validate(AccountData data) {
    assertThat(data.getFirstName()).isNotNull();
    assertThat(data.getFirstName()).isEqualTo(firstName);
    assertThat(data.getLastName()).isEqualTo(lastName);
    assertThat(data.getPii()).isEqualTo(pii);
    assertThat(data.getUserName()).isEqualTo(userName);
    // TODO: Add assertions for additional AccountData fields
  }

  @Test
  public void dataPropertiesPopulated() {
    AccountData data = testData.loadData().get(NamedDataFactory.DEFAULT_SPEC);

    validate(data);
  }

  @Test
  public void collectionPropertiesPopulated() {
    List<AccountData> collection = testData.loadCollections().get(NamedDataFactory.DEFAULT_SPEC);
    assertThat(collection.size()).isEqualTo(2);
    AccountData data = collection.get(0);

    validate(data);
  }
}
