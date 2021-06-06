package io.twdps.starter.example.controller.subaccount;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.twdps.starter.boot.errorhandling.advice.ErrorHandlerAdvice;
import io.twdps.starter.boot.errorhandling.config.ErrorHandlerConfig;
import io.twdps.starter.boot.exception.RequestValidationException;
import io.twdps.starter.boot.exception.ResourceNotFoundException;
import io.twdps.starter.boot.openapi.config.OpenApiConfiguration;
import io.twdps.starter.boot.test.data.spi.DataFactory;
import io.twdps.starter.example.SecurityAllowConfig;
import io.twdps.starter.example.api.subaccount.requests.SubAccountRequest;
import io.twdps.starter.example.api.subaccount.resources.SubAccountResource;
import io.twdps.starter.example.controller.subaccount.mapper.SubAccountRequestMapper;
import io.twdps.starter.example.data.subaccount.model.SubAccountData;
import io.twdps.starter.example.data.subaccount.provider.SubAccountDataFactory;
import io.twdps.starter.example.data.subaccount.provider.SubAccountDataProperties;
import io.twdps.starter.example.service.spi.subaccount.SubAccountService;
import io.twdps.starter.example.service.spi.subaccount.model.SubAccount;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.zalando.problem.Problem;

@Slf4j
@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(SubAccountResource.class)
@ContextConfiguration(
    classes = {
      SecurityAllowConfig.class,
      ErrorHandlerAdvice.class,
      OpenApiConfiguration.class,
      ErrorHandlerConfig.class,
      SubAccountDataFactory.class,
      SubAccountDataProperties.class
    })
class SubAccountErrorHandlingContextTest {
  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private SubAccountRequestMapper mapper;

  @MockBean private SubAccountService service;

  @MockBean private SubAccountResource controller;

  @Autowired private SubAccountDataFactory testData;

  private SubAccountData reference;
  private SubAccountData bogus;

  private final String message = "message";
  private final String traceHeaderName = "X-B3-TraceId";
  private final String traceInfo = "00-4bf92f3577b34da6a3ce929d0e0e4736-00f067aa0ba902b7-01";
  private final String baseUrl = "https://starter.twdps.io";
  private final String notFoundType = String.format("%s/not-found", baseUrl);
  private final String requestValidationType = String.format("%s/request-validation", baseUrl);

  // This object will be magically initialized by the initFields method below.

  @Autowired private JacksonTester<SubAccountRequest> jsonRequest;
  private SubAccountRequest request;
  private SubAccount model;

  /** Setup mapper and test data factory before each test. */
  @BeforeEach
  public void setup() {
    reference = testData.getNamedData(DataFactory.DEFAULT_NAME);
    bogus = testData.getNamedData("bogus");

    request =
        new SubAccountRequest(
            reference.getUserName(),
            reference.getPii(),
            reference.getFirstName(),
            reference.getLastName());
    model =
        new SubAccount(
            reference.getUserName(),
            reference.getPii(),
            reference.getFirstName(),
            reference.getLastName());
  }

  @Test
  void whenResourceNotRetrieved_thenReturns404() throws Exception {
    Mockito.when(controller.findEntityById(bogus.getId()))
        .thenThrow(new ResourceNotFoundException(bogus.getId()));

    // when
    MockHttpServletResponse response =
        mockMvc
            .perform(
                get(String.format("/v1/example/subaccounts/%s", bogus.getId()))
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
    assertThat(error.getInstance().toString()).isEqualTo("%s/%s", baseUrl, traceInfo);
    assertThat(error.getDetail()).isEqualTo("Resource '%s' not found", bogus.getId());
  }

  @Test
  void whenResourceNotFound_thenReturns404() throws Exception {
    Mockito.when(controller.findEntityById(bogus.getId()))
        .thenThrow(new ResourceNotFoundException(bogus.getId()));

    // when
    MockHttpServletResponse response =
        mockMvc
            .perform(
                get(String.format("/v1/example/subaccounts/%s", bogus.getId()))
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
    assertThat(error.getInstance().toString()).isEqualTo("%s/%s", baseUrl, traceInfo);
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
                post("/v1/example/subaccounts")
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
    assertThat(error.getInstance().toString()).isEqualTo("%s/%s", baseUrl, traceInfo);
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
                post("/v1/example/subaccounts")
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
    assertThat(error.getInstance().toString()).isEqualTo("%s/%s", baseUrl, traceInfo);
    assertThat(error.getDetail()).isEqualTo("Resource 'message' invalid request");
  }

  @Test
  void whenRequestBodyMissing_thenReturns400() throws Exception {

    // when
    MockHttpServletResponse response =
        mockMvc
            .perform(
                post("/v1/example/subaccounts")
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
    assertThat(error.getInstance().toString()).isEqualTo("%s/%s", baseUrl, traceInfo);
    assertThat(error.getDetail()).contains("Required request body is missing");
  }
}
