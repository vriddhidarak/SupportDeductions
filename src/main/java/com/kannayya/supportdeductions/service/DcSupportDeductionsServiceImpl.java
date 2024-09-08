package com.kannayya.supportdeductions.service;

import com.kannayya.supportdeductions.dto.DcSupportDeductionsGetAllDTO;
import com.kannayya.supportdeductions.dto.DcSupportDeductionsRequestDTO;
import com.kannayya.supportdeductions.dto.DcSupportDeductionsResponseDTO;
import com.kannayya.supportdeductions.repository.DcSupportDeductionsRepository;
import com.kannayya.supportdeductions.entity.DcSupportDeductions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DcSupportDeductionsServiceImpl implements DcSupportDeductionsService {


    private final DcSupportDeductionsRepository repository;

    @Autowired
    public DcSupportDeductionsServiceImpl(DcSupportDeductionsRepository repository) {
        this.repository = repository;
    }

    public List<DcSupportDeductionsGetAllDTO> findAll() {
        LocalDateTime requestTimeStamp = LocalDateTime.now();

        return repository.findAll().stream().map(value->DcSupportDeductionsGetAllDTO.fromEntity(value,requestTimeStamp,LocalDateTime.now())).toList();
    }

    public Optional<DcSupportDeductionsResponseDTO> findById(Long id) {
        LocalDateTime requestTimeStamp = LocalDateTime.now();
        Optional<DcSupportDeductions> entity = repository.findById(id);
        if (entity.isPresent()) {
            LocalDateTime responseTimeStamp = LocalDateTime.now();
            return Optional.of(DcSupportDeductionsResponseDTO.fromEntity(entity.get(), requestTimeStamp, responseTimeStamp));
        }
        return Optional.empty();
    }

    public DcSupportDeductionsResponseDTO save(DcSupportDeductionsRequestDTO request) {
        LocalDateTime requestTimeStamp = LocalDateTime.now();
        DcSupportDeductions entity = request.toEntity();
        DcSupportDeductions savedEntity = repository.save(entity);
        LocalDateTime responseTimeStamp = LocalDateTime.now();
        return DcSupportDeductionsResponseDTO.fromEntity(savedEntity, requestTimeStamp, responseTimeStamp);
    }

    public DcSupportDeductionsResponseDTO update(Long id, DcSupportDeductionsRequestDTO request) {
        LocalDateTime requestTimeStamp = LocalDateTime.now();
        Optional<DcSupportDeductions> entity = repository.findById(id);
        if (entity.isPresent()) {
            DcSupportDeductions updatedEntity = request.toEntity();
            DcSupportDeductions savedEntity = repository.save(updatedEntity);
            LocalDateTime responseTimeStamp = LocalDateTime.now();
            return DcSupportDeductionsResponseDTO.fromEntity(savedEntity, requestTimeStamp, responseTimeStamp);
        }
        return null;
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}