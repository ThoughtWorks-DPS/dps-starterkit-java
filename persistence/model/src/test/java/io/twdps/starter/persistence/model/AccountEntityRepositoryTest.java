package io.twdps.starter.persistence.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AccountEntityRepositoryTest {

  @Autowired
  private AccountEntityRepository modelEntityRepository;

  private AccountEntity accountEntity;

  @BeforeEach
  public void setup() {
    accountEntity = new AccountEntity("jsmith", "Joe", "Smith");
  }

  @Test
  public void setupValid() {
    assertThat(modelEntityRepository != null);
  }

  @Test
  public void createAndGetTest() {
    modelEntityRepository.save(accountEntity);
    AccountEntity retrievedEntity = modelEntityRepository.findByUserName("jsmith");
    assertThat(retrievedEntity != null);
    assertThat(retrievedEntity.getFirstName().equals("Joe"));
  }

  @Test
  public void testFindByLastName() {
    modelEntityRepository.save(accountEntity);
    AccountEntity agentSmith = new AccountEntity("asmith", "Agent", "Smith");

    modelEntityRepository.save(agentSmith);
    AccountEntity maryQuiteContrary = new AccountEntity("maryQuiteContrary", "Mary", "Contrary");
    modelEntityRepository.save(maryQuiteContrary);
    List<AccountEntity> retrievedSmiths = modelEntityRepository.findByLastName("Smith");
    assertThat(retrievedSmiths.size() == 2);
  }

}
