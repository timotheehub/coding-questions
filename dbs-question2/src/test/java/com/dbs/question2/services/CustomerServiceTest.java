package com.dbs.question2.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.dbs.question2.entities.Customer;
import com.dbs.question2.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.List;


public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerService(customerRepository);
    }

    @Test
    public void testSaveCustomer() {
        Customer customer = new Customer();
        customer.setName("John Doe");

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer savedCustomer = customerService.addCustomer(customer);

        assertThat(savedCustomer.getName()).isEqualTo("John Doe");
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testGetCustomers() {
        Customer customer1 = new Customer();
        customer1.setName("Alice");

        Customer customer2 = new Customer();
        customer2.setName("Bob");

        Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));
        Page<Customer> customerPage = new PageImpl<>(List.of(customer1, customer2), pageable, 2);

        when(customerRepository.findAll(pageable)).thenReturn(customerPage);

        Page<Customer> resultPage = customerService.getCustomers(0, 10, "name");

        assertThat(resultPage.getContent()).hasSize(2);
        assertThat(resultPage.getContent().get(0).getName()).isEqualTo("Alice");
        assertThat(resultPage.getContent().get(1).getName()).isEqualTo("Bob");
        verify(customerRepository, times(1)).findAll(pageable);
    }
}
