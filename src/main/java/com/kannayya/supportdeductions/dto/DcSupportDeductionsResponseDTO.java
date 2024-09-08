package com.kannayya.supportdeductions.dto;
import com.kannayya.supportdeductions.entity.DcSupportDeductions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DcSupportDeductionsResponseDTO {
    private LocalDateTime requestTimeStamp;

    private LocalDateTime responseTimeStamp;

    private Long sprtSeqNum;

    private Long indvId;

    private String name;

    private Date effBeginDt;

    private Date effEndDt;

    private Date reportedDt;

    private Date clientAwareDt;

    private String expenseTypeCd;

    private BigDecimal monthlyActualAmt;

    private String expAmtVerf;

    private Date finalExpenseDt;

    private Date createDt;

    private Date updateDt;

    public DcSupportDeductionsResponseDTO(LocalDateTime now, LocalDateTime now1, long l, String janeDoe, BigDecimal bigDecimal) {
        this.requestTimeStamp = now;
        this.responseTimeStamp = now1;
        this.sprtSeqNum = l;
        this.name = janeDoe;
        this.monthlyActualAmt = bigDecimal;
    }


    public static DcSupportDeductionsResponseDTO fromEntity(DcSupportDeductions entity, LocalDateTime requestTimeStamp, LocalDateTime responseTimeStamp) {
        return new DcSupportDeductionsResponseDTO(
            requestTimeStamp,
            responseTimeStamp,
            entity.getSprtSeqNum(),
            entity.getIndvId(),
            entity.getName(),
            entity.getEffBeginDt(),
            entity.getEffEndDt(),
            entity.getReportedDt(),
            entity.getClientAwareDt(),
            entity.getExpenseTypeCd(),
            entity.getMonthlyActualAmt(),
            entity.getExpAmtVerf(),
            entity.getFinalExpenseDt(),
            entity.getCreateDt(),
            entity.getUpdateDt()
        );
    }

}
