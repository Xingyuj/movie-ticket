package org.ethan.controller.dto;

import lombok.Data;
import org.ethan.domain.Customer;

import java.util.List;

@Data
public class RequestTransactionDto {
    private Long transactionId;
    private List<Customer> customers;
}
