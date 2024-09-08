package com.kannayya.supportdeductions;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kannayya.supportdeductions.controller.DcSupportDeductionsController;
import com.kannayya.supportdeductions.dto.*;
import com.kannayya.supportdeductions.service.DcSupportDeductionsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;

class DcSupportDeductionsControllerTest {

    @Mock
    private DcSupportDeductionsService service;

    @InjectMocks
    private DcSupportDeductionsController controller;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
      void testGetAllDeductions() throws Exception {
        List<DcSupportDeductionsGetAllDTO> deductionsList = Arrays.asList(
                new DcSupportDeductionsGetAllDTO(LocalDateTime.now(), LocalDateTime.now(), 1L, "John Doe", new BigDecimal("500")),
                new DcSupportDeductionsGetAllDTO(LocalDateTime.now(), LocalDateTime.now(), 2L, "Jane Doe", new BigDecimal("600"))
        );

        when(service.findAll()).thenReturn(deductionsList);

        mockMvc.perform(get("/api/deductions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"));

        verify(service, times(1)).findAll();
    }

    @Test
      void testGetDeductionById_Found() throws Exception {
        DcSupportDeductionsResponseDTO responseDTO = new DcSupportDeductionsResponseDTO(
                LocalDateTime.now(), LocalDateTime.now(), 1L, "John Doe", new BigDecimal("500"));

        when(service.findById(1L)).thenReturn(Optional.of(responseDTO));

        mockMvc.perform(get("/api/deductions/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(service, times(1)).findById(1L);
    }

    @Test
      void testGetDeductionById_NotFound() throws Exception {
        when(service.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/deductions/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(service, times(1)).findById(1L);
    }

    @Test
      void testCreateDeduction() throws Exception {
        DcSupportDeductionsRequestDTO requestDTO = new DcSupportDeductionsRequestDTO(
                1L, "John Doe", new Date(), new Date(), new Date(), new Date(),
                "EXP001", new BigDecimal("500"), "Verified", new Date(), new Date(), new Date());
        DcSupportDeductionsResponseDTO responseDTO = new DcSupportDeductionsResponseDTO(
                LocalDateTime.now(), LocalDateTime.now(), 1L, "John Doe", new BigDecimal("500"));

        when(service.save(any(DcSupportDeductionsRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/deductions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.monthlyActualAmt").value(500));

        verify(service, times(1)).save(any(DcSupportDeductionsRequestDTO.class));
    }

    @Test
      void testUpdateDeduction_Success() throws Exception {
        DcSupportDeductionsRequestDTO requestDTO = new DcSupportDeductionsRequestDTO(
                1L, "John Doe", new Date(), new Date(), new Date(), new Date(),
                "EXP001", new BigDecimal("500"), "Verified", new Date(), new Date(), new Date());
        DcSupportDeductionsResponseDTO responseDTO = new DcSupportDeductionsResponseDTO(
                LocalDateTime.now(), LocalDateTime.now(), 1L, "John Doe", new BigDecimal("500"));

        when(service.update(eq(1L), any(DcSupportDeductionsRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/api/deductions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(service, times(1)).update(eq(1L), any(DcSupportDeductionsRequestDTO.class));
    }

    @Test
      void testUpdateDeduction_NotFound() throws Exception {
        DcSupportDeductionsRequestDTO requestDTO = new DcSupportDeductionsRequestDTO(
                1L, "John Doe", new Date(), new Date(), new Date(), new Date(),
                "EXP001", new BigDecimal("500"), "Verified", new Date(), new Date(), new Date());

        when(service.update(eq(1L), any(DcSupportDeductionsRequestDTO.class))).thenReturn(null);

        mockMvc.perform(put("/api/deductions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isNotFound());

        verify(service, times(1)).update(eq(1L), any(DcSupportDeductionsRequestDTO.class));
    }

    @Test
      void testDeleteDeduction() throws Exception {
        doNothing().when(service).deleteById(1L);

        mockMvc.perform(delete("/api/deductions/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(service, times(1)).deleteById(1L);
    }
}
