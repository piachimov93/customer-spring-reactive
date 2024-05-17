package com.piachimov.springreactive.utils;

import com.piachimov.springreactive.dao.CustomerRequestDto;
import com.piachimov.springreactive.dao.CustomerResponseDto;
import com.piachimov.springreactive.model.CustomerEntity;

import java.util.function.Function;

public class CustomerUtils {

    public static Function<CustomerRequestDto, CustomerEntity> mapRequestDtoToEntity = requestDto ->  {
        CustomerEntity entity = new CustomerEntity();
        entity.setName(requestDto.name());
        return entity;
    };

    public static Function <CustomerEntity, CustomerResponseDto> mapEntityToResponseDto = entity -> new CustomerResponseDto(entity.getId(), entity.getName());
}
