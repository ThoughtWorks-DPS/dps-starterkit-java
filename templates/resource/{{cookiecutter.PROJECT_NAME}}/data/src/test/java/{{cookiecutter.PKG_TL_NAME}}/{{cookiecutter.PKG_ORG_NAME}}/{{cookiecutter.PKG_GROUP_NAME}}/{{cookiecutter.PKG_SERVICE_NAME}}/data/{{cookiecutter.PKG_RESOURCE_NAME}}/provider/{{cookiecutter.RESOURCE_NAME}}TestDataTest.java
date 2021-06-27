package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_RESOURCE_NAME}}.provider;

import static org.assertj.core.api.Assertions.assertThat;

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.test.data.provider.NamedDataFactory;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.RESOURCE_NAME}}Data;
import org.junit.jupiter.api.Test;

import java.util.List;

public class {{cookiecutter.RESOURCE_NAME}}TestDataTest {

  private {{cookiecutter.RESOURCE_NAME}}TestData testData = new {{cookiecutter.RESOURCE_NAME}}TestData();

  private final String firstName = "Agent";
  private final String lastName = "Smith";
  private final String pii = "eigenvalue";

  @Test
  public void dataPropertiesPopulated() {
    {{cookiecutter.RESOURCE_NAME}}Data data = testData.loadData().get(NamedDataFactory.DEFAULT_SPEC);
    assertThat(data.getFirstName()).isNotNull();
    assertThat(data.getFirstName()).isEqualTo(firstName);
    assertThat(data.getLastName()).isEqualTo(lastName);
    assertThat(data.getPii()).isEqualTo(pii);
  }

  @Test
  public void collectionPropertiesPopulated() {
    List<{{cookiecutter.RESOURCE_NAME}}Data> collection = testData.loadCollections().get(NamedDataFactory.DEFAULT_SPEC);
    assertThat(collection.size()).isEqualTo(2);
    {{cookiecutter.RESOURCE_NAME}}Data data = collection.get(0);
    assertThat(data.getFirstName()).isNotNull();
    assertThat(data.getFirstName()).isEqualTo(firstName);
    assertThat(data.getLastName()).isEqualTo(lastName);
    assertThat(data.getPii()).isEqualTo(pii);
  }
}
