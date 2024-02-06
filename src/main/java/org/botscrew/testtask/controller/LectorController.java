package org.botscrew.testtask.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.botscrew.testtask.dto.LectorRequestDTO;
import org.botscrew.testtask.dto.LectorResponseDTO;
import org.botscrew.testtask.entity.Lector;
import org.botscrew.testtask.exception.EntityNotFoundException;
import org.botscrew.testtask.service.LectorService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/lectors")
public class LectorController {
    private final LectorService lectorService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<?> createLector(@Valid @RequestBody LectorRequestDTO lectorRequestDTO) {
        Lector lector = lectorService.createLector(lectorRequestDTO);
        LectorResponseDTO lectorResponseDTO = mapToResponseDTO(lector);
        return ResponseEntity.ok(lectorResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLectorById(@PathVariable Long id) {
        return ResponseEntity.ok(lectorService.getLectorById(id)
                .map(this::mapToResponseDTO)
                .orElseThrow(this::throwLectorNotFoundException));
    }

    @GetMapping
    public ResponseEntity<?> getAllLectors() {
        return ResponseEntity.ok(this.mapListToResponseDTO(lectorService.getAllLectors()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLector(@PathVariable Long id,
                                          @Valid @RequestBody LectorRequestDTO lectorRequestDTO) {
        Lector updatedLector = lectorService.updateLector(id, lectorRequestDTO);
        LectorResponseDTO lectorResponseDTO = mapToResponseDTO(updatedLector);
        return ResponseEntity.ok(lectorResponseDTO);
    }

    @PutMapping("/{id}/promote")
    public ResponseEntity<?> promote(@PathVariable Long id) {
        return ResponseEntity.ok(this.lectorService.promote(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLector(@PathVariable Long id) {
        lectorService.deleteLector(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchLectors(@RequestParam String query) {
        return ResponseEntity.ok(this.mapListToResponseDTO(lectorService.searchLectors(query)));
    }

    private LectorResponseDTO mapToResponseDTO(Lector lector) {
        return this.modelMapper.map(lector, LectorResponseDTO.class);
    }

    private List<LectorResponseDTO> mapListToResponseDTO(List<Lector> lectorList) {
        return lectorList.stream().map(this::mapToResponseDTO).toList();
    }

    private EntityNotFoundException throwLectorNotFoundException() {
        throw new EntityNotFoundException(Lector.class);
    }
}

