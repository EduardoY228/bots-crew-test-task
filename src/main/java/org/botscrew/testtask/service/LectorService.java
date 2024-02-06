package org.botscrew.testtask.service;

import lombok.AllArgsConstructor;
import org.botscrew.testtask.dto.LectorRequestDTO;
import org.botscrew.testtask.entity.Degree;
import org.botscrew.testtask.entity.Lector;
import org.botscrew.testtask.entity.Department;
import org.botscrew.testtask.exception.EntityNotFoundException;
import org.botscrew.testtask.exception.ValidationException;
import org.botscrew.testtask.repository.LectorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.text.MessageFormat.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LectorService {
    private final LectorRepository lectorRepository;
    private final DepartmentService departmentService;
    private final ModelMapper modelMapper;

    @Transactional
    public Lector createLector(LectorRequestDTO lectorRequestDTO) {
        Lector lector = modelMapper.map(lectorRequestDTO, Lector.class);

        Set<Department> departmentSet = lectorRequestDTO
                .getDepartmentIds()
                .stream()
                .map(departmentId -> departmentService
                        .getDepartmentById(departmentId)
                        .orElseThrow(() -> this.throwDepartmentNotFoundException(departmentId)))
                .collect(Collectors.toSet());

        lector.setDepartments(departmentSet);
        return lectorRepository.save(lector);
    }

    @Transactional
    public Lector updateLector(Long id, LectorRequestDTO lectorRequestDTO) {
        return lectorRepository
                .findById(id)
                .map(existingLector -> {
                    existingLector.setFirstName(lectorRequestDTO.getFirstName());
                    existingLector.setLastName(lectorRequestDTO.getLastName());
                    existingLector.setDegree(lectorRequestDTO.getDegree());

                    Set<Department> departments = lectorRequestDTO
                            .getDepartmentIds()
                            .stream()
                            .map(departmentId -> departmentService
                                    .getDepartmentById(departmentId)
                                    .orElseThrow(() -> this.throwDepartmentNotFoundException(departmentId))
                            )
                            .collect(Collectors.toSet());

                    existingLector.setDepartments(departments);

                    return this.lectorRepository.save(existingLector);
                })
                .orElseThrow(this::throwLectorNotFoundException);
    }

    public Optional<Lector> getLectorById(Long id) {
        return lectorRepository.findById(id);
    }

    public List<Lector> getAllLectors() {
        return lectorRepository.findAll();
    }

    public void deleteLector(Long id) {
        lectorRepository.deleteById(id);
    }

    public Lector promote(Long id) {
        return lectorRepository
                .findById(id)
                .map(lector -> switch (lector.getDegree()) {
                    case PROFESSOR -> throw new ValidationException("Lector already have professor degree");
                    default -> {
                        Degree prevDegree = lector.getDegree();
                        Degree newDegree = Degree.findByCode(prevDegree.getCode() + 1);
                        lector.setDegree(newDegree);

                        yield this.lectorRepository.save(lector);
                    }
                })
                .orElseThrow(this::throwLectorNotFoundException);
    }

    private EntityNotFoundException throwLectorNotFoundException() {
        throw new EntityNotFoundException(Lector.class);
    }

    public List<Lector> searchLectors(String query) {
        return this.lectorRepository.findByFirstNameOrLastNameContainingIgnoreCase(format("{0}{1}{0}", "%", query));
    }

    private ValidationException throwDepartmentNotFoundException(Long departmentId) {
        return ValidationException.throwIt(format("Department not found with id: {0}", departmentId));
    }
}

