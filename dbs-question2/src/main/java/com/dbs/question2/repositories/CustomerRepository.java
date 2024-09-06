package com.dbs.question2.repositories;

import com.dbs.question2.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Customer repository, for the database.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
