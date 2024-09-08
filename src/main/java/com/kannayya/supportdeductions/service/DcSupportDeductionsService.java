package com.kannayya.supportdeductions.service;

import com.kannayya.supportdeductions.dto.DcSupportDeductionsGetAllDTO;
import com.kannayya.supportdeductions.dto.DcSupportDeductionsRequestDTO;
import com.kannayya.supportdeductions.dto.DcSupportDeductionsResponseDTO;

import java.util.List;
import java.util.Optional;

public interface DcSupportDeductionsService {
    List<DcSupportDeductionsGetAllDTO> findAll();

    Optional<DcSupportDeductionsResponseDTO> findById(Long id);

    DcSupportDeductionsResponseDTO save(DcSupportDeductionsRequestDTO entity);

    DcSupportDeductionsResponseDTO update(Long id, DcSupportDeductionsRequestDTO entity);
    void deleteById(Long id);
}
