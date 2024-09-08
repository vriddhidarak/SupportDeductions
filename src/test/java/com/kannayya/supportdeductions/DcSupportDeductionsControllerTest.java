package com.kannayya.supportdeductions;

import com.kannayya.supportdeductions.controller.DcSupportDeductionsController;
import com.kannayya.supportdeductions.dto.DcSupportDeductionsGetAllDTO;
import com.kannayya.supportdeductions.dto.DcSupportDeductionsRequestDTO;
import com.kannayya.supportdeductions.dto.DcSupportDeductionsResponseDTO;
import com.kannayya.supportdeductions.exceptions.ResourceNotFoundException;
import com.kannayya.supportdeductions.service.DcSupportDeductionsServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
 class DcSupportDeductionsControllerTest {

    @Mock
    private DcSupportDeductionsServiceImpl service;

    @InjectMocks
    private DcSupportDeductionsController controller;

    public DcSupportDeductionsControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDeductions() {
        LocalDateTime now = LocalDateTime.now();
        DcSupportDeductionsGetAllDTO dto = new DcSupportDeductionsGetAllDTO(now, now, 1L, "John Doe", BigDecimal.valueOf(1000.00));
        when(service.findAll()).thenReturn(Collections.singletonList(dto));

        ResponseEntity<List<DcSupportDeductionsGetAllDTO>> response = controller.getAllDeductions();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("John Doe", response.getBody().get(0).getName());
    }

    @Test
    void testGetDeductionByIdSuccess() {
        LocalDateTime now = LocalDateTime.now();
        DcSupportDeductionsResponseDTO dto = new DcSupportDeductionsResponseDTO(now, now, 1L, "John Doe", BigDecimal.valueOf(1000.00));
        when(service.findById(1L)).thenReturn(Optional.of(dto));

        ResponseEntity<DcSupportDeductionsResponseDTO> response = controller.getDeductionById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void testGetDeductionByIdNotFound() {
        when(service.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            controller.getDeductionById(1L);
        });

        assertEquals("Deduction not found with ID: 1", thrown.getMessage());
    }

    @Test
    void testCreateDeduction() {
        LocalDateTime now = LocalDateTime.now();
        DcSupportDeductionsRequestDTO requestDTO = new DcSupportDeductionsRequestDTO("John Doe", BigDecimal.valueOf(1000.00));
        DcSupportDeductionsResponseDTO responseDTO = new DcSupportDeductionsResponseDTO(now, now, 1L, "John Doe", BigDecimal.valueOf(1000.00));
        when(service.save(any(DcSupportDeductionsRequestDTO.class))).thenReturn(responseDTO);

        ResponseEntity<DcSupportDeductionsResponseDTO> response = controller.createDeduction(requestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void testUpdateDeductionSuccess() {
        LocalDateTime now = LocalDateTime.now();
        DcSupportDeductionsRequestDTO requestDTO = new DcSupportDeductionsRequestDTO("Jane Doe", BigDecimal.valueOf(2000.00));
        DcSupportDeductionsResponseDTO responseDTO = new DcSupportDeductionsResponseDTO(now, now, 1L, "Jane Doe", BigDecimal.valueOf(2000.00));
        when(service.update(eq(1L), any(DcSupportDeductionsRequestDTO.class))).thenReturn(responseDTO);

        ResponseEntity<DcSupportDeductionsResponseDTO> response = controller.updateDeduction(1L, requestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
   void testUpdateDeductionNotFound() {
        DcSupportDeductionsRequestDTO requestDTO = new DcSupportDeductionsRequestDTO("Jane Doe", BigDecimal.valueOf(2000.00));
        when(service.update(eq(1L), any(DcSupportDeductionsRequestDTO.class))).thenThrow(new ResourceNotFoundException("Deduction not found with ID: 1"));

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            controller.updateDeduction(1L, requestDTO);
        });

        assertEquals("Deduction not found with ID: 1", thrown.getMessage());
    }

    @Test
    void testDeleteDeductionSuccess() {
        doNothing().when(service).deleteById(1L);

        ResponseEntity<Void> response = controller.deleteDeduction(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deduction deleted successfully with ID: 1", response.getBody());
    }

    @Test
    void testDeleteDeductionNotFound() {
        doThrow(new ResourceNotFoundException("Deduction not found with ID: 1")).when(service).deleteById(1L);

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            controller.deleteDeduction(1L);
        });

        assertEquals("Deduction not found with ID: 1", thrown.getMessage());
    }
}