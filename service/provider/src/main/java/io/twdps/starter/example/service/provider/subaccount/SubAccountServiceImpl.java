package io.twdps.starter.example.service.provider.subaccount;

import io.twdps.starter.boot.exception.RequestValidationException;
import io.twdps.starter.example.persistence.model.SubAccountEntityRepository;
import io.twdps.starter.example.service.provider.subaccount.mapper.SubAccountEntityMapper;
import io.twdps.starter.example.service.spi.subaccount.SubAccountService;
import io.twdps.starter.example.service.spi.subaccount.model.SubAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class SubAccountServiceImpl implements SubAccountService {

  private SubAccountEntityRepository repository;
  private SubAccountEntityMapper mapper;

  SubAccountServiceImpl(SubAccountEntityRepository repository, SubAccountEntityMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  /**
   * add a new SubAccount entity.
   *
   * @param resource resource info to add (id should be null)
   * @return new resource object with valid id
   */
  @Override
  public SubAccount add(SubAccount resource) throws RequestValidationException {
    SubAccount saved = mapper.toModel(repository.save(mapper.toEntity(resource)));
    return saved;
  }

  /**
   * find all resources matching last name.
   *
   * @param lastName criteria for match
   * @return list of matching SubAccount records
   */
  @Override
  public Page<SubAccount> findByLastName(String lastName, Pageable pageable) {
    log.info("looking up by lastname of:{}", lastName);
    Page<SubAccount> responseList =
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
  @Override
  public Optional<SubAccount> findByUserName(String userName) {
    log.info("looking up by username:{}", userName);
    Optional<SubAccount> resource = mapper.toModel(repository.findByUserName(userName));
    return resource;
  }

  @Override
  public Optional<SubAccount> findById(String id) {
    Optional<SubAccount> resource = mapper.toModel(repository.findById(id));
    return resource;
  }

  @Override
  public Page<SubAccount> findAll(Pageable pageable) {
    Page<SubAccount> resource = mapper.toModelPage(repository.findAll(pageable));
    return resource;
  }

  @Override
  // CSOFF: LineLength
  public Optional<SubAccount> updateById(String id, SubAccount record)
      // CSON: LineLength
      throws RequestValidationException {
    Optional<SubAccount> resource =
        mapper.toModel(
            repository
                .findById(id)
                .map((obj) -> mapper.updateMetadata(record, obj))
                .map((obj) -> repository.save(obj)));

    return resource;
  }

  @Override
  public Optional<SubAccount> deleteById(String id) {
    Optional<SubAccount> resource = findById(id);
    repository.deleteById(id);
    return resource;
  }
}
