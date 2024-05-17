//package com.piachimov.springreactive.config;
//
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.extension.BeforeAllCallback;
//import org.junit.jupiter.api.extension.ExtensionContext;
//import org.springframework.test.context.DynamicPropertyRegistry;
//
//import static java.lang.String.format;
//
//@RequiredArgsConstructor
//public class DatabaseSetupExtension implements BeforeAllCallback {
//
//    private final DynamicPropertyRegistry registry;
//
//    @Override
//    public void beforeAll(ExtensionContext context) {
//        TestPostgreSQLContainer.POSTGRES_CONTAINER.start();
////        setDatasourceProperties(TestPostgreSQLContainer.POSTGRES_CONTAINER);
//        updateDataSourceProps(TestPostgreSQLContainer.POSTGRES_CONTAINER);
//        //migration logic here (if needed)
//    }
//
//    static {
//        TestPostgreSQLContainer.POSTGRES_CONTAINER.start();
//    }
//
////    @DynamicPropertySource
////    private static void setDatasourceProperties(TestPostgreSQLContainer container) {
////
//////        // JDBC DataSource Example
//////        registry.add("spring.datasource.url", container::getJdbcUrl);
//////        registry.add("spring.datasource.password", container::getPassword);
//////        registry.add("spring.datasource.username", container::getUsername);
////
////        // R2DBC DataSource Example
////        registry.add("spring.r2dbc.url", () ->
////                format("r2dbc:pool:postgresql://%s:%d/%s",
////                        container.getHost(),
////                        container.getFirstMappedPort(),
////                        container.getDatabaseName()));
////        registry.add("spring.r2dbc.username", container::getUsername);
////        registry.add("spring.r2dbc.password", container::getPassword);
////    }
//
//    private void updateDataSourceProps(TestPostgreSQLContainer container) {
//        System.setProperty("spring.datasource.url", buildPostgresUrl(container));
//        System.setProperty("spring.r2dbc.username", container.getUsername());
//        System.setProperty("spring.r2dbc.password", container.getPassword());
//    }
//
//    private String buildPostgresUrl(TestPostgreSQLContainer container) {
//        return format("r2dbc:pool:postgresql://%s:%d/%s",
//                container.getHost(),
//                container.getFirstMappedPort(),
//                container.getDatabaseName());
//    }
//}
