package io.twdps.starter.impl.account;

import io.twdps.starter.impl.account.mapper.AccountEntityMapper;
import io.twdps.starter.persistence.model.AccountEntityRepository;
import io.twdps.starter.spi.account.AccountManager;
import io.twdps.starter.spi.account.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AccountManagerImpl implements AccountManager {

  private AccountEntityRepository repository;
  private AccountEntityMapper mapper;

  AccountManagerImpl(AccountEntityRepository repository,
                     AccountEntityMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }
  /**
   * add a new Account entity.
   *
   * @param account account info to add (id should be null)
   * @return new account object with valid id
   */
  public Account add(Account account) {
    Account saved = mapper.toModel(repository.save(mapper.toEntity(account)));
    return saved;
  }

  /**
   * find all accounts matching last name.
   *
   * @param lastName criteria for match
   * @return list of matching Account records
   */
  public List<Account> findByLastName(String lastName) {
    log.info("looking up by lastname of:{}", lastName);
    List<Account> responseList = mapper.toModelList(repository.findByLastName(lastName));
    log.info("Response list size:{}", responseList.size());
    return responseList;
  }

  /**
   * find account by user name.
   *
   * @param userName username criteria to match
   * @return matching record, or null
   */
  public Optional<Account> findByUserName(String userName) {
    log.info("looking up by username:{}", userName);
    Optional<Account> account = mapper.toModel(repository.findByUserName(userName));
    return account;
  }

  @Override
  public Optional<Account> findById(String id) {
    Optional<Account> account = mapper.toModel(repository.findById(id));
    return account;
  }

  @Override
  public List<Account> findAll() {
    List<Account> account = mapper.toModelList(repository.findAll());
    return account;
  }

  @Override
  public Optional<Account> updateById(String id, Account record) {
    Optional<Account> account =
        mapper.toModel(
            repository
                .findById(id)
                .map((obj) -> mapper.updateMetadata(record, obj))
                .map((obj) -> repository.save(obj)));

    return account;
  }

  @Override
  public Optional<Account> deleteById(String id) {
    Optional<Account> account = findById(id);
    repository.deleteById(id);
    return account;
  }
}
