package io.twdps.starter.example.data.account.provider;

import static org.assertj.core.api.Assertions.assertThat;

import io.twdps.starter.boot.test.data.spi.DataFactory;
import io.twdps.starter.example.data.account.model.AccountData;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AccountTestDataTest {

  private AccountTestData testData = new AccountTestData();

  private final String firstName = "Agent";
  private final String lastName = "Smith";
  private final String pii = "eigenvalue";

  @Test
  public void dataPropertiesPopulated() {
    AccountData data = testData.loadData().get(DataFactory.DEFAULT_NAME);
    assertThat(data.getFirstName()).isNotNull();
    assertThat(data.getFirstName()).isEqualTo(firstName);
    assertThat(data.getLastName()).isEqualTo(lastName);
    assertThat(data.getPii()).isEqualTo(pii);
  }

  @Test
  public void collectionPropertiesPopulated() {
    List<AccountData> collection = testData.loadCollections().get(DataFactory.DEFAULT_NAME);
    assertThat(collection.size()).isEqualTo(2);
    AccountData data = collection.get(0);
    assertThat(data.getFirstName()).isNotNull();
    assertThat(data.getFirstName()).isEqualTo(firstName);
    assertThat(data.getLastName()).isEqualTo(lastName);
    assertThat(data.getPii()).isEqualTo(pii);
  }
}
