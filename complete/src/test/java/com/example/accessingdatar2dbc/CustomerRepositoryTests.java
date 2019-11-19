package com.example.accessingdatar2dbc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataR2dbcTest
public class CustomerRepositoryTests {

    @Autowired
    private DatabaseClient databaseClient;

    @Autowired
    private CustomerRepository customers;

    @Test
    public void testFindByLastName() {
        Customer customer = new Customer("first", "last");
        databaseClient.insert().into(Customer.class).using(customer).then().as(StepVerifier::create).verifyComplete();

        Flux<Customer> findByLastName = customers.findByLastName(customer.getLastName());


        findByLastName.as(StepVerifier::create)
            .assertNext(actual -> {
                assertThat(actual.getFirstName()).isEqualTo("first");
                assertThat(actual.getLastName()).isEqualTo("last");
            })
            .verifyComplete();
    }
}
