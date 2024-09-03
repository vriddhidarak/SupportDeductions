package com.kannayya.supportdeductions.service;

import com.kannayya.supportdeductions.dto.DcSupportDeductionsResponseDTO;
import com.kannayya.supportdeductions.repository.DcSupportDeductionsRepository;
import com.kannayya.supportdeductions.entity.DcSupportDeductions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DcSupportDeductionsService {

    @Autowired
    private DcSupportDeductionsRepository repository;

    public List<DcSupportDeductionsResponseDTO> findAll() {

        return repository.findAll().stream().map(DcSupportDeductionsResponseDTO::fromEntity).toList();
    }

    public Optional<DcSupportDeductions> findById(Long id) {
        return repository.findById(id);
    }

    public DcSupportDeductions save(DcSupportDeductions entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
