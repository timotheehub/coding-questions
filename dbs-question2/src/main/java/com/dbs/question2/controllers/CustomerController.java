package com.dbs.question2.controllers;

import com.dbs.question2.entities.Customer;
import com.dbs.question2.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * CustomerController is the controller for Customer API endpoints.
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /***
     * Returns the customers with pagination and an optional sorting.
     * The URL is GET /api/customers?page=0&size=10&sortBy=name.
     *
     * @param page page number. The first page is 0.
     * @param size size
     * @param sortBy the column name to use for sorting.
     * @return the list of customers for the requested page.
     */
    @GetMapping
    public ResponseEntity<?> getCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy) {
        // Validate the sort name value
        if (!"name".equals(sortBy) && !"id".equals(sortBy)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid sort parameter. Valid values are 'name' or 'id'.");
        }

        // Return the list of customers
        Page<Customer> customers = customerService.getCustomers(page, size, sortBy);
        return ResponseEntity.ok(customers);
    }

    /***
     * Creates a customer. The id is automatically assigned.
     * The URL is POST /api/customers.
     *
     * @return the created customer.
     */
    @PostMapping
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerService.addCustomer(customer);
    }
}
