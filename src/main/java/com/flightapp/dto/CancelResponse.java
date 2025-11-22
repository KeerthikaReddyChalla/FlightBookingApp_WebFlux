package com.flightapp.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CancelResponse {
    private String pnr;
    private String message;
}
