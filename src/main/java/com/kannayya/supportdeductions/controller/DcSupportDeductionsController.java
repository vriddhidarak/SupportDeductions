package com.kannayya.supportdeductions.controller;

import com.kannayya.supportdeductions.dto.DcSupportDeductionsResponseDTO;
import com.kannayya.supportdeductions.entity.DcSupportDeductions;
import com.kannayya.supportdeductions.exceptions.ResourceNotFoundException;
import com.kannayya.supportdeductions.service.DcSupportDeductionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<DcSupportDeductionsResponseDTO>> getAllDeductions() {
        List<DcSupportDeductionsResponseDTO> deductions = service.findAll();
        return new ResponseEntity<>(deductions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDeductionById(@PathVariable Long id) {
        Optional<DcSupportDeductions> deductionOpt = service.findById(id);
        if (deductionOpt.isPresent()) {
            return new ResponseEntity<>(deductionOpt.get(), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Deduction not found with ID: " + id);
        }
    }

    @PostMapping
    public ResponseEntity<String> createDeduction(@RequestBody DcSupportDeductions deduction) {
        DcSupportDeductions savedDeduction = service.save(deduction);
        return new ResponseEntity<>("Deduction created successfully with ID: " + savedDeduction.getSprtSeqNum(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDeduction(@PathVariable Long id, @RequestBody DcSupportDeductions deduction) {
        Optional<DcSupportDeductions> existingDeductionOpt = service.findById(id);

        if (existingDeductionOpt.isPresent()) {
            DcSupportDeductions existingDeduction = existingDeductionOpt.get();
            deduction.setSprtSeqNum(existingDeduction.getSprtSeqNum()); // Ensure ID is not changed
            DcSupportDeductions updatedDeduction = service.save(deduction);
            return new ResponseEntity<>(updatedDeduction, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Deduction not found with ID: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDeduction(@PathVariable Long id) {
        Optional<DcSupportDeductions> existingDeductionOpt = service.findById(id);

        if (existingDeductionOpt.isPresent()) {
            service.deleteById(id);
            return new ResponseEntity<>("Deduction deleted successfully with ID: " + id, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Deduction not found with ID: " + id);
        }
    }
}