package io.twdps.starter.example.persistence.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface SubAccountEntityRepository
    extends PagingAndSortingRepository<SubAccountEntity, String> {

  // TODO: Refactor SubAccountEntity queries
  Optional<SubAccountEntity> findByUserName(String userName);

  Page<SubAccountEntity> findByLastName(String lastName, Pageable pageable);

  Page<SubAccountEntity> findAllByAccountId(String accountId, Pageable pageable);
}
