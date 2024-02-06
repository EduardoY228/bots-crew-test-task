package org.botscrew.testtask.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.botscrew.testtask.entity.Degree;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class LectorResponseDTO extends BaseResponseDTO {
    private String firstName;
    private String lastName;
    private Degree degree;
    private String externalId;
}
