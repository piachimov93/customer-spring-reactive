//package com.piachimov.springreactive.config;
//
//import org.testcontainers.containers.PostgreSQLContainer;
//
//public class TestPostgreSQLContainer extends PostgreSQLContainer<TestPostgreSQLContainer> {
//
//    private static final String IMAGE_VERSION = "postgres:13.3-alpine";
//    private static final String DATABASE_NAME = "customer_db";
//    private static final String USERNAME = "customer_user";
//    private static final String PASSWORD = "password";
//    private static final boolean IS_REUSE = true;
//
//    public static final TestPostgreSQLContainer POSTGRES_CONTAINER =
//            new TestPostgreSQLContainer()
//                    .withDatabaseName(DATABASE_NAME)
//                    .withUsername(USERNAME)
//                    .withPassword(PASSWORD)
//                    .withReuse(IS_REUSE);
//
//
//    public TestPostgreSQLContainer() {
//        super(IMAGE_VERSION);
//    }
//}
