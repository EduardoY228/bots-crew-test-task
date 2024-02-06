package org.botscrew.testtask.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class DepartmentResponseDTO extends BaseResponseDTO {
    private String name;
    private Set<LectorResponseDTO> lectors = new HashSet<>();
}
