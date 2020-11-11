package io.twdps.starter.persistence.api;

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
        accountEntity = AccountEntity.builder()
                .userName("jsmith").firstName("Joe").lastName("Smith").build();
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
        AccountEntity agentSmith = AccountEntity.builder()
                .userName("asmith").firstName("Agent").lastName("Smith").build();
        modelEntityRepository.save(agentSmith);
        AccountEntity maryQuiteContrary = AccountEntity.builder()
                .userName("maryQuiteContrary").firstName("Mary").lastName("Contrary").build();
        modelEntityRepository.save(maryQuiteContrary);
        List<AccountEntity> retrievedSmiths = modelEntityRepository.findByLastName("Smith");
        assertThat(retrievedSmiths.size() == 2);
    }

}
