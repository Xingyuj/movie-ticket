package org.ethan.controller.dto;

import lombok.Data;
import org.ethan.domain.Ticket;

import java.util.List;

@Data
public class ResponseTransactionDto {
    private Long transactionId;
    private List<Ticket> tickets;
    private Double totalCost;
}
