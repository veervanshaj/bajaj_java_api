package com.bfhl.api.service;

import com.bfhl.api.dto.BfhlRequestDto;
import com.bfhl.api.dto.BfhlResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BfhlServiceImpl implements BfhlService {

    @Value("${bfhl.user.name:john_doe}")
    private String userName;

    @Value("${bfhl.user.dob:17091999}")
    private String userDob;

    @Value("${bfhl.user.email:john@xyz.com}")
    private String userEmail;

    @Value("${bfhl.user.roll-number:ABCD123}")
    private String rollNumber;

    @Override
    public BfhlResponseDto processRequest(BfhlRequestDto request) {
        if (request == null || request.getData() == null) {
            return BfhlResponseDto.builder()
                    .isSuccess(false)
                    .userId(getFormattedUserId())
                    .email(userEmail)
                    .rollNumber(rollNumber)
                    .evenNumbers(Collections.emptyList())
                    .oddNumbers(Collections.emptyList())
                    .alphabets(Collections.emptyList())
                    .specialCharacters(Collections.emptyList())
                    .sepcialCharacters(Collections.emptyList())
                    .sum("0")
                    .concatString("")
                    .build();
        }

        List<String> evenNumbers = new ArrayList<>();
        List<String> oddNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();
        long sum = 0;
        List<Character> allLetters = new ArrayList<>();

        for (String item : request.getData()) {
            if (item == null) {
                continue;
            }

            // Extract all alphabetical characters for the concat_string
            for (char c : item.toCharArray()) {
                if (Character.isLetter(c)) {
                    allLetters.add(c);
                }
            }

            // Classify the item itself
            if (isNumeric(item)) {
                try {
                    long val = Long.parseLong(item);
                    sum += val;
                    if (val % 2 == 0) {
                        evenNumbers.add(item);
                    } else {
                        oddNumbers.add(item);
                    }
                } catch (NumberFormatException e) {
                    // Fallback to special characters if parsing fails (should not happen due to isNumeric check)
                    specialCharacters.add(item);
                }
            } else if (isAlphabetic(item)) {
                alphabets.add(item.toUpperCase());
            } else {
                specialCharacters.add(item);
            }
        }

        // Build the concat_string (reverse order of letters, alternating caps)
        Collections.reverse(allLetters);
        StringBuilder concatBuilder = new StringBuilder();
        for (int i = 0; i < allLetters.size(); i++) {
            char c = allLetters.get(i);
            if (i % 2 == 0) {
                concatBuilder.append(Character.toUpperCase(c));
            } else {
                concatBuilder.append(Character.toLowerCase(c));
            }
        }

        return BfhlResponseDto.builder()
                .isSuccess(true)
                .userId(getFormattedUserId())
                .email(userEmail)
                .rollNumber(rollNumber)
                .evenNumbers(evenNumbers)
                .oddNumbers(oddNumbers)
                .alphabets(alphabets)
                .specialCharacters(specialCharacters)
                .sepcialCharacters(specialCharacters) // match duplicate typo version
                .sum(String.valueOf(sum))
                .concatString(concatBuilder.toString())
                .build();
    }

    private String getFormattedUserId() {
        String cleanName = userName != null ? userName.trim().toLowerCase().replaceAll("\\s+", "_") : "user";
        String cleanDob = userDob != null ? userDob.trim() : "";
        return cleanName + "_" + cleanDob;
    }

    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.matches("^-?\\d+$");
    }

    private boolean isAlphabetic(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.matches("^[a-zA-Z]+$");
    }
}
