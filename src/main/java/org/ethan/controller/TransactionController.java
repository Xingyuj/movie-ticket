package org.ethan.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ethan.controller.dto.DtoMapper;
import org.ethan.controller.dto.RequestTransactionDto;
import org.ethan.controller.dto.ResponseTransactionDto;
import org.ethan.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
@Tag(name = "transactions", description = "Operations to manage transactions")
public class TransactionController {

    @Autowired
    TicketService ticketService;

    @PostMapping
    @Operation(summary = "transactions", description = "transactions")
    public ResponseEntity<ResponseTransactionDto> transaction(@RequestBody RequestTransactionDto transaction) {
        return new ResponseEntity<>(
                DtoMapper.toResponseTransactionDto(transaction.getTransactionId(),
                        ticketService.generateTicketsFromTransaction(transaction)),
                HttpStatus.OK);
    }

}
