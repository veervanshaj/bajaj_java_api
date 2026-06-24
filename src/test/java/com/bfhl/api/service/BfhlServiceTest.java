package com.bfhl.api.service;

import com.bfhl.api.dto.BfhlRequestDto;
import com.bfhl.api.dto.BfhlResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BfhlServiceTest {

    @Autowired
    private BfhlService bfhlService;

    @Test
    public void testExampleA() {
        BfhlRequestDto request = BfhlRequestDto.builder()
                .data(Arrays.asList("a", "1", "334", "4", "R", "$"))
                .build();

        BfhlResponseDto response = bfhlService.processRequest(request);

        assertTrue(response.isSuccess());
        assertEquals("john_doe_17091999", response.getUserId());
        assertEquals("john@xyz.com", response.getEmail());
        assertEquals("ABCD123", response.getRollNumber());
        assertEquals(Arrays.asList("1"), response.getOddNumbers());
        assertEquals(Arrays.asList("334", "4"), response.getEvenNumbers());
        assertEquals(Arrays.asList("A", "R"), response.getAlphabets());
        assertEquals(Arrays.asList("$"), response.getSpecialCharacters());
        assertEquals(Arrays.asList("$"), response.getSepcialCharacters());
        assertEquals("339", response.getSum());
        assertEquals("Ra", response.getConcatString());
    }

    @Test
    public void testExampleB() {
        BfhlRequestDto request = BfhlRequestDto.builder()
                .data(Arrays.asList("2", "a", "y", "4", "&", "-", "*", "5", "92", "b"))
                .build();

        BfhlResponseDto response = bfhlService.processRequest(request);

        assertTrue(response.isSuccess());
        assertEquals("john_doe_17091999", response.getUserId());
        assertEquals(Arrays.asList("5"), response.getOddNumbers());
        assertEquals(Arrays.asList("2", "4", "92"), response.getEvenNumbers());
        assertEquals(Arrays.asList("A", "Y", "B"), response.getAlphabets());
        assertEquals(Arrays.asList("&", "-", "*"), response.getSpecialCharacters());
        assertEquals(Arrays.asList("&", "-", "*"), response.getSepcialCharacters());
        assertEquals("103", response.getSum());
        assertEquals("ByA", response.getConcatString());
    }

    @Test
    public void testExampleC() {
        BfhlRequestDto request = BfhlRequestDto.builder()
                .data(Arrays.asList("A", "ABCD", "DOE"))
                .build();

        BfhlResponseDto response = bfhlService.processRequest(request);

        assertTrue(response.isSuccess());
        assertEquals("john_doe_17091999", response.getUserId());
        assertEquals(Collections.emptyList(), response.getOddNumbers());
        assertEquals(Collections.emptyList(), response.getEvenNumbers());
        assertEquals(Arrays.asList("A", "ABCD", "DOE"), response.getAlphabets());
        assertEquals(Collections.emptyList(), response.getSpecialCharacters());
        assertEquals(Collections.emptyList(), response.getSepcialCharacters());
        assertEquals("0", response.getSum());
        assertEquals("EoDdCbAa", response.getConcatString());
    }

    @Test
    public void testNullData() {
        BfhlRequestDto request = BfhlRequestDto.builder()
                .data(null)
                .build();

        BfhlResponseDto response = bfhlService.processRequest(request);

        assertFalse(response.isSuccess());
        assertEquals("john_doe_17091999", response.getUserId());
        assertEquals("0", response.getSum());
        assertEquals("", response.getConcatString());
    }
}
