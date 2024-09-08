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
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DcSupportDeductionsServiceImplTest {

    @Mock
    private DcSupportDeductionsRepository repository;

    @InjectMocks
    private DcSupportDeductionsServiceImpl service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        List<DcSupportDeductions> deductionsList = Arrays.asList(
                new DcSupportDeductions(1L, 101L, "John Doe", new Date(), new Date(), new Date(), new Date(), "EXP001", new BigDecimal("500"), "Verified", new Date(), new Date(), new Date()),
                new DcSupportDeductions(2L, 102L, "Jane Doe", new Date(), new Date(), new Date(), new Date(), "EXP002", new BigDecimal("600"), "Verified", new Date(), new Date(), new Date())
        );

        when(repository.findAll()).thenReturn(deductionsList);

        List<DcSupportDeductionsGetAllDTO> result = service.findAll();

        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals(new BigDecimal("500"), result.get(0).getMonthlyActualAmt());
        assertEquals("Jane Doe", result.get(1).getName());

        verify(repository, times(1)).findAll();
    }

    @Test
      void testFindById_Found() {
        DcSupportDeductions entity = new DcSupportDeductions(1L, 101L, "John Doe", new Date(), new Date(), new Date(), new Date(), "EXP001", new BigDecimal("500"), "Verified", new Date(), new Date(), new Date());

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        Optional<DcSupportDeductionsResponseDTO> result = service.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
        assertEquals(new BigDecimal("500"), result.get().getMonthlyActualAmt());

        verify(repository, times(1)).findById(1L);
    }

    @Test
      void testFindById_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Optional<DcSupportDeductionsResponseDTO> result = service.findById(1L);

        assertFalse(result.isPresent());
        verify(repository, times(1)).findById(1L);
    }

    @Test
      void testSave() {
        DcSupportDeductionsRequestDTO requestDTO = new DcSupportDeductionsRequestDTO(
                101L, "John Doe", new Date(), new Date(), new Date(), new Date(), "EXP001",
                new BigDecimal("500"), "Verified", new Date(), new Date(), new Date());

        DcSupportDeductions savedEntity = new DcSupportDeductions(1L, 101L, "John Doe", new Date(), new Date(), new Date(), new Date(), "EXP001", new BigDecimal("500"), "Verified", new Date(), new Date(), new Date());

        when(repository.save(any(DcSupportDeductions.class))).thenReturn(savedEntity);

        DcSupportDeductionsResponseDTO result = service.save(requestDTO);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals(new BigDecimal("500"), result.getMonthlyActualAmt());

        verify(repository, times(1)).save(any(DcSupportDeductions.class));
    }

    @Test
      void testUpdate_Success() {
        DcSupportDeductionsRequestDTO requestDTO = new DcSupportDeductionsRequestDTO(
                101L, "John Doe", new Date(), new Date(), new Date(), new Date(), "EXP001",
                new BigDecimal("500"), "Verified", new Date(), new Date(), new Date());
        DcSupportDeductions entity = new DcSupportDeductions(1L, 101L, "John Doe", new Date(), new Date(), new Date(), new Date(), "EXP001", new BigDecimal("500"), "Verified", new Date(), new Date(), new Date());

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(any(DcSupportDeductions.class))).thenReturn(entity);

        DcSupportDeductionsResponseDTO result = service.update(1L, requestDTO);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals(new BigDecimal("500"), result.getMonthlyActualAmt());

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(DcSupportDeductions.class));
    }

    @Test
      void testUpdate_NotFound() {
        DcSupportDeductionsRequestDTO requestDTO = new DcSupportDeductionsRequestDTO(
                101L, "John Doe", new Date(), new Date(), new Date(), new Date(), "EXP001",
                new BigDecimal("500"), "Verified", new Date(), new Date(), new Date());

        when(repository.findById(1L)).thenReturn(Optional.empty());

        DcSupportDeductionsResponseDTO result = service.update(1L, requestDTO);

        assertNull(result);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(0)).save(any(DcSupportDeductions.class));
    }

    @Test
      void testDeleteById() {
        doNothing().when(repository).deleteById(1L);

        service.deleteById(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}
