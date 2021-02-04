package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.provider.{{cookiecutter.PKG_RESOURCE_NAME}};

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.persistence.model.{{cookiecutter.RESOURCE_NAME}}Entity;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.persistence.model.{{cookiecutter.RESOURCE_NAME}}EntityRepository;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.provider.{{cookiecutter.PKG_RESOURCE_NAME}}.mapper.{{cookiecutter.RESOURCE_NAME}}EntityMapper;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.RESOURCE_NAME}};
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class {{cookiecutter.RESOURCE_NAME}}ServiceImplTest {

  private {{cookiecutter.RESOURCE_NAME}}ServiceImpl manager;

  @Mock private {{cookiecutter.RESOURCE_NAME}}EntityRepository repository;
  @Mock private {{cookiecutter.RESOURCE_NAME}}EntityMapper mapper;

  private final String username = "jsmith";
  private final String bogusName = "bogus";
  private final String firstName = "Joe";
  private final String lastName = "Smith";
  private final String identifier = "12345";

  private {{cookiecutter.RESOURCE_NAME}} resource;
  private {{cookiecutter.RESOURCE_NAME}} output;
  private {{cookiecutter.RESOURCE_NAME}}Entity entity;
  private {{cookiecutter.RESOURCE_NAME}}Entity added;
  private Optional<{{cookiecutter.RESOURCE_NAME}}> empty{{cookiecutter.RESOURCE_NAME}} = Optional.empty();
  private Optional<{{cookiecutter.RESOURCE_NAME}}Entity> emptyEntity = Optional.empty();
  private Optional<{{cookiecutter.RESOURCE_NAME}}Entity> optionalEntity;
  private Optional<{{cookiecutter.RESOURCE_NAME}}Entity> optionalAdded;
  private Optional<{{cookiecutter.RESOURCE_NAME}}> optionalOutput;
  private List<{{cookiecutter.RESOURCE_NAME}}Entity> entityList;
  private List<{{cookiecutter.RESOURCE_NAME}}> outputList;
  private List<{{cookiecutter.RESOURCE_NAME}}Entity> emptyEntityList = Arrays.asList();
  private List<{{cookiecutter.RESOURCE_NAME}}> emptyOutputList = Arrays.asList();

  /** setup data for each test. */
  @BeforeEach
  public void setup() {

    manager = new {{cookiecutter.RESOURCE_NAME}}ServiceImpl(repository, mapper);

    // use the real mapper to generate consistent objects to use in mapper stubs
    {{cookiecutter.RESOURCE_NAME}}EntityMapper real = Mappers.getMapper({{cookiecutter.RESOURCE_NAME}}EntityMapper.class);

    resource = {{cookiecutter.RESOURCE_NAME}}.builder().userName(username).firstName(firstName).lastName(lastName).build();
    entity = real.toEntity(resource);
    added =
        new {{cookiecutter.RESOURCE_NAME}}Entity(
            identifier, entity.getUserName(), entity.getFirstName(), entity.getLastName());
    output = real.toModel(added);
    optionalEntity = Optional.of(entity);
    optionalAdded = Optional.of(added);
    optionalOutput = Optional.of(output);
    entityList = Arrays.asList(added, added);
    outputList = Arrays.asList(output, output);
  }

  private void createMapperStubs() {
    Mockito.when(mapper.toEntity(resource)).thenReturn(entity);
    Mockito.when(mapper.toModel(added)).thenReturn(output);
  }

  private void createOptionalMapperStubs() {
    Mockito.when(mapper.toModel(optionalAdded)).thenReturn(optionalOutput);
  }

  private void createEmptyMapperStubs() {
    Mockito.when(mapper.toModel(emptyEntity)).thenReturn(empty{{cookiecutter.RESOURCE_NAME}});
  }

  private void createListMapperStubs() {
    Mockito.when(mapper.toModelList(entityList)).thenReturn(outputList);
  }

  private void createEmptyListMapperStubs() {
    Mockito.when(mapper.toModelList(emptyEntityList)).thenReturn(emptyOutputList);
  }

  @Test
  public void findBy{{cookiecutter.RESOURCE_NAME}}IdFailTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findById(Mockito.any())).thenReturn(emptyEntity);

    Optional<{{cookiecutter.RESOURCE_NAME}}> result = manager.findById("bogus");
    Assertions.assertThat(!result.isPresent()).isTrue();
  }

  @Test
  public void add{{cookiecutter.RESOURCE_NAME}}Test() {

    createMapperStubs();
    Mockito.when(repository.save(entity)).thenReturn(added);

    {{cookiecutter.RESOURCE_NAME}} response = manager.add(resource);

    Assertions.assertThat(response.getFirstName()).isEqualTo(resource.getFirstName());
    Assertions.assertThat(response.getId()).isEqualTo(added.getId());
  }

  @Test
  public void findByUserNameTest() {

    createOptionalMapperStubs();
    Mockito.when(repository.findByUserName(username)).thenReturn(optionalAdded);

    Optional<{{cookiecutter.RESOURCE_NAME}}> response = manager.findByUserName(username);

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(added.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(added.getId());
  }

  @Test
  public void findByUserNameFailedTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findByUserName(bogusName)).thenReturn(emptyEntity);

    Optional<{{cookiecutter.RESOURCE_NAME}}> response = manager.findByUserName(bogusName);

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

  @Test
  public void findByLastNameTest() {

    createListMapperStubs();
    Mockito.when(repository.findByLastName(username)).thenReturn(entityList);

    List<{{cookiecutter.RESOURCE_NAME}}> response = manager.findByLastName(username);

    Assertions.assertThat(response.isEmpty()).isFalse();
    Assertions.assertThat(response.get(0).getFirstName()).isEqualTo(added.getFirstName());
    Assertions.assertThat(response.get(0).getId()).isEqualTo(added.getId());
  }

  @Test
  public void findByLastNameFailedTest() {

    createEmptyListMapperStubs();
    Mockito.when(repository.findByLastName(bogusName)).thenReturn(Arrays.asList());

    List<{{cookiecutter.RESOURCE_NAME}}> response = manager.findByLastName(bogusName);

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

  @Test
  public void findByIdTest() {

    createOptionalMapperStubs();
    Mockito.when(repository.findById(identifier)).thenReturn(optionalAdded);

    Optional<{{cookiecutter.RESOURCE_NAME}}> response = manager.findById(identifier);

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(added.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(added.getId());
  }

  @Test
  public void findByIdFailedTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findById(bogusName)).thenReturn(emptyEntity);

    Optional<{{cookiecutter.RESOURCE_NAME}}> response = manager.findById(bogusName);

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

  @Test
  public void findAllTest() {

    createListMapperStubs();
    Mockito.when(repository.findAll()).thenReturn(entityList);

    List<{{cookiecutter.RESOURCE_NAME}}> response = manager.findAll();

    Assertions.assertThat(response.size()).isEqualTo(2);
  }

  @Test
  public void findAllEmptyTest() {

    createEmptyListMapperStubs();
    Mockito.when(repository.findAll()).thenReturn(emptyEntityList);

    List<{{cookiecutter.RESOURCE_NAME}}> response = manager.findAll();

    Assertions.assertThat(response.size()).isEqualTo(0);
  }

  @Test
  public void updateTest() {

    createOptionalMapperStubs();
    Mockito.when(mapper.updateMetadata(resource, added)).thenReturn(added);
    Mockito.when(repository.findById(identifier)).thenReturn(optionalAdded);
    Mockito.when(repository.save(added)).thenReturn(added);

    Optional<{{cookiecutter.RESOURCE_NAME}}> response = manager.updateById(identifier, resource);

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(resource.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(identifier);
  }

  @Test
  public void updateFailedTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findById(identifier)).thenReturn(emptyEntity);

    Optional<{{cookiecutter.RESOURCE_NAME}}> response = manager.updateById(identifier, resource);

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

  @Test
  public void deleteTest() {

    createOptionalMapperStubs();
    Mockito.when(repository.findById(identifier)).thenReturn(optionalAdded);

    Optional<{{cookiecutter.RESOURCE_NAME}}> response = manager.deleteById(identifier);

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(added.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(added.getId());
  }

  @Test
  public void deleteFailedTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findById(bogusName)).thenReturn(emptyEntity);

    Optional<{{cookiecutter.RESOURCE_NAME}}> response = manager.deleteById(bogusName);

    Assertions.assertThat(response.isEmpty()).isTrue();
  }
}
