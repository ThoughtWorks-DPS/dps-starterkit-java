package io.twdps.starter.persistence.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AccountEntityRepository extends CrudRepository<AccountEntity, String> {

  Optional<AccountEntity> findByUserName(String userName);

  List<AccountEntity> findByLastName(String lastName);

}
