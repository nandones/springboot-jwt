package com.nandonescorp.api.mapper;

import com.nandonescorp.api.dto.AttachmentResponseDTO;
import com.nandonescorp.api.model.Attachment;

public class AttachmentMapper {
    public static AttachmentResponseDTO toDTO(Attachment attachment) {
        return new AttachmentResponseDTO(
                attachment.getId(),
                attachment.getFilename(),
                attachment.getContentType()
        );
    }
}
