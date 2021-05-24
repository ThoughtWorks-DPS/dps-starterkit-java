package io.twdps.starter.example.persistence.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class SubAccountEntityRepositoryTest {

  @Autowired private SubAccountEntityRepository modelEntityRepository;

  private SubAccountEntity entity;

  private final String userName = "jsmith";
  private final String pii = "123-45-6789";
  private final String firstName = "Joe";
  private final String lastName = "Smith";

  @BeforeEach
  public void setup() {
    entity = new SubAccountEntity(userName, pii, firstName, lastName);
  }

  /**
   * populate the tables with some tests data.
   *
   * @return one of the saved entities
   */
  public SubAccountEntity populate() {
    SubAccountEntity result = modelEntityRepository.save(entity);
    SubAccountEntity agentSmith = new SubAccountEntity("asmith", pii, "Agent", lastName);
    modelEntityRepository.save(agentSmith);
    SubAccountEntity maryQuiteContrary =
        new SubAccountEntity("maryQuiteContrary", pii, "Mary", "Contrary");
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

    Optional<SubAccountEntity> retrievedEntity = modelEntityRepository.findByUserName(userName);

    assertThat(retrievedEntity.isPresent());
    assertThat(retrievedEntity.get().getFirstName()).isEqualTo(firstName);
  }

  @Test
  public void testFindByLastName() {
    populate();

    Page<SubAccountEntity> retrievedSmiths =
        modelEntityRepository.findByLastName(lastName, Pageable.unpaged());

    assertThat(retrievedSmiths.getContent().size()).isEqualTo(2);
  }

  @Test
  public void testFindByLastNamePaged() {
    populate();

    Pageable pageable = PageRequest.of(0, 1);
    Page<SubAccountEntity> retrievedSmiths =
        modelEntityRepository.findByLastName(lastName, pageable);

    assertThat(retrievedSmiths.getContent().size()).isEqualTo(1);
  }

  @Test
  public void testUpdateRecord() {
    final String newName = "Contrary";
    SubAccountEntity saved = modelEntityRepository.save(entity);
    saved.setLastName(newName);

    SubAccountEntity updated = modelEntityRepository.save(saved);

    Page<SubAccountEntity> retrievedSmiths =
        modelEntityRepository.findByLastName(lastName, Pageable.unpaged());
    assertThat(retrievedSmiths.getContent().size()).isEqualTo(0);
    Page<SubAccountEntity> retrievedContrarians =
        modelEntityRepository.findByLastName(newName, Pageable.unpaged());
    assertThat(retrievedContrarians.getContent().size()).isEqualTo(1);
  }

  @Test
  public void testDeleteRecord() {
    SubAccountEntity saved = populate();

    modelEntityRepository.deleteById(saved.getId());

    Page<SubAccountEntity> retrievedSmiths =
        modelEntityRepository.findByLastName("Smith", Pageable.unpaged());
    assertThat(retrievedSmiths.getContent().size()).isEqualTo(1);
  }

  @Test
  public void testFindAll() {
    populate();
    Page<SubAccountEntity> retrieved = modelEntityRepository.findAll(Pageable.unpaged());
    assertThat(retrieved.getContent().size()).isEqualTo(3);
  }

  @Test
  public void testFindAllPaged() {
    populate();
    Pageable pageable = PageRequest.of(0, 2);
    Page<SubAccountEntity> retrieved = modelEntityRepository.findAll(pageable);
    assertThat(retrieved.getContent().size()).isEqualTo(2);
  }
}
