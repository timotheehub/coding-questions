package com.dbs.question2.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dbs.question2.entities.Customer;
import com.dbs.question2.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        customerRepository.deleteAll(); // Clear the database before each test
    }

    @Test
    public void testAddCustomer() throws Exception {
        mockMvc.perform(post("/api/customers")
                        .contentType("application/json")
                        .content("{\"name\":\"John Doe\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));

        assertThat(customerRepository.findAll()).hasSize(1);
        Customer savedCustomer = customerRepository.findAll().getFirst();
        assertThat(savedCustomer.getName()).isEqualTo("John Doe");
    }

    @Test
    public void testGetCustomers() throws Exception {
        Customer customer1 = new Customer();
        customer1.setName("Alice");
        customerRepository.save(customer1);

        Customer customer2 = new Customer();
        customer2.setName("Bob");
        customerRepository.save(customer2);

        mockMvc.perform(get("/api/customers")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Alice"))
                .andExpect(jsonPath("$.content[1].name").value("Bob"))
                .andExpect(jsonPath("$.content.length()").value(2));
    }
}
