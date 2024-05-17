//package com.piachimov.springreactive;
//
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import static java.lang.String.format;
//
//@Testcontainers
//public class TestContainerUtils {
//
//    public static final PostgreSQLContainer<?> POSTGRES_CONTAINER =
//            new PostgreSQLContainer<>("postgres:13.3-alpine")
//                    .withDatabaseName("customer_db")
//                    .withUsername("customer_user")
//                    .withPassword("password");
//    @DynamicPropertySource
//    private static void setDatasourceProperties(DynamicPropertyRegistry registry) {
//
//        // JDBC DataSource Example
//        registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
//        registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
//        registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
//
//        // R2DBC DataSource Example
//        registry.add("spring.r2dbc.url", () ->
//                format("r2dbc:pool:postgresql://%s:%d/%s",
//                        POSTGRES_CONTAINER.getHost(),
//                        POSTGRES_CONTAINER.getFirstMappedPort(),
//                        POSTGRES_CONTAINER.getDatabaseName()));
//        registry.add("spring.r2dbc.username", POSTGRES_CONTAINER::getUsername);
//        registry.add("spring.r2dbc.password", POSTGRES_CONTAINER::getPassword);
//    }
//
//    public static void startPostgresContainer() {
//        POSTGRES_CONTAINER.start();
//    }
//}
