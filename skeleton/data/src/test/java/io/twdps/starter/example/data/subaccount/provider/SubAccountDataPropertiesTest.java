package io.twdps.starter.example.data.subaccount.provider;

import static org.assertj.core.api.Assertions.assertThat;

import io.twdps.starter.boot.test.data.provider.NamedDataFactory;
import io.twdps.starter.example.data.subaccount.model.SubAccountData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@JsonTest
@TestPropertySource(properties = {"spring.config.location=classpath:application-subaccount.yml"})
@ContextConfiguration(classes = {SubAccountDataProperties.class})
public class SubAccountDataPropertiesTest {

  @Autowired private SubAccountDataProperties testData;

  private final String firstName = "Agent";
  private final String lastName = "Smith";
  private final String pii = "eigenvalue";
  private final String userName = "asmith";

  @Test
  public void dataPropertiesPopulated() {
    SubAccountData data = testData.loadData().get(NamedDataFactory.DEFAULT_SPEC);
    assertThat(data.getFirstName()).isNotNull();
    assertThat(data.getFirstName()).isEqualTo(firstName);
    assertThat(data.getLastName()).isEqualTo(lastName);
    assertThat(data.getPii()).isEqualTo(pii);
    assertThat(data.getUserName()).isEqualTo(userName);
  }

  @Test
  public void collectionPropertiesPopulated() {
    List<SubAccountData> collection = testData.loadCollections().get(NamedDataFactory.DEFAULT_SPEC);
    assertThat(collection.size()).isEqualTo(2);
    SubAccountData data = collection.get(0);
    assertThat(data.getFirstName()).isNotNull();
    assertThat(data.getFirstName()).isEqualTo(firstName);
    assertThat(data.getLastName()).isEqualTo(lastName);
    assertThat(data.getPii()).isEqualTo(pii);
    assertThat(data.getUserName()).isEqualTo(userName);
  }
}
