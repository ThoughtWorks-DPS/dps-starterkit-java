package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.persistence.model;

import static org.assertj.core.api.Assertions.assertThat;

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.test.data.provider.NamedDataFactory;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.RESOURCE_NAME}}Data;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_RESOURCE_NAME}}.provider.{{cookiecutter.RESOURCE_NAME}}DataFactory;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_RESOURCE_NAME}}.provider.{{cookiecutter.RESOURCE_NAME}}DataProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@EnableAutoConfiguration
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ContextConfiguration(classes = { {{cookiecutter.RESOURCE_NAME}}DataFactory.class, {{cookiecutter.RESOURCE_NAME}}DataProperties.class })
@TestPropertySource(
    properties = {
        "spring.config.location=classpath:application-{{cookiecutter.PKG_RESOURCE_NAME}}.yml,classpath:application.yml"
    })
public class {{cookiecutter.RESOURCE_NAME}}EntityRepositoryTest {

  @Autowired private {{cookiecutter.RESOURCE_NAME}}EntityRepository modelEntityRepository;

  private {{cookiecutter.RESOURCE_NAME}}Entity entity;

  @Autowired private {{cookiecutter.RESOURCE_NAME}}DataFactory testData;

  private {{cookiecutter.RESOURCE_NAME}}Data reference;
  private {{cookiecutter.RESOURCE_NAME}}Data bogus;

  /** Setup test data. */
  @BeforeEach
  public void setup() {
    reference = testData.createBySpec(NamedDataFactory.DEFAULT_SPEC);
    bogus = testData.createBySpec("bogus");

    entity = new {{cookiecutter.RESOURCE_NAME}}Entity(
        reference.getUserName(),
        reference.getPii(),
        reference.getFirstName(),
        reference.getLastName());
  }

  /**
   * populate the tables with some tests data.
   *
   * @return one of the saved entities
   */
  public {{cookiecutter.RESOURCE_NAME}}Entity populate() {
    {{cookiecutter.RESOURCE_NAME}}Entity result = modelEntityRepository.save(entity);
    testData.createCollectionBySpec(NamedDataFactory.DEFAULT_SPEC).stream()
        .forEach(
            d -> {
              {{cookiecutter.RESOURCE_NAME}}Entity ref =
                  new {{cookiecutter.RESOURCE_NAME}}Entity(d.getUserName(), d.getPii(), d.getFirstName(), d.getLastName());
              modelEntityRepository.save(ref);
            });

    return result;
  }

  @Test
  public void setupValid() {
    assertThat(modelEntityRepository != null);
  }

  @Test
  public void createAndGetTest() {
    modelEntityRepository.save(entity);

    Optional<{{cookiecutter.RESOURCE_NAME}}Entity> retrievedEntity = modelEntityRepository.findByUserName(reference.getUserName());

    assertThat(retrievedEntity.isPresent());
    assertThat(retrievedEntity.get().getFirstName()).isEqualTo(reference.getFirstName());
  }

  @Test
  public void testFindByLastName() {
    populate();

    Page<{{cookiecutter.RESOURCE_NAME}}Entity> results =
        modelEntityRepository.findByLastName(reference.getLastName(), Pageable.unpaged());

    assertThat(results.getContent().size()).isEqualTo(2);
  }

  @Test
  public void testFindByLastNamePaged() {
    populate();

    Pageable pageable = PageRequest.of(0, 1);
    Page<{{cookiecutter.RESOURCE_NAME}}Entity> results = modelEntityRepository.findByLastName(reference.getLastName(), pageable);

    assertThat(results.getContent().size()).isEqualTo(1);
  }

  @Test
  public void testUpdateRecord() {
    {{cookiecutter.RESOURCE_NAME}}Entity saved = modelEntityRepository.save(entity);
    saved.setLastName(bogus.getLastName());

    {{cookiecutter.RESOURCE_NAME}}Entity updated = modelEntityRepository.save(saved);

    Page<{{cookiecutter.RESOURCE_NAME}}Entity> results =
        modelEntityRepository.findByLastName(reference.getLastName(), Pageable.unpaged());
    assertThat(results.getContent().size()).isEqualTo(0);
    Page<{{cookiecutter.RESOURCE_NAME}}Entity> bogonResults =
        modelEntityRepository.findByLastName(bogus.getLastName(), Pageable.unpaged());
    assertThat(bogonResults.getContent().size()).isEqualTo(1);
  }

  @Test
  public void testDeleteRecord() {
    {{cookiecutter.RESOURCE_NAME}}Entity saved = populate();

    modelEntityRepository.deleteById(saved.getId());

    Page<{{cookiecutter.RESOURCE_NAME}}Entity> results =
        modelEntityRepository.findByLastName(reference.getLastName(), Pageable.unpaged());
    assertThat(results.getContent().size()).isEqualTo(1);
  }

  @Test
  public void testFindAll() {
    populate();
    Page<{{cookiecutter.RESOURCE_NAME}}Entity> retrieved = modelEntityRepository.findAll(Pageable.unpaged());
    assertThat(retrieved.getContent().size()).isEqualTo(3);
  }

  @Test
  public void testFindAllPaged() {
    populate();
    Pageable pageable = PageRequest.of(0, 2);
    Page<{{cookiecutter.RESOURCE_NAME}}Entity> retrieved = modelEntityRepository.findAll(pageable);
    assertThat(retrieved.getContent().size()).isEqualTo(2);
  }
}
