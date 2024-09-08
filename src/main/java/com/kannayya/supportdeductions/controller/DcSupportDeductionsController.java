package com.kannayya.supportdeductions.controller;

import com.kannayya.supportdeductions.dto.DcSupportDeductionsGetAllDTO;
import com.kannayya.supportdeductions.dto.DcSupportDeductionsRequestDTO;
import com.kannayya.supportdeductions.dto.DcSupportDeductionsResponseDTO;
import com.kannayya.supportdeductions.entity.DcSupportDeductions;
import com.kannayya.supportdeductions.service.DcSupportDeductionsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/deductions")
public class DcSupportDeductionsController {

    @Autowired
    private DcSupportDeductionsService service;

    @GetMapping
    public List<DcSupportDeductionsGetAllDTO> getAllDeductions() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DcSupportDeductionsResponseDTO> getDeductionById(@PathVariable Long id) {
        Optional<DcSupportDeductionsResponseDTO> responseDTO = service.findById(id);
        return responseDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DcSupportDeductionsResponseDTO> createDeduction(@RequestBody DcSupportDeductionsRequestDTO requestDTO) {
        DcSupportDeductionsResponseDTO responseDTO = service.save(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }


    @PutMapping("/{id}")
    public ResponseEntity<DcSupportDeductionsResponseDTO> updateDeduction(@PathVariable Long id, @RequestBody DcSupportDeductionsRequestDTO requestDTO) {
        DcSupportDeductionsResponseDTO responseDTO = service.update(id, requestDTO);
        if (responseDTO != null) {
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeduction(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}