package com.jadson.controllers;

import com.jadson.dto.requests.ErrorLogRequestDTO;
import com.jadson.dto.responses.ErrorLogResponseDTO;
import com.jadson.exceptions.BusinessRuleException;
import com.jadson.services.ErrorLogService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RestController
@RequestMapping("/ErrorLogController")
@AllArgsConstructor
public class ErrorLogController {
    private final ErrorLogService errorLogService;

    @PostMapping
    public ResponseEntity<ErrorLogResponseDTO> createErrorLog(ErrorLogRequestDTO errorLogRequestDTO)  {
        return new ResponseEntity<>(errorLogService.registerEntity(errorLogRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{idErrorLog}")
    public ResponseEntity<ErrorLogResponseDTO> updateErrorLog(ErrorLogRequestDTO errorLogRequestDTO, @PathVariable(name = "idErrorLog") Long id) throws BusinessRuleException {
        return new ResponseEntity<>(errorLogService.updateEntity(errorLogRequestDTO, id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{idErrorLog}")
    public ResponseEntity<Boolean> deleteErrorLog(@PathVariable(name = "idErrorLog") Long id) throws BusinessRuleException {
        return new ResponseEntity<>(errorLogService.deleteEntity(id), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/all/logs")
    public ResponseEntity<Boolean> deleteAllErrorLog() {
        return new ResponseEntity<>(errorLogService.deleteAllEntity(), HttpStatus.NO_CONTENT);
    }

    @GetMapping(name = "/search-id/", value = "/{idErrorLog}")
    public ResponseEntity<ErrorLogResponseDTO> findByIdErrorLog(@PathVariable(name = "idErrorLog") Long id) throws BusinessRuleException {
        return new ResponseEntity<>(errorLogService.findByIdEntity(id), HttpStatus.OK);
    }

    @GetMapping(name = "/search-all")
    public ResponseEntity<Page<ErrorLogResponseDTO>> findByIdErrorLog(Pageable pageable) throws BusinessRuleException {
        return new ResponseEntity<>(errorLogService.findAllEntity(pageable), HttpStatus.OK);
    }
}
