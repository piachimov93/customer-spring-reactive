package com.piachimov.springreactive.handler;

import com.piachimov.springreactive.dao.CustomerDao;
import com.piachimov.springreactive.dao.CustomerRequestDto;
import com.piachimov.springreactive.dao.CustomerResponseDto;
import com.piachimov.springreactive.dto.Customer;
import com.piachimov.springreactive.exception.CustomerApiException;
import com.piachimov.springreactive.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.piachimov.springreactive.utils.CustomerUtils.mapEntityToResponseDto;

@Service
@RequiredArgsConstructor
public class CustomerStreamHandler {

    private final CustomerDao customerDao;
    private final CustomerService customerService;


    public Mono<ServerResponse> loadCustomersStream(ServerRequest serverRequest) {
        Flux<Customer> customersStream = customerDao.getCustomersStream();
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(customersStream, Customer.class);
    }

    public Mono<ServerResponse> loadCustomerFromRepository(ServerRequest serverRequest) {
        Flux<CustomerResponseDto> customers = customerService.loadCustomerEntityFromRepository();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(customers, CustomerResponseDto.class);
    }

    public Mono<ServerResponse> createCustomer(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CustomerRequestDto.class)
                .flatMap(customerService::createCustomer)
                .flatMap(customerResponseDto -> ServerResponse.ok().body(Mono.just(customerResponseDto), CustomerResponseDto.class));
    }

    public Mono<ServerResponse> deleteCustomerById(ServerRequest serverRequest) {
        Long id = Long.valueOf(serverRequest.pathVariable("id"));
        return ServerResponse.ok()
                .body(customerService.deleteCustomerById(id), Void.class);
    }

    public Mono<ServerResponse> loadCustomerById(ServerRequest serverRequest) {
        Long id = Long.valueOf(serverRequest.pathVariable("id"));

        var customerMono = customerService.findByCustomerId(id)
                .map(mapEntityToResponseDto)
                .switchIfEmpty(Mono.error(new CustomerApiException("Book not found with bookId : " + id)));

        return ServerResponse.ok().body(customerMono, CustomerResponseDto.class);
    }
}
