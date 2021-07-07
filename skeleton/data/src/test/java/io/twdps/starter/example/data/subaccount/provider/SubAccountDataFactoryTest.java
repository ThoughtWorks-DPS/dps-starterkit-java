package io.twdps.starter.example.data.subaccount.provider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.twdps.starter.boot.test.data.spi.DataNotFoundException;
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
@ContextConfiguration(classes = {SubAccountDataProperties.class, SubAccountDataFactory.class})
public class SubAccountDataFactoryTest {

  private static String NOT_FOUND = "notFound";

  @Autowired private SubAccountDataFactory subAccountDataFactory;
  @Autowired private SubAccountDataProperties subAccountDataProperties;

  private void validate(SubAccountData data, SubAccountData control) {
    assertThat(data.getFirstName()).isNotNull();
    assertThat(data.getFirstName()).isEqualTo(control.getFirstName());
    assertThat(data.getLastName()).isEqualTo(control.getLastName());
    assertThat(data.getPii()).isEqualTo(control.getPii());
    assertThat(data.getUserName()).isEqualTo(control.getUserName());
  }

  @Test
  public void dataDefaultRecordPopulated() {
    SubAccountData control = subAccountDataProperties.loadData().get("default");
    SubAccountData data = subAccountDataFactory.create();

    validate(data, control);
  }

  @Test
  public void dataNamedRecordPopulated() {
    SubAccountData control = subAccountDataProperties.loadData().get("raiders");
    SubAccountData data = subAccountDataFactory.createBySpec("raiders");

    validate(data, control);
  }

  @Test
  public void missingNamedRecordThrows() {

    assertThrows(
        DataNotFoundException.class,
        () -> {
          SubAccountData response = subAccountDataFactory.createBySpec(NOT_FOUND);
        });
  }

  @Test
  public void collectionDefaultCollectionPopulated() {
    List<SubAccountData> controlCollection =
        subAccountDataProperties.loadCollections().get("default");
    List<SubAccountData> collection = subAccountDataFactory.createCollection();

    assertThat(collection.size()).isEqualTo(controlCollection.size());
    SubAccountData data = collection.get(0);
    SubAccountData control = controlCollection.get(0);

    validate(data, control);
  }

  @Test
  public void collectionNamedCollectionPopulated() {
    List<SubAccountData> controlCollection =
        subAccountDataProperties.loadCollections().get("starwars");
    List<SubAccountData> collection = subAccountDataFactory.createCollectionBySpec("starwars");

    assertThat(collection.size()).isEqualTo(controlCollection.size());
    SubAccountData data = collection.get(0);
    SubAccountData control = controlCollection.get(0);

    validate(data, control);
  }

  @Test
  public void missingNamedCollectionThrows() {

    assertThrows(
        DataNotFoundException.class,
        () -> {
          List<SubAccountData> response = subAccountDataFactory.createCollectionBySpec(NOT_FOUND);
        });
  }
}
