package io.twdps.starter.example.data.subaccount.provider;

import static org.assertj.core.api.Assertions.assertThat;

import io.twdps.starter.boot.test.data.spi.DataFactory;
import io.twdps.starter.example.data.subaccount.model.SubAccountData;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SubAccountTestDataTest {

  private SubAccountTestData testData = new SubAccountTestData();

  private final String firstName = "Agent";
  private final String lastName = "Smith";
  private final String pii = "eigenvalue";

  @Test
  public void dataPropertiesPopulated() {
    SubAccountData data = testData.loadData().get(DataFactory.DEFAULT_NAME);
    assertThat(data.getFirstName()).isNotNull();
    assertThat(data.getFirstName()).isEqualTo(firstName);
    assertThat(data.getLastName()).isEqualTo(lastName);
    assertThat(data.getPii()).isEqualTo(pii);
  }

  @Test
  public void collectionPropertiesPopulated() {
    List<SubAccountData> collection = testData.loadCollections().get(DataFactory.DEFAULT_NAME);
    assertThat(collection.size()).isEqualTo(2);
    SubAccountData data = collection.get(0);
    assertThat(data.getFirstName()).isNotNull();
    assertThat(data.getFirstName()).isEqualTo(firstName);
    assertThat(data.getLastName()).isEqualTo(lastName);
    assertThat(data.getPii()).isEqualTo(pii);
  }
}
