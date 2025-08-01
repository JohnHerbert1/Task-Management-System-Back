package com.jadson.controllers;

import com.jadson.dto.requests.UserTypeRequestDTO;
import com.jadson.dto.responses.UserTypeResponseDTO;
import com.jadson.services.UserTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/UserType")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class UserTypeController {

    private final UserTypeService userTypeService;

    @GetMapping(value = "/{idUserType}")
    public ResponseEntity<UserTypeResponseDTO> findById(@PathVariable Long idUserType) {
        return ResponseEntity.ok().body(userTypeService.findById(idUserType));
    }

    @GetMapping
    public ResponseEntity<List<UserTypeResponseDTO>> findAll() { return ResponseEntity.ok(userTypeService.findAll());
    }

    @PostMapping
    public ResponseEntity<UserTypeResponseDTO> createUserType(@Valid @RequestBody UserTypeRequestDTO userTypeRequestDTO, UriComponentsBuilder uriComponentsBuilder) {

        UserTypeResponseDTO userTypeResponseDTO = userTypeService.createUserType(userTypeRequestDTO);

        URI uri = uriComponentsBuilder.path("/UserType/{id}").buildAndExpand(userTypeResponseDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(userTypeResponseDTO);
    }

    @PutMapping (value = "/{idUserType}")
    public ResponseEntity<UserTypeResponseDTO> updateUserType(@Valid @RequestBody UserTypeRequestDTO userTypeRequestDTO, @PathVariable Long idUserType) {
        return ResponseEntity.ok(userTypeService.updateUserType(userTypeRequestDTO, idUserType));
    }

    @DeleteMapping (value = "/{idUserType}")
    public ResponseEntity<String> deleteUserType(@PathVariable Long idUserType) {
        return ResponseEntity.ok(userTypeService.deleteUserType(idUserType));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllUserType() {
        return ResponseEntity.ok(userTypeService.deleteAllUserType());
    }

}
