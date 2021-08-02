package org.ethan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.ethan.controller.dto.RequestTransactionDto;
import org.ethan.domain.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableWebMvc
public class MovieTicketTest {
    private static final String API_URL = "/api/v1/transactions";

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private static final ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }

    @Test
    public void testEachTypeIncluded() throws Exception {
        RequestTransactionDto dto = new RequestTransactionDto();
        dto.setTransactionId(3L);
        dto.setCustomers(
                Arrays.asList(new Customer("Jesse", 36),
                        new Customer("Daniel", 95),
                        new Customer("Mary", 15),
                        new Customer("Michelle", 10)));
        mockMvc.perform(post(API_URL)
                .contentType("application/json")
                .content(getContentStringFromInput(dto))
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tickets", hasSize(4)));
    }

    @Test
    public void testMoreThanThreeChildren() throws Exception {
        RequestTransactionDto dto = new RequestTransactionDto();
        dto.setTransactionId(3L);
        dto.setCustomers(
                Arrays.asList(new Customer("Jesse", 45),
                        new Customer("Daniel", 5),
                        new Customer("Mary", 1),
                        new Customer("Michelle", 10)));
        mockMvc.perform(post(API_URL)
                .contentType("application/json")
                .content(getContentStringFromInput(dto))
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tickets", hasSize(2)))
                .andExpect(jsonPath("$.totalCost", is(36.25)));
    }

    public static String getContentStringFromInput(Object input, String... fieldsToRemove) throws JsonProcessingException {
        JsonNode node = mapper.convertValue(input, JsonNode.class);
        if (node instanceof ObjectNode) {
            ObjectNode objNode = (ObjectNode) node;
            Arrays.stream(fieldsToRemove)
                    .filter(objNode::has)
                    .forEach(objNode::remove);
        }
        return mapper.writeValueAsString(node);
    }
}
