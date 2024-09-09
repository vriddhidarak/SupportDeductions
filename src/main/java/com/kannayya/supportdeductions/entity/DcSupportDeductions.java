package com.kannayya.supportdeductions.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dc_support_deductions", schema = "wp_cap")
public class DcSupportDeductions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  // Auto-generating primary key
    @Column(name = "sprt_seq_num", nullable = false) // Primary Key, Not Null
    private Long sprtSeqNum;

    @Column(name = "indv_id", nullable = false) // Numeric, Not Null
    private Long indvId;

    @Column(name = "name", nullable = false, length = 100) // Varchar(100), Not Null
    private String name;

    @Column(name = "eff_begin_dt", nullable = false) // LocalDate, Not Null
    private LocalDate effBeginDt;

    @Column(name = "eff_end_dt") // LocalDate
    private LocalDate effEndDt;

    @Column(name = "reported_dt") // LocalDate
    private LocalDate reportedDt;

    @Column(name = "client_aware_dt") // LocalDate
    private LocalDate clientAwareDt;

    @Column(name = "expense_type_cd", length = 5) // Varchar(5)
    private String expenseTypeCd;

    @Column(name = "monthly_actual_amt", precision = 10, scale = 2) // Numeric(10,2)
    private BigDecimal monthlyActualAmt;

    @Column(name = "exp_amt_verf", length = 5) // Varchar(5)
    private String expAmtVerf;

    @Column(name = "final_expense_dt") // LocalDate
    private LocalDate finalExpenseDt;

    @Column(name = "create_dt") // Timestamp without time zone
    private LocalDate createDt;

    @Column(name = "upLocalDate_dt") // Timestamp without time zone
    private LocalDate upLocalDateDt;

}
