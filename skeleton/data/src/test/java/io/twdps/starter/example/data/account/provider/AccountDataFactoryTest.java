package io.twdps.starter.example.data.account.provider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.twdps.starter.boot.test.data.spi.DataNotFoundException;
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
@ContextConfiguration(classes = {AccountDataProperties.class, AccountDataFactory.class})
public class AccountDataFactoryTest {

  private static String NOT_FOUND = "notFound";

  @Autowired private AccountDataFactory accountDataFactory;
  @Autowired private AccountDataProperties accountDataProperties;

  private void validate(AccountData data, AccountData control) {
    assertThat(data.getFirstName()).isNotNull();
    assertThat(data.getFirstName()).isEqualTo(control.getFirstName());
    assertThat(data.getLastName()).isEqualTo(control.getLastName());
    assertThat(data.getPii()).isEqualTo(control.getPii());
    assertThat(data.getUserName()).isEqualTo(control.getUserName());
    // TODO: Add assertions for additional Account fields
  }

  @Test
  public void dataDefaultRecordPopulated() {
    AccountData control = accountDataProperties.loadData().get("default");
    AccountData data = accountDataFactory.create();

    validate(data, control);
  }

  @Test
  public void dataNamedRecordPopulated() {
    AccountData control = accountDataProperties.loadData().get("raiders");
    AccountData data = accountDataFactory.createBySpec("raiders");

    validate(data, control);
  }

  @Test
  public void missingNamedRecordThrows() {

    assertThrows(
        DataNotFoundException.class,
        () -> {
          AccountData response = accountDataFactory.createBySpec(NOT_FOUND);
        });
  }

  @Test
  public void collectionDefaultCollectionPopulated() {
    List<AccountData> controlCollection = accountDataProperties.loadCollections().get("default");
    List<AccountData> collection = accountDataFactory.createCollection();

    assertThat(collection.size()).isEqualTo(controlCollection.size());
    AccountData data = collection.get(0);
    AccountData control = controlCollection.get(0);

    validate(data, control);
  }

  @Test
  public void collectionNamedCollectionPopulated() {
    List<AccountData> controlCollection = accountDataProperties.loadCollections().get("starwars");
    List<AccountData> collection = accountDataFactory.createCollectionBySpec("starwars");

    assertThat(collection.size()).isEqualTo(controlCollection.size());
    AccountData data = collection.get(0);
    AccountData control = controlCollection.get(0);

    validate(data, control);
  }

  @Test
  public void missingNamedCollectionThrows() {

    assertThrows(
        DataNotFoundException.class,
        () -> {
          List<AccountData> response = accountDataFactory.createCollectionBySpec(NOT_FOUND);
        });
  }
}
