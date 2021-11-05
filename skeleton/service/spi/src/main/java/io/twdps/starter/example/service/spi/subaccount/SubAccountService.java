package io.twdps.starter.example.service.spi.subaccount;

import io.twdps.starter.boot.exception.RequestValidationException;
import io.twdps.starter.example.service.spi.subaccount.model.SubAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SubAccountService {

  SubAccount add(SubAccount resource) throws RequestValidationException;

  Page<SubAccount> findByLastName(String lastName, Pageable pageable);

  Optional<SubAccount> findByUserName(String userName);

  Optional<SubAccount> findById(String id);

  Page<SubAccount> findAll(Pageable pageable);

  Page<SubAccount> findAllByAccountId(String id, Pageable pageable);

  Optional<SubAccount> updateById(String id, SubAccount record) throws RequestValidationException;

  Optional<SubAccount> deleteById(String id);
}
