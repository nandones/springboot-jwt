package com.nandonescorp.api.dto;

public record AttachmentRequestDTO(String filename, String contentType, byte[] data) {}
