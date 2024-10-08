package com.kannayya.supportdeductions.dto;

import com.kannayya.supportdeductions.entity.DcSupportDeductions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DcSupportDeductionsGetAllDTO {

    private LocalDateTime requestTimeStamp;
    private LocalDateTime responseTimeStamp;
    private Long sprtSeqNum;
    private String name;
    private BigDecimal monthlyActualAmt;

    public DcSupportDeductionsGetAllDTO(long l, long l1, String janeDoe) {
        this.sprtSeqNum = l;
        this.monthlyActualAmt = new BigDecimal(l1);
        this.name = janeDoe;

    }

    public static DcSupportDeductionsGetAllDTO fromEntity(DcSupportDeductions entity, LocalDateTime requestTimeStamp, LocalDateTime responseTimeStamp) {
        return new DcSupportDeductionsGetAllDTO(
            requestTimeStamp,
            responseTimeStamp,
            entity.getSprtSeqNum(),
            entity.getName(),
            entity.getMonthlyActualAmt()
        );
    }
}
