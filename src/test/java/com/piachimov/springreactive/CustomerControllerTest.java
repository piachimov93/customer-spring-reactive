package com.piachimov.springreactive;

import com.piachimov.springreactive.dao.CustomerResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static java.lang.String.format;

@Testcontainers
@AutoConfigureWebTestClient(timeout = "3600000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ExtendWith(DatabaseSetupExtension.class)
public class CustomerControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    private static final PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>("postgres:13.3-alpine")
                    .withDatabaseName("customer_db")
                    .withUsername("customer_user")
                    .withPassword("password");

    static {
        postgresContainer.start();
    }

    @DynamicPropertySource
    private static void setDatasourceProperties(DynamicPropertyRegistry registry) {

        // JDBC DataSource Example
//        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
//        registry.add("spring.datasource.password", postgresContainer::getPassword);
//        registry.add("spring.datasource.username", postgresContainer::getUsername);

        // R2DBC DataSource Example
        registry.add("spring.r2dbc.url", () ->
                format("r2dbc:pool:postgresql://%s:%d/%s",
                        postgresContainer.getHost(),
                        postgresContainer.getFirstMappedPort(),
                        postgresContainer.getDatabaseName()));
        registry.add("spring.r2dbc.username", postgresContainer::getUsername);
        registry.add("spring.r2dbc.password", postgresContainer::getPassword);
    }

    @Test
    void createCustomerTest() {

        webTestClient.post()
                .uri("/api/router/repository")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("{\"name\":\"Jane Doe\"}"))
                .exchange()
                .expectStatus().isOk()
//                .expectBody().json("{\"id\":1,\"name\":\"Customer Name 1\"}");
                .expectBody().json("{\"name\":\"Jane Doe\"}");
    }

    @Test
    void getCustomerByIdTest() {
        webTestClient.get()
                .uri("/api/router/repository/customer/2")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(2L)
                .jsonPath("$.name").isEqualTo("Alice Smith");
    }

    @Test
    void getAllCustomersTestOne() {
        Flux<CustomerResponseDto> responseBody = webTestClient.get().uri("/api/router/repository")
                .exchange()
                .expectStatus().isOk()
                .returnResult(CustomerResponseDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(new CustomerResponseDto(1L, "John Doe"))
                .expectNext(new CustomerResponseDto(2L, "Alice Smith"))
                .expectNext(new CustomerResponseDto(3L, "Jane Doe"))
                .verifyComplete();
    }

    @Test
    void getAllCustomersTestTwo() {

        CustomerResponseDto customerResponseDtoOne = new CustomerResponseDto(1L, "John Doe");
        CustomerResponseDto customerResponseDtoTwo = new CustomerResponseDto(2L, "Alice Smith");
        CustomerResponseDto customerResponseDtoThree = new CustomerResponseDto(3L, "Jane Doe");

        webTestClient.get().uri("/api/router/repository")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CustomerResponseDto.class)
                .hasSize(3)
                .contains(customerResponseDtoOne, customerResponseDtoTwo, customerResponseDtoThree);
    }
}
