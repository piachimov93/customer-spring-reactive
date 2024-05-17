package com.piachimov.springreactive.handler;

import com.piachimov.springreactive.dao.CustomerDao;
import com.piachimov.springreactive.dto.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomerHandler {

    private final CustomerDao customerDao;


    public Mono<ServerResponse> loadCustomers(ServerRequest serverRequest) {
        Flux<Customer> customerList = customerDao.getCustomerList();
        return ServerResponse.ok().body(customerList, Customer.class);
    }
    public Mono<ServerResponse> loadCustomerById(ServerRequest serverRequest) {
        Integer id = Integer.valueOf(serverRequest.pathVariable("id"));
        Mono<Customer> customer = customerDao.getCustomerList().filter(c -> Objects.equals(c.id(), id)).take(1).single();
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(customer, Customer.class);
    }
}
