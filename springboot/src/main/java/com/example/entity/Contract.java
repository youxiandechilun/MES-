package com.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Contract {
    private Integer id;
    private String contractNumber;
    private String contractName;
    private String businessOpportunity;
    private String customerCode;
    private String customerName;
    private String customerPhone;
    private Boolean salesOrderGenerated;
    private Boolean invoiceIssued;
    private BigDecimal totalAmount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDateTime;

    private String contractType;
    private String partyA;
    private String partyB;
    private String remarks;
    private String executionStage;
    private String attachmentUrl;
}