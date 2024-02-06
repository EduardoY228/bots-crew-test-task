package org.botscrew.testtask.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class BaseResponseDTO {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
