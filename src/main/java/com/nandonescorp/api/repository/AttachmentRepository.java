package com.nandonescorp.api.repository;

import com.nandonescorp.api.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> findByUserId(Long userId);
}
