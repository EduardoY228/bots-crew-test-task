package org.botscrew.testtask.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.botscrew.testtask.entity.Degree;

import java.util.HashSet;
import java.util.Set;

@Data
public class LectorRequestDTO {
    @NotNull
    @Size(min = 2, max = 50)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 50)
    private String lastName;

    private Degree degree;

    private Set<Long> departmentIds = new HashSet<>();
}
