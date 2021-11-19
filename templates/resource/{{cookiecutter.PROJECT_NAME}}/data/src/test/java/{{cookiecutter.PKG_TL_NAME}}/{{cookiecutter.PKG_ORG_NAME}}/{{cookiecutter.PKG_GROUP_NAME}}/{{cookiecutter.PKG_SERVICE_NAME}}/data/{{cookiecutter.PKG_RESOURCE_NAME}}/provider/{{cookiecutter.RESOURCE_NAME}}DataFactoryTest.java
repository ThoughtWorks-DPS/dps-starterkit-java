package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_RESOURCE_NAME}}.provider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.test.data.spi.DataNotFoundException;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.RESOURCE_NAME}}Data;
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
@TestPropertySource(properties = {"spring.config.location=classpath:application-{{cookiecutter.PKG_RESOURCE_NAME}}.yml"})
@ContextConfiguration(classes = { {{cookiecutter.RESOURCE_NAME}}DataProperties.class, {{cookiecutter.RESOURCE_NAME}}DataFactory.class})
public class {{cookiecutter.RESOURCE_NAME}}DataFactoryTest {

  private static String NOT_FOUND = "notFound";

  @Autowired private {{cookiecutter.RESOURCE_NAME}}DataFactory {{cookiecutter.RESOURCE_VAR_NAME}}DataFactory;
  @Autowired private {{cookiecutter.RESOURCE_NAME}}DataProperties {{cookiecutter.RESOURCE_VAR_NAME}}DataProperties;

  private void validate({{cookiecutter.RESOURCE_NAME}}Data data, {{cookiecutter.RESOURCE_NAME}}Data control) {
    assertThat(data.getFirstName()).isNotNull();
    assertThat(data.getFirstName()).isEqualTo(control.getFirstName());
    assertThat(data.getLastName()).isEqualTo(control.getLastName());
    assertThat(data.getPii()).isEqualTo(control.getPii());
    assertThat(data.getUserName()).isEqualTo(control.getUserName());
    // TODO: Add assertions for additional {{cookiecutter.RESOURCE_NAME}} fields
  }

  @Test
  public void dataDefaultRecordPopulated() {
    {{cookiecutter.RESOURCE_NAME}}Data control = {{cookiecutter.RESOURCE_VAR_NAME}}DataProperties.loadData().get("default");
    {{cookiecutter.RESOURCE_NAME}}Data data = {{cookiecutter.RESOURCE_VAR_NAME}}DataFactory.create();

    validate(data, control);
  }

  @Test
  public void dataNamedRecordPopulated() {
    {{cookiecutter.RESOURCE_NAME}}Data control = {{cookiecutter.RESOURCE_VAR_NAME}}DataProperties.loadData().get("raiders");
    {{cookiecutter.RESOURCE_NAME}}Data data = {{cookiecutter.RESOURCE_VAR_NAME}}DataFactory.createBySpec("raiders");

    validate(data, control);
  }

  @Test
  public void missingNamedRecordThrows() {

    assertThrows(
        DataNotFoundException.class,
        () -> {
          {{cookiecutter.RESOURCE_NAME}}Data response = {{cookiecutter.RESOURCE_VAR_NAME}}DataFactory.createBySpec(NOT_FOUND);
        });
  }

  @Test
  public void collectionDefaultCollectionPopulated() {
    List<{{cookiecutter.RESOURCE_NAME}}Data> controlCollection = {{cookiecutter.RESOURCE_VAR_NAME}}DataProperties.loadCollections().get("default");
    List<{{cookiecutter.RESOURCE_NAME}}Data> collection = {{cookiecutter.RESOURCE_VAR_NAME}}DataFactory.createCollection();

    assertThat(collection.size()).isEqualTo(controlCollection.size());
    {{cookiecutter.RESOURCE_NAME}}Data data = collection.get(0);
    {{cookiecutter.RESOURCE_NAME}}Data control = controlCollection.get(0);

    validate(data, control);
  }

  @Test
  public void collectionNamedCollectionPopulated() {
    List<{{cookiecutter.RESOURCE_NAME}}Data> controlCollection = {{cookiecutter.RESOURCE_VAR_NAME}}DataProperties.loadCollections().get("starwars");
    List<{{cookiecutter.RESOURCE_NAME}}Data> collection = {{cookiecutter.RESOURCE_VAR_NAME}}DataFactory.createCollectionBySpec("starwars");

    assertThat(collection.size()).isEqualTo(controlCollection.size());
    {{cookiecutter.RESOURCE_NAME}}Data data = collection.get(0);
    {{cookiecutter.RESOURCE_NAME}}Data control = controlCollection.get(0);

    validate(data, control);
  }

  @Test
  public void missingNamedCollectionThrows() {

    assertThrows(
        DataNotFoundException.class,
        () -> {
          List<{{cookiecutter.RESOURCE_NAME}}Data> response = {{cookiecutter.RESOURCE_VAR_NAME}}DataFactory.createCollectionBySpec(NOT_FOUND);
        });
  }
}
