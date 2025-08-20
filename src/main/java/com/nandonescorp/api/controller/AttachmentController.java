package com.nandonescorp.api.controller;

import com.nandonescorp.api.dto.AttachmentResponseDTO;
import com.nandonescorp.api.model.User;
import com.nandonescorp.api.security.UserDetailsImpl;
import com.nandonescorp.api.service.AttachmentService;
import com.nandonescorp.api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/attachments")
public class AttachmentController {
    private final AttachmentService attachmentService;
    private final UserService userService;

    public AttachmentController(AttachmentService attachmentService, UserService userService) {
        this.attachmentService = attachmentService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<AttachmentResponseDTO> upload(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {

        User owner = userService.findByUsername(userDetails.getUsername());
        return ResponseEntity.ok(attachmentService.save(file, owner));
    }

    @GetMapping
    public ResponseEntity<List<AttachmentResponseDTO>> list(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User owner = userService.findByUsername(userDetails.getUsername());
        List<AttachmentResponseDTO> attachments = attachmentService.listByUser(owner);
        return ResponseEntity.ok(attachments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttachmentResponseDTO> get(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User owner = userService.findByUsername(userDetails.getUsername());
        AttachmentResponseDTO attachment = attachmentService.getByIdForUser(id, owner);
        return ResponseEntity.ok(attachment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User owner = userService.findByUsername(userDetails.getUsername());
        attachmentService.delete(id, owner);
        return ResponseEntity.noContent().build();
    }
}
