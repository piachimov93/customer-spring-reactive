package com.piachimov.springreactive.service;

import com.piachimov.springreactive.repository.CustomerRepository;
import com.piachimov.springreactive.dao.CustomerDao;
import com.piachimov.springreactive.dao.CustomerRequestDto;
import com.piachimov.springreactive.dao.CustomerResponseDto;
import com.piachimov.springreactive.dto.Customer;
import com.piachimov.springreactive.model.CustomerEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.piachimov.springreactive.utils.CustomerUtils.mapEntityToResponseDto;
import static com.piachimov.springreactive.utils.CustomerUtils.mapRequestDtoToEntity;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerDao customerDao;
    private final CustomerRepository customerRepository;

    public List<Customer> loadCustomers() {
        var start = System.currentTimeMillis();
        List<Customer> customers = customerDao.getCustomers();
        var end = System.currentTimeMillis();

        log.info("Total time to load customers: {}", end - start);
        return customers;
    }

    public Flux<Customer> loadCustomersStream() {
        return customerDao.getCustomersStream();
    }

    public Flux<CustomerResponseDto> loadCustomerEntityFromRepository() {
        return customerRepository.findAll().map(mapEntityToResponseDto);
    }

    public Mono<CustomerResponseDto> createCustomer(CustomerRequestDto customerRequestDto) {
        CustomerEntity customerEntity = mapRequestDtoToEntity.apply(customerRequestDto);

        return customerRepository.save(customerEntity).map(mapEntityToResponseDto);
    }

    public Mono<Void> deleteCustomerById(Long id) {
        return customerRepository.deleteById(id);
    }

    public Mono<CustomerEntity> findByCustomerId(Long id) {
        return customerRepository.findById(id);
    }
}
