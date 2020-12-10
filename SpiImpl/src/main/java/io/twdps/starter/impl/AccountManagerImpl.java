package io.twdps.starter.impl;

import io.twdps.starter.persistence.mapper.AccountEntityMapper;
import io.twdps.starter.persistence.model.AccountEntity;
import io.twdps.starter.persistence.model.AccountEntityRepository;
import io.twdps.starter.spi.AccountManager;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountManagerImpl implements AccountManager {

  @Autowired
  private AccountEntityRepository accountEntityRepository;
  @Autowired
  private AccountEntityMapper accountEntityMapper;

  public AccountEntity addEntity(AccountEntity accountEntity) {
    AccountEntity saved = accountEntityRepository.save(accountEntity);
    return saved;
  }

  public List<AccountEntity> findByLastName(String lastName) {
    log.info("looking up by lastname of:{}", lastName);
    List<AccountEntity> responseList = accountEntityRepository.findByLastName(lastName);
    log.info("Response list size:{}", responseList.size());
    return responseList;
  }

  public Optional<AccountEntity> findByUserName(String userName) {
    log.info("looking up by username:{}",userName);
    Optional<AccountEntity> accountEntity = Optional.ofNullable(accountEntityRepository.findByUserName(userName));
    return accountEntity;
  }
}
