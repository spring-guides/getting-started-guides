package hello;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // tag::methods[]
    long countByLastNameStartsWithIgnoreCase(String lastName);

    Page<Customer> findByLastNameStartsWithIgnoreCase(String lastName, Pageable pageable);
    // end::methods[]

}
