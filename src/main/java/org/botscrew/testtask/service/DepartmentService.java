package org.botscrew.testtask.service;

import lombok.AllArgsConstructor;
import org.botscrew.testtask.dto.DepartmentRequestDTO;
import org.botscrew.testtask.dto.DepartmentResponseDTO;
import org.botscrew.testtask.entity.Degree;
import org.botscrew.testtask.entity.Department;
import org.botscrew.testtask.exception.EntityNotFoundException;
import org.botscrew.testtask.repository.DepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    public Department saveDepartment(DepartmentRequestDTO departmentRequestDTO) {
        Department department = modelMapper.map(departmentRequestDTO, Department.class);
        return departmentRepository.save(department);
    }

    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    public Department updateDepartment(Long id, DepartmentRequestDTO departmentRequestDTO) {
        return departmentRepository.findById(id).map(department -> {
            department.setName(departmentRequestDTO.getName());

            return departmentRepository.save(department);
        }).orElseThrow(() -> new EntityNotFoundException(Department.class));
    }

    public Map<Degree, Integer> getStatisticsByDepartmentId(Long id) {
        return this.getDepartmentById(id)
                .map(department -> this.departmentRepository
                        .countLectorsByDegreeInDepartment(department.getId())
                        .stream()
                        .collect(Collectors.toMap(
                                result -> Degree.findByCode(result[0]),
                                result -> result[1],
                                (existing, f) -> existing
                        )))
                .orElseThrow(() -> new EntityNotFoundException(Department.class));
    }
}