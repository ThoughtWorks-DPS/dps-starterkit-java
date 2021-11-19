package io.twdps.starter.example.data.account.provider;

import static org.assertj.core.api.Assertions.assertThat;

import io.twdps.starter.boot.test.data.provider.NamedDataFactory;
import io.twdps.starter.example.data.account.model.AccountData;
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
@TestPropertySource(properties = {"spring.config.location=classpath:application-account.yml"})
@ContextConfiguration(classes = {AccountDataProperties.class})
public class AccountDataPropertiesTest {

  @Autowired private AccountDataProperties testData;

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
