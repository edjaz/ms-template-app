package fr.edjaz.document.service.mapper;

import fr.edjaz.document.domain.*;
import fr.edjaz.document.service.dto.DocumentEntityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DocumentEntity and its DTO DocumentEntityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DocumentEntityMapper extends EntityMapper<DocumentEntityDTO, DocumentEntity> {



    default DocumentEntity fromId(String id) {
        if (id == null) {
            return null;
        }
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setId(id);
        return documentEntity;
    }
}
