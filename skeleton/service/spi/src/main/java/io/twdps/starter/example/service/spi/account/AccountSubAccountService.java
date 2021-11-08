package io.twdps.starter.example.service.spi.account;

import io.twdps.starter.boot.exception.RequestValidationException;
import io.twdps.starter.example.service.spi.account.model.SubAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AccountSubAccountService {

  SubAccount add(String parentId, SubAccount resource) throws RequestValidationException;

  Optional<SubAccount> findById(String parentId, String id);

  Page<SubAccount> findAll(String parentId, Pageable pageable);

  // CSOFF: LineLength
  Optional<SubAccount> updateById(String parentId, String id, SubAccount record)
      throws RequestValidationException;
  // CSON: LineLength

  Optional<SubAccount> deleteById(String parentId, String id);
}
