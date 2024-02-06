package org.botscrew.testtask.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "departments" ,indexes = {
        @Index(columnList = "name", name = "department_name_index")
})
public class Department extends BaseEntity {
    @Column(nullable = false, unique = true, name = "name")
    private String name;

    @ManyToMany(mappedBy = "departments", fetch = FetchType.EAGER)
    private Set<Lector> lectors = new HashSet<>();
}
