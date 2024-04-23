package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Schema(
        name = "Response",
        description = "Schema to hold suceessful response"
)
@AllArgsConstructor
public class ResponseDTO {
    @Schema(
            name = "Response status code",
            description = "response's status code",
            example = "200"
    )
    private String statusCode;
    ////////////////////////////////end////////////////////////
    @Schema(
            name = "Response Status Msg",
            description = "response's status Message for better understanding",
            example = "CREATED"
    )
    private String statusMsg;
}
