package io.twdps.starter.persistence.model;


import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface AccountEntityRepository extends CrudRepository<AccountEntity, Long> {

  AccountEntity findByUserName(String userName);

  List<AccountEntity> findByLastName(String lastName);
}