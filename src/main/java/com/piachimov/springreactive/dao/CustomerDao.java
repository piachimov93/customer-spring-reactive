package com.piachimov.springreactive.dao;

import com.piachimov.springreactive.dto.Customer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class CustomerDao {

    public List<Customer> getCustomers() {
        return IntStream.rangeClosed(1, 50)
                .peek(CustomerDao::sleep)
                .mapToObj(c -> new Customer(c, "Customer " + c))
                .peek(System.out::println)
                .collect(Collectors.toList());
    }

    public Flux<Customer> getCustomersStream() {
        return Flux.range(1,10)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(i -> System.out.println("Stream Processing count : " + i))
                .map(i -> new Customer(i, "Customer " + i));
    }


    public Flux<Customer> getCustomerList() {
        return Flux.range(1,50)
                .doOnNext(i -> System.out.println("Stream Processing count : " + i))
                .map(i -> new Customer(i, "Customer " + i));
    }

    private static void sleep(int i) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
