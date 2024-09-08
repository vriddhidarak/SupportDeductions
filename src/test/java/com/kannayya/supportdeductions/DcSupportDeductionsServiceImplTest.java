package com.kannayya.supportdeductions;

import com.kannayya.supportdeductions.dto.DcSupportDeductionsGetAllDTO;
import com.kannayya.supportdeductions.entity.DcSupportDeductions;
import com.kannayya.supportdeductions.repository.DcSupportDeductionsRepository;
import com.kannayya.supportdeductions.service.DcSupportDeductionsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DcSupportDeductionsServiceImplTest {

    @Mock
    private DcSupportDeductionsRepository repository;

    @InjectMocks
    private DcSupportDeductionsServiceImpl service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        DcSupportDeductions deduction1 = new DcSupportDeductions(1L, 1L, "John Doe");
        DcSupportDeductions deduction2 = new DcSupportDeductions(2L, 2L, "Jane Doe");

        when(repository.findAll()).thenReturn(Arrays.asList(deduction1, deduction2));

        List<DcSupportDeductionsGetAllDTO> result = service.findAll();

        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Doe", result.get(1).getName());
    }

    @Test
    public void testFindByIdSuccess() {
        DcSupportDeductions deduction = new DcSupportDeductions(1L, 1L, "John Doe");

        when(repository.findById(1L)).thenReturn(Optional.of(deduction));

        Optional<DcSupportDeductions> result = service.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
    }

    @Test
    public void testFindByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Optional<DcSupportDeductions> result = service.findById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    public void testSave() {
        DcSupportDeductions deduction = new DcSupportDeductions(1L, 1L, "John Doe");

        when(repository.save(deduction)).thenReturn(deduction);

        DcSupportDeductions result = service.save(deduction);

        assertEquals("John Doe", result.getName());
        verify(repository, times(1)).save(deduction);
    }

    @Test
    public void testDeleteById() {
        Long id = 1L;

        service.deleteById(id);

        verify(repository, times(1)).deleteById(id);
    }
}
