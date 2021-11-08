package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.controller.{{cookiecutter.PKG_RESOURCE_NAME}};

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.errorhandling.advice.ErrorHandlerAdvice;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.errorhandling.config.ErrorHandlerConfig;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.exception.RequestValidationException;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.exception.ResourceNotFoundException;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.openapi.config.OpenApiConfiguration;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.test.data.provider.NamedDataFactory;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.SecurityAllowConfig;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.requests.{{cookiecutter.RESOURCE_NAME}}Request;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.resources.{{cookiecutter.RESOURCE_NAME}}Resource;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.controller.{{cookiecutter.PKG_RESOURCE_NAME}}.mapper.{{cookiecutter.RESOURCE_NAME}}RequestMapper;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.RESOURCE_NAME}}Data;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_RESOURCE_NAME}}.provider.{{cookiecutter.RESOURCE_NAME}}DataFactory;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_RESOURCE_NAME}}.provider.{{cookiecutter.RESOURCE_NAME}}DataProperties;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.{{cookiecutter.RESOURCE_NAME}}Service;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.RESOURCE_NAME}};
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.zalando.problem.Problem;

@Slf4j
@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest({{cookiecutter.RESOURCE_NAME}}Resource.class)
@ContextConfiguration(
    classes = {
      SecurityAllowConfig.class,
      ErrorHandlerAdvice.class,
      OpenApiConfiguration.class,
      ErrorHandlerConfig.class,
      {{cookiecutter.RESOURCE_NAME}}DataFactory.class,
      {{cookiecutter.RESOURCE_NAME}}DataProperties.class
    })
@TestPropertySource(properties = {"spring.config.location=classpath:application-{{cookiecutter.PKG_RESOURCE_NAME}}.yml,classpath:application.yml"})
class {{cookiecutter.RESOURCE_NAME}}ErrorHandlingContextTest {
  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private {{cookiecutter.RESOURCE_NAME}}RequestMapper mapper;

  @MockBean private {{cookiecutter.RESOURCE_NAME}}Service service;

  @MockBean private {{cookiecutter.RESOURCE_NAME}}Resource controller;

  @Autowired private {{cookiecutter.RESOURCE_NAME}}DataFactory testData;

  private {{cookiecutter.RESOURCE_NAME}}Data reference;
  private {{cookiecutter.RESOURCE_NAME}}Data bogus;

  private final String message = "message";
  private final String traceHeaderName = "X-B3-TraceId";
  private final String traceInfo = "00-4bf92f3577b34da6a3ce929d0e0e4736-00f067aa0ba902b7-01";
  private final String baseUrl = "https://starter.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_TL_NAME}}";
  private final String notFoundType = String.format("%s/not-found", baseUrl);
  private final String requestValidationType = String.format("%s/request-validation", baseUrl);

  // This object will be magically initialized by the initFields method below.

  @Autowired private JacksonTester<{{cookiecutter.RESOURCE_NAME}}Request> jsonRequest;
  private {{cookiecutter.RESOURCE_NAME}}Request request;
  private {{cookiecutter.RESOURCE_NAME}} model;

  /** Setup mapper and test data factory before each test. */
  @BeforeEach
  public void setup() {
    reference = testData.createBySpec(NamedDataFactory.DEFAULT_SPEC);
    bogus = testData.createBySpec("bogus");

    request =
       new {{cookiecutter.RESOURCE_NAME}}Request(
           reference.getUserName(),
           reference.getPii(),
           reference.getFirstName(),
           reference.getLastName()
{%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %},
          reference.get{{cookiecutter.PARENT_RESOURCE_NAME}}Id()
{%- endif %});
    model =
       new {{cookiecutter.RESOURCE_NAME}}(
           reference.getUserName(),
           reference.getPii(),
           reference.getFirstName(),
           reference.getLastName());
  }

