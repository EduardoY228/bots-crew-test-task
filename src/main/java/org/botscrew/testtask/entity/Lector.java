package org.botscrew.testtask.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "lectors")
@Table(name = "lectors", indexes = {
        @Index(columnList = "first_name, last_name, external_id", unique = true)
})
public class Lector extends BaseEntity {
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Enumerated(EnumType.ORDINAL)
    private Degree degree;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "lectors_departments",
            joinColumns = @JoinColumn(name = "lector_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    private Set<Department> departments;

    @Column(name = "external_id", updatable = false, nullable = false, unique = true)
    private String externalId = UUID.randomUUID().toString();
}
