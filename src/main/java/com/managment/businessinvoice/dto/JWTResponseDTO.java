package com.managment.businessinvoice.dto;

import lombok.Builder;

@Builder
public class JWTResponseDTO {
    private String username;
    private String jwtToken;
}
