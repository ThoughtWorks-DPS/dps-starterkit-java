package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_RESOURCE_NAME}}.provider;

import static org.assertj.core.api.Assertions.assertThat;

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.test.data.provider.NamedDataFactory;
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
@ContextConfiguration(classes = { {{cookiecutter.RESOURCE_NAME}}DataProperties.class})
public class {{cookiecutter.RESOURCE_NAME}}DataPropertiesTest {

  @Autowired private {{cookiecutter.RESOURCE_NAME}}DataProperties testData;

  // TODO: Align these with the {{cookiecutter.RESOURCE_NAME}}Data test data
  private final String firstName = "Agent";
  private final String lastName = "Smith";
  private final String pii = "eigenvalue";
  private final String userName = "asmith";

  private void validate({{cookiecutter.RESOURCE_NAME}}Data data) {
    assertThat(data.getFirstName()).isNotNull();
    assertThat(data.getFirstName()).isEqualTo(firstName);
    assertThat(data.getLastName()).isEqualTo(lastName);
    assertThat(data.getPii()).isEqualTo(pii);
    assertThat(data.getUserName()).isEqualTo(userName);
    // TODO: Add assertions for additional {{cookiecutter.RESOURCE_NAME}}Data fields
  }

  @Test
  public void dataPropertiesPopulated() {
    {{cookiecutter.RESOURCE_NAME}}Data data = testData.loadData().get(NamedDataFactory.DEFAULT_SPEC);

    validate(data);
  }

  @Test
  public void collectionPropertiesPopulated() {
    List<{{cookiecutter.RESOURCE_NAME}}Data> collection = testData.loadCollections().get(NamedDataFactory.DEFAULT_SPEC);
    assertThat(collection.size()).isEqualTo(2);
    {{cookiecutter.RESOURCE_NAME}}Data data = collection.get(0);

    validate(data);
  }
}
