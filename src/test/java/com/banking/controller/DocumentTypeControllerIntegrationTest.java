package com.banking.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.banking.dto.DocumentTypeDTO;
import com.banking.service.DocumentTypeService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DocumentTypeControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private DocumentTypeService documentTypeService;

	@Test
	public void testGetDocumentTypes() throws Exception {
		// Arrange
		List<DocumentTypeDTO> expectedTypes = documentTypeService.getDocumentTypes();

		// Act
		ResultActions resultActions = mockMvc.perform(
				get("/api/document-types/getDocumentTypes")
				.header("X-API-KEY", "themostsecretkeyeverexisted")
				);

		// Assert
		resultActions.andExpect(status().isOk()).andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.length()").value(expectedTypes.size()));

		for (int i = 0; i < expectedTypes.size(); i++) {
			DocumentTypeDTO expectedType = expectedTypes.get(i);
			resultActions.andExpect(jsonPath("$[" + i + "].id").value(expectedType.getId()))
					.andExpect(jsonPath("$[" + i + "].name").value(expectedType.getName()));
		}
	}
}
