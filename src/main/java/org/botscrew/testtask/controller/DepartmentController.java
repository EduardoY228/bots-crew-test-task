package org.botscrew.testtask.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.botscrew.testtask.dto.DepartmentRequestDTO;
import org.botscrew.testtask.dto.DepartmentResponseDTO;
import org.botscrew.testtask.entity.Department;
import org.botscrew.testtask.service.DepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<?> createDepartment(@Valid @RequestBody DepartmentRequestDTO departmentRequestDTO) {
        DepartmentResponseDTO departmentResponseDTO = toDepartmentResponseDTO(departmentService.saveDepartment(departmentRequestDTO));
        return ResponseEntity.ok(departmentResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id)
                .map(this::toDepartmentResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/statistics")
    public ResponseEntity<?> getDepartmentsStatisticsById(@PathVariable Long id) {
        return ResponseEntity.ok(this.departmentService.getStatisticsByDepartmentId(id));
    }

    @GetMapping
    public ResponseEntity<?> getAllDepartments() {
        return ResponseEntity.ok(
                departmentService.getAllDepartments().stream().map(this::toDepartmentResponseDTO)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentRequestDTO departmentRequestDTO) {
        DepartmentResponseDTO departmentResponseDTO = toDepartmentResponseDTO(departmentService.updateDepartment(id, departmentRequestDTO));
        return ResponseEntity.ok(departmentResponseDTO);
    }

    private DepartmentResponseDTO toDepartmentResponseDTO(Department department) {
        return modelMapper.map(department, DepartmentResponseDTO.class);
    }
}
