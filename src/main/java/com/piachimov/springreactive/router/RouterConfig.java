package com.piachimov.springreactive.router;

import com.piachimov.springreactive.handler.CustomerHandler;
import com.piachimov.springreactive.handler.CustomerStreamHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {

    private final CustomerHandler customerHandler;
    private final CustomerStreamHandler customerStreamHandler;

    @Bean
    public WebProperties.Resources resources(){
        return new WebProperties.Resources();
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("/api/router/customer", customerHandler::loadCustomers)
                .GET("/api/router/customer/stream", customerStreamHandler::loadCustomersStream)
                .GET("/api/router/customer/{id}", customerHandler::loadCustomerById)
                .GET("/api/router/repository", customerStreamHandler::loadCustomerFromRepository)
                .POST("/api/router/repository", customerStreamHandler::createCustomer)
                .DELETE("/api/router/repository/customer/{id}", customerStreamHandler::deleteCustomerById)
                .GET("/api/router/repository/customer/{id}", customerStreamHandler::loadCustomerById)
                .build();
    }
}
