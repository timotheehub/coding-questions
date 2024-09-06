package com.dbs.question2.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dbs.question2.entities.Customer;
import com.dbs.question2.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    public void testAddCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setName("John Doe");

        when(customerService.addCustomer(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post("/api/customers")
                        .contentType("application/json")
                        .content("{\"name\":\"John Doe\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(customerService, times(1)).addCustomer(any(Customer.class));
    }

    @Test
    public void testGetCustomers() throws Exception {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("John Doe");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Jane Doe");

        List<Customer> customers = List.of(customer1, customer2);
        Page<Customer> customerPage = new PageImpl<>(customers, PageRequest.of(0, 10, Sort.by("name")), customers.size());

        when(customerService.getCustomers(0, 10, "name")).thenReturn(customerPage);

        mockMvc.perform(get("/api/customers?page=0&size=10&sortBy=name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("John Doe"))
                .andExpect(jsonPath("$.content[1].id").value(2))
                .andExpect(jsonPath("$.content[1].name").value("Jane Doe"))
                .andExpect(jsonPath("$.content.length()").value(2));

        verify(customerService, times(1)).getCustomers(0, 10, "name");
    }

    @Test
    public void testInvalidSortByParameter() throws Exception {
        mockMvc.perform(get("/api/customers?page=0&size=10&sortBy=invalid"))
                .andExpect(status().isBadRequest());
    }
}