  @Test
  void whenResourceNotRetrieved_thenReturns404() throws Exception {
    Mockito.when(controller.findEntityById(bogus.getId())).thenThrow(new ResourceNotFoundException(bogus.getId()));

    // when
    MockHttpServletResponse response =
        mockMvc
            .perform(
                get(String.format("/v1/{{cookiecutter.SERVICE_URL}}/{{cookiecutter.RESOURCE_URL}}/%s", bogus.getId()))
                    .header(traceHeaderName, traceInfo)
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    String content = response.getContentAsString();
    log.info("result [{}]", content);

    // then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

    Problem error = objectMapper.readValue(content, Problem.class);
    assertThat(error.getStatus().getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    assertThat(error.getType().toString()).isEqualTo(notFoundType);
    assertThat(error.getInstance().toString())
        .isEqualTo("%s/%s", baseUrl, traceInfo);
    assertThat(error.getDetail()).isEqualTo("Resource '%s' not found", bogus.getId());
  }

  @Test
  void whenResourceNotFound_thenReturns404() throws Exception {
    Mockito.when(controller.findEntityById(bogus.getId())).thenThrow(new ResourceNotFoundException(bogus.getId()));

    // when
    MockHttpServletResponse response =
        mockMvc
            .perform(
                get(String.format("/v1/{{cookiecutter.SERVICE_URL}}/{{cookiecutter.RESOURCE_URL}}/%s", bogus.getId()))
                    .header(traceHeaderName, traceInfo)
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    String content = response.getContentAsString();
    log.info("result [{}]", content);

    // then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

    Problem error = objectMapper.readValue(content, Problem.class);
    assertThat(error.getStatus().getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    assertThat(error.getType().toString()).isEqualTo(notFoundType);
    assertThat(error.getInstance().toString())
        .isEqualTo("%s/%s", baseUrl, traceInfo);
    assertThat(error.getDetail()).isEqualTo("Resource '%s' not found", bogus.getId());
  }

  @Test
  void whenHttpMessageNotReadable_thenReturns400() throws Exception {

    String requestMessage =
        "{ \"userName\": null, \"pii\": null, \"firstName\": null, \"lastName\": null}";

    // when
    MockHttpServletResponse response =
        mockMvc
            .perform(
                post("/v1/{{cookiecutter.SERVICE_URL}}/{{cookiecutter.RESOURCE_URL}}")
                    .header(traceHeaderName, traceInfo)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestMessage))
            .andReturn()
            .getResponse();

    String content = response.getContentAsString();
    log.info("result [{}]", content);

    // then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    Problem error = objectMapper.readValue(content, Problem.class);
    assertThat(error.getStatus().getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(error.getType().toString()).isEqualTo(requestValidationType);
    assertThat(error.getInstance().toString())
        .isEqualTo("%s/%s", baseUrl, traceInfo);
    assertThat(error.getDetail()).contains("userName is marked non-null but is null");
  }

  @Test
  void whenRequestNotValid_thenReturns400() throws Exception {
    Mockito.when(controller.addEntity(Mockito.any()))
        .thenThrow(new RequestValidationException(message));

    // when
    MockHttpServletResponse response =
        mockMvc
            .perform(
                post("/v1/{{cookiecutter.SERVICE_URL}}/{{cookiecutter.RESOURCE_URL}}")
                    .header(traceHeaderName, traceInfo)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest.write(request).getJson()))
            .andReturn()
            .getResponse();

    String content = response.getContentAsString();
    log.info("result [{}]", content);

    // then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    Problem error = objectMapper.readValue(content, Problem.class);
    assertThat(error.getStatus().getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(error.getType().toString()).isEqualTo(requestValidationType);
    assertThat(error.getInstance().toString())
        .isEqualTo("%s/%s", baseUrl, traceInfo);
    assertThat(error.getDetail()).isEqualTo("Resource 'message' invalid request");
  }

  @Test
  void whenRequestBodyMissing_thenReturns400() throws Exception {

    // when
    MockHttpServletResponse response =
        mockMvc
            .perform(
                post("/v1/{{cookiecutter.SERVICE_URL}}/{{cookiecutter.RESOURCE_URL}}")
                    .header(traceHeaderName, traceInfo)
                    .contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    String content = response.getContentAsString();
    log.info("result [{}]", content);

    // then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    Problem error = objectMapper.readValue(content, Problem.class);
    assertThat(error.getStatus().getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(error.getType().toString()).isEqualTo(requestValidationType);
    assertThat(error.getInstance().toString())
        .isEqualTo("%s/%s", baseUrl, traceInfo);
    assertThat(error.getDetail()).contains("Required request body is missing");
  }
}
