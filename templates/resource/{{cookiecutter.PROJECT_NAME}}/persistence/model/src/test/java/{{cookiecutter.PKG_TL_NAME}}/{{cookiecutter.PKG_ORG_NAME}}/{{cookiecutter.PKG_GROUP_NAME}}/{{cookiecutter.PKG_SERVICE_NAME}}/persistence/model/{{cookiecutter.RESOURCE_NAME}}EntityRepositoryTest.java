package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.persistence.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class {{cookiecutter.RESOURCE_NAME}}EntityRepositoryTest {

  @Autowired private {{cookiecutter.RESOURCE_NAME}}EntityRepository modelEntityRepository;

  private {{cookiecutter.RESOURCE_NAME}}Entity entity;

  private final String username = "jsmith";
  private final String pii = "123-45-6789";
  private final String firstName = "Joe";
  private final String lastName = "Smith";

  @BeforeEach
  public void setup() {
    entity = new {{cookiecutter.RESOURCE_NAME}}Entity(username, pii, firstName, lastName);
  }

  /** populate the tables with some tests data.
   *
   * @return one of the saved entities
   */
  public {{cookiecutter.RESOURCE_NAME}}Entity populate() {
    {{cookiecutter.RESOURCE_NAME}}Entity result = modelEntityRepository.save(entity);
    {{cookiecutter.RESOURCE_NAME}}Entity agentSmith = new {{cookiecutter.RESOURCE_NAME}}Entity("asmith", pii, "Agent", lastName);
    modelEntityRepository.save(agentSmith);
    {{cookiecutter.RESOURCE_NAME}}Entity maryQuiteContrary = new {{cookiecutter.RESOURCE_NAME}}Entity("maryQuiteContrary", pii, "Mary", "Contrary");
    modelEntityRepository.save(maryQuiteContrary);

    return result;
  }

  @Test
  public void setupValid() {
    assertThat(modelEntityRepository != null);
  }

  @Test
  public void createAndGetTest() {
    modelEntityRepository.save(entity);

    Optional<{{cookiecutter.RESOURCE_NAME}}Entity> retrievedEntity = modelEntityRepository.findByUserName(username);

    assertThat(retrievedEntity.isPresent());
    assertThat(retrievedEntity.get().getFirstName()).isEqualTo(firstName);
  }

  @Test
  public void testFindByLastName() {
    populate();

    List<{{cookiecutter.RESOURCE_NAME}}Entity> retrievedSmiths = modelEntityRepository.findByLastName(lastName);

    assertThat(retrievedSmiths.size()).isEqualTo(2);
  }

  @Test
  public void testUpdateRecord() {
    final String newName = "Contrary";
    {{cookiecutter.RESOURCE_NAME}}Entity saved = modelEntityRepository.save(entity);
    saved.setLastName(newName);

    {{cookiecutter.RESOURCE_NAME}}Entity updated = modelEntityRepository.save(saved);

    List<{{cookiecutter.RESOURCE_NAME}}Entity> retrievedSmiths = modelEntityRepository.findByLastName(lastName);
    assertThat(retrievedSmiths.size()).isEqualTo(0);
    List<{{cookiecutter.RESOURCE_NAME}}Entity> retrievedContrarians = modelEntityRepository.findByLastName(newName);
    assertThat(retrievedContrarians.size()).isEqualTo(1);
  }

  @Test
  public void testDeleteRecord() {
    {{cookiecutter.RESOURCE_NAME}}Entity saved = populate();

    modelEntityRepository.deleteById(saved.getId());

    List<{{cookiecutter.RESOURCE_NAME}}Entity> retrievedSmiths = modelEntityRepository.findByLastName("Smith");
    assertThat(retrievedSmiths.size()).isEqualTo(1);
  }

  @Test
  public void testFindAll() {
    populate();
    List<{{cookiecutter.RESOURCE_NAME}}Entity> retrieved =
        new ArrayList<>((Collection<? extends {{cookiecutter.RESOURCE_NAME}}Entity>) modelEntityRepository.findAll());
    assertThat(retrieved.size()).isEqualTo(3);
  }
}
