package io.twdps.starter.example.service.provider.account;

import io.twdps.starter.boot.exception.RequestValidationException;
import io.twdps.starter.example.persistence.model.AccountEntityRepository;
import io.twdps.starter.example.persistence.model.SubAccountEntity;
import io.twdps.starter.example.persistence.model.SubAccountEntityRepository;
import io.twdps.starter.example.service.provider.account.mapper.AccountEntityMapper;
import io.twdps.starter.example.service.spi.account.AccountService;
import io.twdps.starter.example.service.spi.account.model.Account;
import io.twdps.starter.example.service.spi.account.model.SubAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

  private AccountEntityRepository repository;
  private AccountEntityMapper mapper;
  private SubAccountEntityRepository subResourceRepository;

  AccountServiceImpl(
      AccountEntityRepository repository,
      AccountEntityMapper mapper,
      SubAccountEntityRepository subResourceRepository) {
    this.repository = repository;
    this.mapper = mapper;
    this.subResourceRepository = subResourceRepository;
  }

  /**
   * add a new Account entity.
   *
   * @param resource resource info to add (id should be null)
   * @return new resource object with valid id
   */
  public Account add(Account resource)
      throws RequestValidationException {
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
    Page<Account> responseList =
        mapper.toModelPage(repository.findByLastName(lastName, pageable));
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
  // CSOFF: LineLength
  public Optional<Account> updateById(String id, Account record)
      // CSON: LineLength
      throws RequestValidationException {
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

  /**
   * add a new SubAccount entity.
   *
   * @param id Account resource id
   * @param subResource resource info to add (id should be null)
   * @return new resource object with valid id
   */
  // CSOFF: LineLength
  public SubAccount addSubAccount(String id, SubAccount subResource)
      // CSON: LineLength
      throws RequestValidationException {
    SubAccountEntity entity = mapper.toSubAccountEntity(subResource);
    entity.setAccountId(id);
    SubAccount saved =
        mapper.toSubAccountModel(subResourceRepository.save(entity));
    return saved;
  }

  /**
   * find a SubAccount resource by resource id.
   *
   * @param id Account resource id
   * @param subResourceId id of the SubAccount
   * @return matching record, or null
   */
  @Override
  // CSOFF: LineLength
  public Optional<SubAccount> getSubAccount(String id, String subResourceId) {
    // CSON: LineLength
    Optional<SubAccount> resource =
        mapper.toSubAccountModel(subResourceRepository.findById(subResourceId));
    return resource;
  }

  /**
   * find all SubAccount resources related to Account.
   *
   * @param id Account resource id
   * @return list of SubAccount resources
   */
  @Override
  // CSOFF: LineLength
  public Page<SubAccount> getSubAccounts(String id, Pageable pageable) {
    // CSON: LineLength
    Page<SubAccount> resources =
        mapper.toSubAccountModelPage(
            subResourceRepository.findAllByAccountId(id, pageable));
    return resources;
  }


  /**
   * update a SubAccount resource based on id.
   *
   * @param id Account resource id
   * @param subResourceId SubAccount resource id
   * @param record SubAccount resource data
   * @return Optional<> reference to updated SubAccount resource
   */
  @Override
  // CSOFF: LineLength
  public Optional<SubAccount> updateSubAccount(String id, String subResourceId, SubAccount record)
      // CSON: LineLength
      throws RequestValidationException {
    Optional<SubAccount> resource = mapper.toSubAccountModel(
        subResourceRepository.findById(subResourceId)
            .map((obj) -> mapper.updateSubAccountMetadata(record, obj))
            .map((obj) -> subResourceRepository.save(obj)));

    return resource;
  }

  /**
   * delete a SubAccount resource based on id.
   *
   * @param id Account resource id
   * @param subResourceId SubAccount resource id
   * @return subResource SubAccount resource data
   */
  @Override
  // CSOFF: LineLength
  public Optional<SubAccount> deleteSubAccount(String id, String subResourceId) {
    // CSON: LineLength
    Optional<SubAccount> result =
        getSubAccount(id, subResourceId);
    subResourceRepository.deleteById(subResourceId);
    return result;
  }
}
