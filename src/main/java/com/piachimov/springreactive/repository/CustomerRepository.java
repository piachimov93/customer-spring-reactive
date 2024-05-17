package com.piachimov.springreactive.repository;

import com.piachimov.springreactive.model.CustomerEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface CustomerRepository extends R2dbcRepository<CustomerEntity, Long> {
}
