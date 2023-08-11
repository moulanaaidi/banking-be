package com.banking.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.banking.constant.DocumentType;
import com.banking.dto.DocumentTypeDTO;
import com.banking.service.DocumentTypeService;

public class DocumentTypeServiceImplTest {

	@InjectMocks
	private DocumentTypeService documentTypeService = new DocumentTypeServiceImpl();

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetDocumentTypes() {
		List<DocumentTypeDTO> expectedDocumentTypes = Arrays.stream(DocumentType.values())
				.map(documentType -> new DocumentTypeDTO(documentType.getId(), documentType.getName()))
				.collect(Collectors.toList());

		List<DocumentTypeDTO> actualDocumentTypes = documentTypeService.getDocumentTypes();

		assertEquals(expectedDocumentTypes, actualDocumentTypes);
	}
}
