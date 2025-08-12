package com.nandonescorp.api.service;

import com.nandonescorp.api.dto.AttachmentResponseDTO;
import com.nandonescorp.api.mapper.AttachmentMapper;
import com.nandonescorp.api.model.Attachment;
import com.nandonescorp.api.model.User;
import com.nandonescorp.api.repository.AttachmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;

    public AttachmentService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public AttachmentResponseDTO save(MultipartFile file, User owner) throws IOException {
        Attachment att = Attachment.builder()
                .filename(file.getOriginalFilename())
                .contentType(file.getContentType())
                .data(file.getBytes())
                .user(owner)
                .build();
        Attachment saved = attachmentRepository.save(att);
        return AttachmentMapper.toDTO(saved);
    }

    public List<AttachmentResponseDTO> listByUser(User owner) {
        return attachmentRepository.findByUserId(owner.getId())
                .stream().map(AttachmentMapper::toDTO).toList();
    }

    public AttachmentResponseDTO getByIdForUser(Long id, User owner) {
        Attachment att = attachmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attachment não encontrado"));
        if (!att.getUser().getId().equals(owner.getId())) {
            throw new RuntimeException("Acesso negado");
        }
        return AttachmentMapper.toDTO(att);
    }

    public void delete(Long id, User owner) {
        Attachment att = attachmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attachment não encontrado"));
        if (!att.getUser().getId().equals(owner.getId())) {
            throw new RuntimeException("Acesso negado");
        }
        attachmentRepository.delete(att);
    }
}
