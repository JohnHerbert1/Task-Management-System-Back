package com.jadson.controllers;

import com.jadson.dto.requests.UserLogRequestDTO;
import com.jadson.dto.responses.UserLogResponseDTO;
import com.jadson.exceptions.BusinessRuleException;
import com.jadson.services.UserLogService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RestController
@RequestMapping("/UserLogController")
@AllArgsConstructor
public class UserLogController {
    private final UserLogService userLogService;

    @PostMapping
    public ResponseEntity<UserLogResponseDTO> createUserLog(UserLogRequestDTO userLogRequestDTO)  {
        return new ResponseEntity<>(userLogService.registerEntity(userLogRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{idUserLog}")
    public ResponseEntity<UserLogResponseDTO> updateUserLog(UserLogRequestDTO userLogRequestDTO, @PathVariable(name = "idUserLog") Long id) throws BusinessRuleException {
        return new ResponseEntity<>(userLogService.updateEntity(userLogRequestDTO, id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{idUserLog}")
    public ResponseEntity<Boolean> deleteUserLog(@PathVariable(name = "idUserLog") Long id) throws BusinessRuleException {
        return new ResponseEntity<>(userLogService.deleteEntity(id), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/all/logs")
    public ResponseEntity<Boolean> deleteAllUserLog() {
        return new ResponseEntity<>(userLogService.deleteAllEntity(), HttpStatus.NO_CONTENT);
    }

    @GetMapping(name = "/search-id/", value = "/{idUserLog}")
    public ResponseEntity<UserLogResponseDTO> findByIdUserLog(@PathVariable(name = "idUserLog") Long id) throws BusinessRuleException {
        return new ResponseEntity<>(userLogService.findByIdEntity(id), HttpStatus.OK);
    }

    @GetMapping(name = "/search-all")
    public ResponseEntity<Page<UserLogResponseDTO>> findByIdUserLog(Pageable pageable) throws BusinessRuleException {
        return new ResponseEntity<>(userLogService.findAllEntity(pageable), HttpStatus.OK);
    }
}
