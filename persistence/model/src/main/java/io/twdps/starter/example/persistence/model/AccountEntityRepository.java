package io.twdps.starter.example.persistence.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface AccountEntityRepository
    extends PagingAndSortingRepository<AccountEntity, String> {

  Optional<AccountEntity> findByUserName(String userName);

  Page<AccountEntity> findByLastName(String lastName, Pageable pageable);
}
