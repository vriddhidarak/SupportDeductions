package com.kannayya.supportdeductions;

import com.kannayya.supportdeductions.controller.DcSupportDeductionsController;
import com.kannayya.supportdeductions.dto.DcSupportDeductionsResponseDTO;
import com.kannayya.supportdeductions.entity.DcSupportDeductions;
import com.kannayya.supportdeductions.exceptions.ResourceNotFoundException;
import com.kannayya.supportdeductions.service.DcSupportDeductionsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
public class DcSupportDeductionsControllerTest {

    @Mock
    private DcSupportDeductionsService service;

    @InjectMocks
    private DcSupportDeductionsController controller;

    public DcSupportDeductionsControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllDeductions() {
        DcSupportDeductionsResponseDTO dto = new DcSupportDeductionsResponseDTO(1L, "John Doe", 1000.00);
        when(service.findAll()).thenReturn(Collections.singletonList(dto));

        ResponseEntity<List<DcSupportDeductionsResponseDTO>> response = controller.getAllDeductions();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("John Doe", response.getBody().get(0).getName());
    }

    @Test
    public void testGetDeductionByIdSuccess() {
        DcSupportDeductions deduction = new DcSupportDeductions(1L, 1L, "John Doe", null, null, null, null, null, null, null, null, null);
        when(service.findById(1L)).thenReturn(Optional.of(deduction));

        ResponseEntity<Object> response = controller.getDeductionById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deduction, response.getBody());
    }

    @Test
    public void testGetDeductionByIdNotFound() {
        when(service.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            controller.getDeductionById(1L);
        });

        assertEquals("Deduction not found with ID: 1", thrown.getMessage());
    }

    @Test
    public void testCreateDeduction() {
        DcSupportDeductions deduction = new DcSupportDeductions(1L, 1L, "John Doe", null, null, null, null, null, null, null, null, null);
        when(service.save(any(DcSupportDeductions.class))).thenReturn(deduction);

        ResponseEntity<String> response = controller.createDeduction(deduction);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Deduction created successfully with ID: 1", response.getBody());
    }

    @Test
    public void testUpdateDeductionSuccess() {
        DcSupportDeductions existingDeduction = new DcSupportDeductions(1L, 1L, "John Doe", null, null, null, null, null, null, null, null, null);
        DcSupportDeductions updatedDeduction = new DcSupportDeductions(1L, 1L, "Jane Doe", null, null, null, null, null, null, null, null, null);
        when(service.findById(1L)).thenReturn(Optional.of(existingDeduction));
        when(service.save(any(DcSupportDeductions.class))).thenReturn(updatedDeduction);

        ResponseEntity<Object> response = controller.updateDeduction(1L, updatedDeduction);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedDeduction, response.getBody());
    }

    @Test
    public void testUpdateDeductionNotFound() {
        DcSupportDeductions updatedDeduction = new DcSupportDeductions(1L, 1L, "Jane Doe", null, null, null, null, null, null, null, null, null);
        when(service.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            controller.updateDeduction(1L, updatedDeduction);
        });

        assertEquals("Deduction not found with ID: 1", thrown.getMessage());
    }

    @Test
    public void testDeleteDeductionSuccess() {
        DcSupportDeductions deduction = new DcSupportDeductions(1L, 1L, "John Doe", null, null, null, null, null, null, null, null, null);
        when(service.findById(1L)).thenReturn(Optional.of(deduction));

        ResponseEntity<String> response = controller.deleteDeduction(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deduction deleted successfully with ID: 1", response.getBody());
    }

    @Test
    public void testDeleteDeductionNotFound() {
        when(service.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            controller.deleteDeduction(1L);
        });

        assertEquals("Deduction not found with ID: 1", thrown.getMessage());
    }
}

