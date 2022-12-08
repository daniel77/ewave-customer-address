package com.ewave.volvo;

import com.ewave.volvo.model.Address;
import com.ewave.volvo.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class VolvoApplicationTests {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	@Autowired
	private MockMvc mvc;

	@Test
	public void givenCustomer_saveNewCustomer_thenStatus200()
			throws Exception {

		mvc.perform(MockMvcRequestBuilders
						.post("/customers")
						.content(OBJECT_MAPPER.writeValueAsString(Customer.builder().age(1).name("new")
								.address(Collections.singletonList(Address.builder().number(1).zipCode("89834-234").build())).build()))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.documentId").exists());
	}

	@Test
	public void givenCustomer_withInvalidZipCode_thenBadRequest()
			throws Exception {
		assertTrue( mvc.perform(MockMvcRequestBuilders
						.post("/customers")
						.content(OBJECT_MAPPER.writeValueAsString(Customer.builder().age(1).name("new")
								.address(Collections.singletonList(Address.builder().number(1).zipCode("89834").build())).build()))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError()).andReturn().getResponse().getContentAsString().contains("INVALID ZIP CODE BRAZIL"));
	}

	@Test
	public void givenCustomer_whenUpdate_thenStatus200() throws Exception {

		Customer customer = OBJECT_MAPPER.readValue(mvc.perform(MockMvcRequestBuilders
						.post("/customers")
						.content(OBJECT_MAPPER.writeValueAsString(Customer.builder().age(1).name("new")
								.address(Collections.singletonList(Address.builder().number(1).zipCode("89834-234").build())).build()))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn().getResponse().getContentAsString(), Customer.class);

		customer.setName("New Name");

		Customer customerUpdated = OBJECT_MAPPER.readValue(mvc.perform(MockMvcRequestBuilders
						.put("/customers/" + customer.getDocumentId())
						.content(OBJECT_MAPPER.writeValueAsString(customer))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getContentAsString(), Customer.class);

		assertEquals("New Name", customerUpdated.getName());
	}

}
