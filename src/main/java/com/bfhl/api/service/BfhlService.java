package com.bfhl.api.service;

import com.bfhl.api.dto.BfhlRequestDto;
import com.bfhl.api.dto.BfhlResponseDto;

public interface BfhlService {
    BfhlResponseDto processRequest(BfhlRequestDto request);
}
