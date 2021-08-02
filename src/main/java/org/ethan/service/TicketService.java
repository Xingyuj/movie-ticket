package org.ethan.service;

import org.ethan.controller.dto.RequestTransactionDto;
import org.ethan.domain.Customer;
import org.ethan.domain.Ticket;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {

    private static final Double ADULT_PRICE = 25D;
    private static final Double CHILDREN_PRICE = 5D;
    private static final Double TEEN_PRICE = 12D;
    private static final Double SENIOR_DISCOUNT = 0.7;
    private static final Double SENIOR_PRICE = ADULT_PRICE * SENIOR_DISCOUNT;
    private static final Double CHILDREN_DISCOUNT = 0.75;
    private static final int CHILDREN_DISCOUNT_THRESHOLD_SIZE = 3;

    private static final int CHILDREN_NOMOREAGE = 11;
    private static final int TEEN_NOMOREAGE = 18;
    private static final int ADULT_NOMOREAGE = 65;

    public List<Ticket> generateTicketsFromTransaction(RequestTransactionDto transaction) {
        List<Ticket> tickets = new ArrayList<>();
        List<Customer> adults = new ArrayList<>();
        List<Customer> seniors = new ArrayList<>();
        List<Customer> teens = new ArrayList<>();
        List<Customer> children = new ArrayList<>();
        transaction.getCustomers().forEach(
                customer -> {
                    int age = customer.getAge();
                    if (age < CHILDREN_NOMOREAGE) {
                        children.add(customer);
                    } else if (age < TEEN_NOMOREAGE) {
                        teens.add(customer);
                    } else if (age < ADULT_NOMOREAGE) {
                        adults.add(customer);
                    } else {
                        seniors.add(customer);
                    }
                }
        );
        if (adults.size() > 0) {
            tickets.add(new Ticket("Adult", adults.size(), adults.size() * ADULT_PRICE));
        }
        if (children.size() > 0 && children.size() < CHILDREN_DISCOUNT_THRESHOLD_SIZE) {
            tickets.add(new Ticket("Children", children.size(), children.size() * CHILDREN_PRICE));
        } else if (children.size() >= CHILDREN_DISCOUNT_THRESHOLD_SIZE) {
            tickets.add(new Ticket("Children", children.size(), children.size() * CHILDREN_PRICE * CHILDREN_DISCOUNT));
        }
        if (seniors.size() > 0) {
            tickets.add(new Ticket("Senior", seniors.size(), seniors.size() * SENIOR_PRICE));
        }
        if (teens.size() > 0) {
            tickets.add(new Ticket("Teen", teens.size(), teens.size() * TEEN_PRICE));
        }
        return tickets;
    }
}
