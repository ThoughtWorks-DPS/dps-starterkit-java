package io.twdps.starter.example.service.provider.account;

import io.twdps.starter.boot.exception.RequestValidationException;
import io.twdps.starter.example.persistence.model.AccountEntityRepository;
import io.twdps.starter.example.service.provider.account.mapper.AccountEntityMapper;
import io.twdps.starter.example.service.spi.account.AccountService;
import io.twdps.starter.example.service.spi.account.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

  private AccountEntityRepository repository;
  private AccountEntityMapper mapper;

  AccountServiceImpl(AccountEntityRepository repository,
                     AccountEntityMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  /**
   * add a new Account entity.
   *
   * @param resource resource info to add (id should be null)
   * @return new resource object with valid id
   */
  public Account add(Account resource) throws RequestValidationException {
    Account saved = mapper.toModel(repository.save(mapper.toEntity(resource)));
    return saved;
  }

  /**
   * find all resources matching last name.
   *
   * @param lastName criteria for match
   * @return list of matching Account records
   */
  public Page<Account> findByLastName(String lastName, Pageable pageable) {
    log.info("looking up by lastname of:{}", lastName);
    Page<Account> responseList = mapper.toModelPage(repository.findByLastName(lastName, pageable));
    log.info("Response list size:{}", responseList.getContent().size());
    return responseList;
  }

  /**
   * find resource by user name.
   *
   * @param userName username criteria to match
   * @return matching record, or null
   */
  public Optional<Account> findByUserName(String userName) {
    log.info("looking up by username:{}", userName);
    Optional<Account> resource = mapper.toModel(repository.findByUserName(userName));
    return resource;
  }

  @Override
  public Optional<Account> findById(String id) {
    Optional<Account> resource = mapper.toModel(repository.findById(id));
    return resource;
  }

  @Override
  public Page<Account> findAll(Pageable pageable) {
    Page<Account> resource = mapper.toModelPage(repository.findAll(pageable));
    return resource;
  }

  @Override
  public Optional<Account> updateById(String id, Account record) throws RequestValidationException {
    Optional<Account> resource =
        mapper.toModel(
            repository
                .findById(id)
                .map((obj) -> mapper.updateMetadata(record, obj))
                .map((obj) -> repository.save(obj)));

    return resource;
  }

  @Override
  public Optional<Account> deleteById(String id) {
    Optional<Account> resource = findById(id);
    repository.deleteById(id);
    return resource;
  }
}
