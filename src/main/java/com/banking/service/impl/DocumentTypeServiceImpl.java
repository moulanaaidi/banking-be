package com.banking.service.impl;

import com.banking.constant.DocumentType;
import com.banking.dto.DocumentTypeDTO;
import com.banking.service.DocumentTypeService;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentTypeServiceImpl implements DocumentTypeService {

    @Override
    public List<DocumentTypeDTO> getDocumentTypes() {
        return Arrays.stream(DocumentType.values())
                .map(documentType -> new DocumentTypeDTO(documentType.getId(), documentType.getName()))
                .collect(Collectors.toList());
    }
}
