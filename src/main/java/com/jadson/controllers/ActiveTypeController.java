package com.jadson.controllers;

import com.jadson.dto.requests.ActiveTypeRequestDTO;
import com.jadson.dto.responses.ActiveTypeResponseDTO;
import com.jadson.services.ActiveTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/ActiveType")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ActiveTypeController {

    private final ActiveTypeService activeTypeService;

    @GetMapping(value = "/{idActiveType}")
    public ResponseEntity<ActiveTypeResponseDTO> findById(@PathVariable Long idActiveType) {
        return ResponseEntity.ok().body(activeTypeService.findById(idActiveType));
    }

    @GetMapping
    public ResponseEntity<List<ActiveTypeResponseDTO>> findAll() { return ResponseEntity.ok(activeTypeService.findAll());
    }

    @PostMapping
    public ResponseEntity<ActiveTypeResponseDTO> createActiveType(@Valid @RequestBody ActiveTypeRequestDTO activeTypeRequestDTO, UriComponentsBuilder uriComponentsBuilder) {

        ActiveTypeResponseDTO activeTypeResponseDTO = activeTypeService.createActiveType(activeTypeRequestDTO);

        URI uri = uriComponentsBuilder.path("/ActiveType/{id}").buildAndExpand(activeTypeResponseDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(activeTypeResponseDTO);
    }

    @PutMapping (value = "/{idActiveType}")
    public ResponseEntity<ActiveTypeResponseDTO> updateActiveType(@Valid @RequestBody ActiveTypeRequestDTO activeTypeRequestDTO, @PathVariable Long idActiveType) {
        return ResponseEntity.ok(activeTypeService.updateActiveType(activeTypeRequestDTO, idActiveType));
    }

    @DeleteMapping (value = "/{idActiveType}")
    public ResponseEntity<String> deleteActiveType(@PathVariable Long idActiveType) {
        return ResponseEntity.ok(activeTypeService.deleteActiveType(idActiveType));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllActiveType() {
        return ResponseEntity.ok(activeTypeService.deleteAllActiveType());
    }

}
