package com.dbs.question2.services;

import com.dbs.question2.entities.Customer;
import com.dbs.question2.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

/**
 * CustomerService handles the operations related to storing and retrieving customers.
 */
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Page<Customer> getCustomers(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return customerRepository.findAll(pageable);
    }

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}