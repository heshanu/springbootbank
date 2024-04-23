package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold customer and acccount info"
)
public class CustomerDTO {

    @Schema(
            name = "Customer's Email",
            description = "Email of the customer",
            example = "user@gmail.com"
    )
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    ////////////////////////////////end////////////////////////
    @Schema(
            name = "Customer name",
            description = "Name of the customer",
            example = "EazyByte"
    )
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 5,max = 30,message = "length of the customer name should be between 5 to 30")
    private String name;
    ////////////////////////////////end////////////////////////
    @Schema(
            name = "Customer mobile phone",
            description = "mobile number of the customer",
            example = "1111111111"
    )
    @NotEmpty(message = "Mobile Number cannot be empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(
            description = "Account details of the Customer"

    )
    private AccountDTO accountDTO;
}
