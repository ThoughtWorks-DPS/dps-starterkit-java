package io.twdps.starter.example.controller.subaccount.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import io.twdps.starter.boot.test.data.spi.DataFactory;
import io.twdps.starter.example.api.responses.PagedResponse;
import io.twdps.starter.example.api.subaccount.requests.SubAccountRequest;
import io.twdps.starter.example.api.subaccount.responses.SubAccountResponse;
import io.twdps.starter.example.data.subaccount.model.SubAccountData;
import io.twdps.starter.example.data.subaccount.provider.SubAccountDataFactory;
import io.twdps.starter.example.data.subaccount.provider.SubAccountTestData;
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

public class SubAccountRequestMapperTest {

  private SubAccountRequestMapper mapper;

  private SubAccountTestData resourceTestDataLoader = new SubAccountTestData();
  private SubAccountDataFactory resourceTestData =
      new SubAccountDataFactory(resourceTestDataLoader);

  private SubAccountData reference;

  /** Setup mapper and test data factory before each test. */
  @BeforeEach
  public void setup() {
    mapper = Mappers.getMapper(SubAccountRequestMapper.class);

    reference = resourceTestData.getNamedData(DataFactory.DEFAULT_NAME);
  }

  @Test
  public void mapperNewSubAccountTest() {
    SubAccountRequest resource = createSubAccountRequest();

    SubAccount response = mapper.toModel(resource);

    verifySubAccount(response);
  }

  @Test
  public void mapperSubAccountResponseTest() {
    SubAccount resource = createSubAccount(reference.getId());

    SubAccountResponse response = mapper.toSubAccountResponse(resource);

    verifySubAccountResponse(response);
  }

  @Test
  public void mapperOptionalTest() {
    Optional<SubAccount> resource = Optional.of(createSubAccount(reference.getId()));

    SubAccountResponse response = mapper.toSubAccountResponse(resource);

    assertThat(response).isNotNull();
    verifySubAccountResponse(response);
  }

  @Test
  public void mapperOptionalNullTest() {
    Optional<SubAccount> resource = Optional.ofNullable(null);

    SubAccountResponse response = mapper.toSubAccountResponse(resource);

    assertThat(response).isNull();
  }

  @Test
  public void mapperOptionalEmptyTest() {
    Optional<SubAccount> resource = Optional.empty();

    SubAccountResponse response = mapper.toSubAccountResponse(resource);

    assertThat(response).isNull();
  }

  @Test
  public void mapperEntityListTest() {
    List<SubAccount> resources =
        Arrays.asList(createSubAccount(reference.getId()), createSubAccount(reference.getId()));

    List<SubAccountResponse> response = mapper.toSubAccountResponseList(resources);

    assertThat(response.size()).isEqualTo(2);
    verifySubAccountResponse(response.get(0));
    verifySubAccountResponse(response.get(1));
  }

  @Test
  public void mapperEntityPageTest() {
    Pageable pageable = PageRequest.of(0, 1);
    Page<SubAccount> resources =
        new PageImpl<>(Arrays.asList(createSubAccount(reference.getId())), pageable, 100);
    PagedResponse<SubAccountResponse> response = mapper.toSubAccountResponsePage(resources);

    assertThat(response.getItems().size()).isEqualTo(1);
    assertThat(response.getTotalItems()).isEqualTo(100);
    assertThat(response.getPageNumber()).isEqualTo(0);
    assertThat(response.getPageSize()).isEqualTo(1);
    assertThat(response.getTotalPages()).isEqualTo(100);
    verifySubAccountResponse(response.getItems().get(0));
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
   * convenience function to create resource request object.
   *
   * @return SubAccountRequest object
   */
  private SubAccountRequest createSubAccountRequest() {
    return new SubAccountRequest(
        reference.getUserName(),
        reference.getPii(),
        reference.getFirstName(),
        reference.getLastName());
  }

  /**
   * helper function to validate standard values.
   *
   * @param resource the object to validate
   */
  protected void verifySubAccount(SubAccount resource) {
    assertThat(resource.getUserName().equals(reference.getUserName()));
    assertThat(resource.getPii().equals(reference.getPii()));
    assertThat(resource.getFirstName().equals(reference.getFirstName()));
    assertThat(resource.getLastName().equals(reference.getLastName()));
    assertThat(resource.getId()).isNotEqualTo(reference.getId());
  }

  /**
   * helper function to validate standard values.
   *
   * @param response the object to validate
   */
  private void verifySubAccountResponse(SubAccountResponse response) {
    assertThat(response.getUserName().equals(reference.getUserName()));
    assertThat(response.getPii().equals(reference.getPii()));
    assertThat(response.getFullName().equals(reference.getFullName()));
    assertThat(response.getId()).isEqualTo(reference.getId());
  }
}
