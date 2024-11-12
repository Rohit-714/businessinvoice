package com.managment.businessinvoice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JWTRequestDTO {
    private String email;
    private String password;
}
