package com.bfhl.api.controller;

import com.bfhl.api.dto.BfhlRequestDto;
import com.bfhl.api.dto.BfhlResponseDto;
import com.bfhl.api.service.BfhlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class BfhlController {

    private final BfhlService bfhlService;

    @Autowired
    public BfhlController(BfhlService bfhlService) {
        this.bfhlService = bfhlService;
    }

    @PostMapping("/bfhl")
    public ResponseEntity<BfhlResponseDto> processData(@RequestBody BfhlRequestDto request) {
        BfhlResponseDto response = bfhlService.processRequest(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bfhl")
    public ResponseEntity<Map<String, Object>> getOperationCode() {
        return ResponseEntity.ok(Collections.singletonMap("operation_code", 1));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> getHealth() {
        return ResponseEntity.ok(Collections.singletonMap("status", "UP"));
    }
}
