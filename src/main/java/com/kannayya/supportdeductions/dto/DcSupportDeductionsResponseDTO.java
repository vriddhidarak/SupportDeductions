package com.kannayya.supportdeductions.dto;

import com.kannayya.supportdeductions.entity.DcSupportDeductions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DcSupportDeductionsResponseDTO {

    private Long sprtSeqNum;
    private String name;
    private BigDecimal monthlyActualAmt;

    public static DcSupportDeductionsResponseDTO fromEntity(DcSupportDeductions entity) {
        return new DcSupportDeductionsResponseDTO(
            entity.getSprtSeqNum(),
            entity.getName(),
            entity.getMonthlyActualAmt()
        );
    }
}
