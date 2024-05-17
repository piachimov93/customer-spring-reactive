package com.piachimov.springreactive.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "t_customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity {
    @Id
    private Long id;
    @Column
    private String name;
}
