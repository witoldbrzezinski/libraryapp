package pl.witoldbrzezinski.libraryapp.customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {

    boolean existsByPersonalNumber(String personalNumber);

}
