package io.twdps.starter.persistence.model;

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
public class AccountEntityRepositoryTest {

  @Autowired private AccountEntityRepository modelEntityRepository;

  private AccountEntity accountEntity;

  private final String username = "jsmith";
  private final String firstName = "Joe";
  private final String lastName = "Smith";

  @BeforeEach
  public void setup() {
    accountEntity = new AccountEntity(username, firstName, lastName);
  }

  /** populate the tables with some tests data.
   *
   * @return one of the saved entities
   */
  public AccountEntity populate() {
    AccountEntity result = modelEntityRepository.save(accountEntity);
    AccountEntity agentSmith = new AccountEntity("asmith", "Agent", lastName);
    modelEntityRepository.save(agentSmith);
    AccountEntity maryQuiteContrary = new AccountEntity("maryQuiteContrary", "Mary", "Contrary");
    modelEntityRepository.save(maryQuiteContrary);

    return result;
  }

  @Test
  public void setupValid() {
    assertThat(modelEntityRepository != null);
  }

  @Test
  public void createAndGetTest() {
    modelEntityRepository.save(accountEntity);

    Optional<AccountEntity> retrievedEntity = modelEntityRepository.findByUserName(username);

    assertThat(retrievedEntity.isPresent());
    assertThat(retrievedEntity.get().getFirstName()).isEqualTo(firstName);
  }

  @Test
  public void testFindByLastName() {
    populate();

    List<AccountEntity> retrievedSmiths = modelEntityRepository.findByLastName(lastName);

    assertThat(retrievedSmiths.size()).isEqualTo(2);
  }

  @Test
  public void testUpdateRecord() {
    final String newName = "Contrary";
    AccountEntity saved = modelEntityRepository.save(accountEntity);
    saved.setLastName(newName);

    AccountEntity updated = modelEntityRepository.save(saved);

    List<AccountEntity> retrievedSmiths = modelEntityRepository.findByLastName(lastName);
    assertThat(retrievedSmiths.size()).isEqualTo(0);
    List<AccountEntity> retrievedContrarians = modelEntityRepository.findByLastName(newName);
    assertThat(retrievedContrarians.size()).isEqualTo(1);
  }

  @Test
  public void testDeleteRecord() {
    AccountEntity saved = populate();

    modelEntityRepository.deleteById(saved.getId());

    List<AccountEntity> retrievedSmiths = modelEntityRepository.findByLastName("Smith");
    assertThat(retrievedSmiths.size()).isEqualTo(1);
  }

  @Test
  public void testFindAll() {
    populate();
    List<AccountEntity> retrieved =
        new ArrayList<>((Collection<? extends AccountEntity>) modelEntityRepository.findAll());
    assertThat(retrieved.size()).isEqualTo(3);
  }
}
