package com.kannayya.supportdeductions;

import com.kannayya.supportdeductions.dto.DcSupportDeductionsGetAllDTO;
import com.kannayya.supportdeductions.dto.DcSupportDeductionsRequestDTO;
import com.kannayya.supportdeductions.dto.DcSupportDeductionsResponseDTO;
import com.kannayya.supportdeductions.entity.DcSupportDeductions;
import com.kannayya.supportdeductions.repository.DcSupportDeductionsRepository;
import com.kannayya.supportdeductions.service.DcSupportDeductionsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DcSupportDeductionsServiceImplTest {

    @Mock
    private DcSupportDeductionsRepository repository;

    @InjectMocks
    private DcSupportDeductionsServiceImpl service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
   void testFindAll() {
        DcSupportDeductions entity1 = new DcSupportDeductions();
        DcSupportDeductions entity2 = new DcSupportDeductions();
        DcSupportDeductionsGetAllDTO deduction1 = new DcSupportDeductionsGetAllDTO(1L, 1L, "John Doe");
        DcSupportDeductionsGetAllDTO deduction2 = new DcSupportDeductionsGetAllDTO(2L, 2L, "Jane Doe");

        when(repository.findAll()).thenReturn(Arrays.asList(entity1, entity2));
        when(DcSupportDeductionsGetAllDTO.fromEntity(entity1, LocalDateTime.now(), LocalDateTime.now())).thenReturn(deduction1);
        when(DcSupportDeductionsGetAllDTO.fromEntity(entity2, LocalDateTime.now(), LocalDateTime.now())).thenReturn(deduction2);

        List<DcSupportDeductionsGetAllDTO> result = service.findAll();

        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Doe", result.get(1).getName());
    }

    @Test
    void testFindByIdSuccess() {
        DcSupportDeductions entity = new DcSupportDeductions();
        DcSupportDeductionsResponseDTO deduction = new DcSupportDeductionsResponseDTO(LocalDateTime.now(), LocalDateTime.now(), 1L, "John Doe", BigDecimal.ZERO);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(DcSupportDeductionsResponseDTO.fromEntity(entity, LocalDateTime.now(), LocalDateTime.now())).thenReturn(deduction);

        Optional<DcSupportDeductionsResponseDTO> result = service.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
    }

    @Test
    void testFindByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Optional<DcSupportDeductionsResponseDTO> result = service.findById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void testSave() {
        DcSupportDeductionsRequestDTO request = new DcSupportDeductionsRequestDTO();
        DcSupportDeductions entity = new DcSupportDeductions();
        DcSupportDeductions savedEntity = new DcSupportDeductions();
        DcSupportDeductionsResponseDTO response = new DcSupportDeductionsResponseDTO(LocalDateTime.now(), LocalDateTime.now(), 1L, "John Doe", BigDecimal.ZERO);

        when(request.toEntity()).thenReturn(entity);
        when(repository.save(entity)).thenReturn(savedEntity);
        when(DcSupportDeductionsResponseDTO.fromEntity(savedEntity, LocalDateTime.now(), LocalDateTime.now())).thenReturn(response);

        DcSupportDeductionsResponseDTO result = service.save(request);

        assertEquals("John Doe", result.getName());
        verify(repository, times(1)).save(entity);
    }

    @Test
   void testDeleteById() {
        Long id = 1L;

        service.deleteById(id);

        verify(repository, times(1)).deleteById(id);
    }
}