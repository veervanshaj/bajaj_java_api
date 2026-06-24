package com.bfhl.api.controller;

import com.bfhl.api.dto.BfhlRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BfhlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${bfhl.user.name:john_doe}")
    private String expectedName;

    @Value("${bfhl.user.dob:17091999}")
    private String expectedDob;

    @Value("${bfhl.user.email:john@xyz.com}")
    private String expectedEmail;

    @Value("${bfhl.user.roll-number:ABCD123}")
    private String expectedRollNumber;

    private String getExpectedUserId() {
        return (expectedName + "_" + expectedDob).toLowerCase().replaceAll("\\s+", "_");
    }

    @Test
    public void testGetBFHL() throws Exception {
        mockMvc.perform(get("/bfhl"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation_code", is(1)));
    }

    @Test
    public void testGetHealth() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("UP")));
    }

    @Test
    public void testPostBFHLSuccess() throws Exception {
        BfhlRequestDto request = BfhlRequestDto.builder()
                .data(Arrays.asList("a", "1", "334", "4", "R", "$"))
                .build();

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(true)))
                .andExpect(jsonPath("$.user_id", is(getExpectedUserId())))
                .andExpect(jsonPath("$.email", is(expectedEmail)))
                .andExpect(jsonPath("$.roll_number", is(expectedRollNumber)))
                .andExpect(jsonPath("$.sum", is("339")))
                .andExpect(jsonPath("$.concat_string", is("Ra")));
    }

    @Test
    public void testPostBFHLMalformedJson() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{invalid json}"))
                .andExpect(status().isOk()) // ExceptionHandler turns it into 200 OK with success:false
                .andExpect(jsonPath("$.is_success", is(false)));
    }
}
