package com.banking.controller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.banking.dto.request.CustomerCreateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@Rollback
public class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateCustomer() throws Exception {
        CustomerCreateRequest request = new CustomerCreateRequest();
        request.setDocumentTypeId("1");
        request.setName("Moulana Aidi Jamre");
        request.setDocumentNumber("12345678901234567890");
        // Set other fields
        
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/customer/create")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-API-KEY", "themostsecretkeyeverexisted")
				.content(
						new ObjectMapper().writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testInquireCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/customer/inquire/{customerId}", 100009)
                .header("X-API-KEY", "themostsecretkeyeverexisted"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testInquireNonExistingCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/customer/inquire/{customerId}", 999L)
                .header("X-API-KEY", "themostsecretkeyeverexisted"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
