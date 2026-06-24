package com.bfhl.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BfhlResponseDto {
    @JsonProperty("is_success")
    private boolean isSuccess;

    @JsonProperty("user_id")
    private String userId;

    private String email;

    @JsonProperty("roll_number")
    private String rollNumber;

    @JsonProperty("even_numbers")
    private List<String> evenNumbers;

    @JsonProperty("odd_numbers")
    private List<String> oddNumbers;

    private List<String> alphabets;

    @JsonProperty("special_characters")
    private List<String> specialCharacters;

    @JsonProperty("sepcial_characters")
    private List<String> sepcialCharacters; // Typo variant matching Example B request

    private String sum;

    @JsonProperty("concat_string")
    private String concatString;
}
