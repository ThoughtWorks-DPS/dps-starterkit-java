package io.twdps.starter.example.service.provider.account;

import io.twdps.starter.boot.exception.RequestValidationException;
import io.twdps.starter.example.service.provider.account.mapper.AccountSubAccountEntityMapper;
import io.twdps.starter.example.service.spi.account.AccountSubAccountService;
import io.twdps.starter.example.service.spi.account.model.SubAccount;
import io.twdps.starter.example.service.spi.subaccount.SubAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AccountSubAccountServiceImpl implements AccountSubAccountService {

  private AccountSubAccountEntityMapper mapper;
  private SubAccountService subResourceService;

  AccountSubAccountServiceImpl(
      AccountSubAccountEntityMapper mapper, SubAccountService subResourceService) {
    this.mapper = mapper;
    this.subResourceService = subResourceService;
  }

  /**
   * add a new SubAccount entity.
   *
   * @param parentId Account resource id
   * @param resource resource info to add (id should be null)
   * @return new resource object with valid id
   */
  @Override
  // CSOFF: LineLength
  public SubAccount add(String parentId, SubAccount resource)
      // CSON: LineLength
      throws RequestValidationException {
    SubAccount result =
        mapper.fromServiceModel(subResourceService.add(mapper.toServiceModel(resource, parentId)));
    return result;
  }

  /**
   * find a SubAccount resource by resource id.
   *
   * @param parentId Account resource id
   * @param id id of the SubAccount
   * @return matching record, or null
   */
  @Override
  // CSOFF: LineLength
  public Optional<SubAccount> findById(String parentId, String id) {
    // CSON: LineLength
    Optional<SubAccount> result =
        mapper.fromServiceModel(
            subResourceService
                .findById(id)
                // TODO: In lieu of JPA Specifications, we filter the result based on matching
                // parent resource
                .filter((r) -> ((null != r.getAccountId()) && r.getAccountId().equals(parentId))));
    return result;
  }

  /**
   * find all SubAccount resources related to Account.
   *
   * @param parentId Account resource id
   * @return list of SubAccount resources
   */
  @Override
  // CSOFF: LineLength
  public Page<SubAccount> findAll(String parentId, Pageable pageable) {
    // CSON: LineLength
    Page<SubAccount> resources =
        mapper.fromServiceModelPage(subResourceService.findAllByAccountId(parentId, pageable));
    return resources;
  }

  /**
   * update a SubAccount resource based on id.
   *
   * @param parentId Account resource id
   * @param id SubAccount resource id
   * @param record SubAccount resource data
   * @return Optional<> reference to updated SubAccount resource
   */
  @Override
  // CSOFF: LineLength
  public Optional<SubAccount> updateById(String parentId, String id, SubAccount record)
      // CSON: LineLength
      throws RequestValidationException {
    Optional<SubAccount> resource =
        mapper.fromServiceModel(
            subResourceService.updateById(id, mapper.toServiceModel(record, parentId)));

    return resource;
  }

  /**
   * delete a SubAccount resource based on id.
   *
   * @param parentId Account resource id
   * @param id SubAccount resource id
   * @return subResource SubAccount resource data
   */
  @Override
  // CSOFF: LineLength
  public Optional<SubAccount> deleteById(String parentId, String id) {
    // CSON: LineLength
    Optional<SubAccount> result = mapper.fromServiceModel(subResourceService.deleteById(id));
    return result;
  }
}
