package io.twdps.starter.example.service.provider.subaccount.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import io.twdps.starter.boot.test.data.provider.NamedDataFactory;
import io.twdps.starter.example.data.subaccount.model.SubAccountData;
import io.twdps.starter.example.data.subaccount.provider.SubAccountDataFactory;
import io.twdps.starter.example.data.subaccount.provider.SubAccountTestData;
import io.twdps.starter.example.persistence.model.SubAccountEntity;
import io.twdps.starter.example.service.spi.subaccount.model.SubAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SubAccountEntityMapperTest {

  private SubAccountEntityMapper mapper;
  private final String parentIdentifier = "abcde";

  private SubAccountTestData resourceTestDataLoader = new SubAccountTestData();
  private SubAccountDataFactory resourceTestData =
      new SubAccountDataFactory(resourceTestDataLoader);

  private SubAccountData reference;

  /** Setup mapper and test data factory before each test. */
  @BeforeEach
  public void setup() {
    mapper = Mappers.getMapper(SubAccountEntityMapper.class);

    reference = resourceTestData.createBySpec(NamedDataFactory.DEFAULT_SPEC);
  }

  @Test
  public void mapperNewSubAccountTest() {
    SubAccount resource = createSubAccount(null);

    SubAccountEntity response = mapper.toEntity(resource);

    verifySubAccountEntity(response, false, false);
  }

  @Test
  public void mapperSubAccountTest() {
    SubAccount resource = createSubAccount(reference.getId());

    SubAccountEntity response = mapper.toEntity(resource);

    verifySubAccountEntity(response, true, false);
  }

  @Test
  public void mapperEntityTest() {
    SubAccountEntity entity = createSubAccountEntity();

    SubAccount response = mapper.toModel(entity);

    verifySubAccount(response);
  }

  @Test
  public void mapperOptionalEntityTest() {
    Optional<SubAccountEntity> entity = Optional.of(createSubAccountEntity());

    Optional<SubAccount> response = mapper.toModel(entity);

    assertThat(response.isPresent());
    verifySubAccount(response.get());
  }

  @Test
  public void mapperOptionalTest() {
    Optional<SubAccount> resource = Optional.of(createSubAccount(null));

    Optional<SubAccountEntity> response = mapper.toEntity(resource);

    assertThat(response.isPresent());
    verifySubAccountEntity(response.get(), false, false);
  }

  @Test
  public void mapperOptionalNullTest() {
    Optional<SubAccount> resource = Optional.ofNullable(null);

    Optional<SubAccountEntity> response = mapper.toEntity(resource);

    assertThat(response.isEmpty());
  }

  @Test
  public void mapperOptionalEmptyTest() {
    Optional<SubAccount> resource = Optional.empty();

    Optional<SubAccountEntity> response = mapper.toEntity(resource);

    assertThat(response.isEmpty());
  }

  @Test
  public void mapperEntityListTest() {
    List<SubAccountEntity> entities =
        Arrays.asList(createSubAccountEntity(), createSubAccountEntity());

    List<SubAccount> response = mapper.toModelList(entities);

    assertThat(response.size()).isEqualTo(2);
    verifySubAccount(response.get(0));
    verifySubAccount(response.get(1));
  }

  @Test
  public void mapperEntityPageTest() {
    Pageable pageable = PageRequest.of(0, 3);
    Page<SubAccountEntity> entities =
        new PageImpl<>(
            Arrays.asList(
                createSubAccountEntity(), createSubAccountEntity(), createSubAccountEntity()),
            pageable,
            100);

    Page<SubAccount> response = mapper.toModelPage(entities);

    assertThat(response.getContent().size()).isEqualTo(3);
    assertThat(response.getTotalElements()).isEqualTo(100);
    assertThat(response.getNumber()).isEqualTo(0);
    assertThat(response.getNumberOfElements()).isEqualTo(3);

    verifySubAccount(response.toList().get(0));
    verifySubAccount(response.toList().get(1));
    verifySubAccount(response.toList().get(2));
  }

  /**
   * convenience function to create resource object.
   *
   * @param id whether to create with identifier (null if not)
   * @return SubAccount object
   */
  private SubAccount createSubAccount(String id) {
    return new SubAccount(
        id,
        reference.getUserName(),
        reference.getPii(),
        reference.getFirstName(),
        reference.getLastName());
  }

  /**
   * convenience function to create resource entity object.
   *
   * @return SubAccountEntity object
   */
  private SubAccountEntity createSubAccountEntity() {
    return new SubAccountEntity(
        reference.getId(),
        reference.getUserName(),
        reference.getPii(),
        reference.getFirstName(),
        reference.getLastName(),
        parentIdentifier);
  }

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  protected void verifySubAccount(SubAccount response) {
    assertThat(response.getUserName()).isEqualTo(reference.getUserName());
    assertThat(response.getPii()).isEqualTo(reference.getPii());
    assertThat(response.getFirstName()).isEqualTo(reference.getFirstName());
    assertThat(response.getLastName()).isEqualTo(reference.getLastName());
    assertThat(response.getId()).isEqualTo(reference.getId());
  }

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  private void verifySubAccountEntity(SubAccountEntity response) {
    verifySubAccountEntity(response, true, true);
  }

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  // CSOFF: LineLength
  private void verifySubAccountEntity(
      SubAccountEntity response, boolean hasId, boolean hasParentId) {
    // CSON: LineLength
    assertThat(response.getUserName()).isEqualTo(reference.getUserName());
    assertThat(response.getPii()).isEqualTo(reference.getPii());
    assertThat(response.getFirstName()).isEqualTo(reference.getFirstName());
    assertThat(response.getLastName()).isEqualTo(reference.getLastName());
    if (hasParentId) {
      assertThat(response.getAccountId()).isEqualTo(parentIdentifier);
    } else {
      assertThat(response.getAccountId()).isNotEqualTo(parentIdentifier);
    }
    if (hasId) {
      assertThat(response.getId()).isEqualTo(reference.getId());
    } else {
      assertThat(response.getId()).isNotEqualTo(reference.getId());
    }
  }
}
