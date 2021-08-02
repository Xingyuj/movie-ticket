package org.ethan.controller.dto;

import org.ethan.domain.Ticket;

import java.util.List;

public class DtoMapper {
    public static ResponseTransactionDto toResponseTransactionDto(Long id, List<Ticket> tickets){
        ResponseTransactionDto dto = new ResponseTransactionDto();
        dto.setTransactionId(id);
        dto.setTickets(tickets);
        dto.setTotalCost(tickets.stream().mapToDouble(Ticket::getTotalCost).sum());
        return dto;
    }
}
