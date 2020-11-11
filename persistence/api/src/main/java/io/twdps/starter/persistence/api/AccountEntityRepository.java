package io.twdps.starter.persistence.api;


import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountEntityRepository extends CrudRepository<AccountEntity, Long> {

    AccountEntity findByUserName(String userName);

    List<AccountEntity> findByLastName(String lastName);
}