package com.kannayya.supportdeductions.dto;

import com.kannayya.supportdeductions.entity.DcSupportDeductions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DcSupportDeductionsRequestDTO {

    private Long indvId;

    private String name;

    private LocalDate effBeginDt;

    private LocalDate effEndDt;

    private LocalDate reportedDt;

    private LocalDate clientAwareDt;

    private String expenseTypeCd;

    private BigDecimal monthlyActualAmt;

    private String expAmtVerf;

    private LocalDate finalExpenseDt;

    private LocalDate createDt;

    private LocalDate upLocalDateDt;

    public DcSupportDeductionsRequestDTO(String janeDoe, BigDecimal bigDecimal) {
        this.name = janeDoe;
        this.monthlyActualAmt = bigDecimal;
    }

    public DcSupportDeductions toEntity() {
        DcSupportDeductionsRequestDTO dto = this;
        DcSupportDeductions entity = new DcSupportDeductions();
        entity.setIndvId(dto.getIndvId());
        entity.setName(dto.getName());
        entity.setEffBeginDt(dto.getEffBeginDt());
        entity.setEffEndDt(dto.getEffEndDt());
        entity.setReportedDt(dto.getReportedDt());
        entity.setClientAwareDt(dto.getClientAwareDt());
        entity.setExpenseTypeCd(dto.getExpenseTypeCd());
        entity.setMonthlyActualAmt(dto.getMonthlyActualAmt());
        entity.setExpAmtVerf(dto.getExpAmtVerf());
        entity.setFinalExpenseDt(dto.getFinalExpenseDt());
        entity.setCreateDt(dto.getCreateDt());
        entity.setUpLocalDateDt(dto.getUpLocalDateDt());
        return entity;
    }
}
