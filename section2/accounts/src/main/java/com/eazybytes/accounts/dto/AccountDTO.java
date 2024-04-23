package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "Accounts",
        description = "Schema to hold Customer and account information"
)
public class AccountDTO {
    @Schema(
            name = "Account Number",
            description = "Account number of bank",
            example = "111111111"
    )
    @NotEmpty(message = "Account Number cannot be empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must be 10 digits")
    private Long accountNumber;
    ////////////////////////////////end////////////////////////
    @Schema(
            name = "Account Type",
            description = "Account Type of bank Saving mobile Online etc",
            example = "SAVING"
    )
    @NotEmpty(message = "Account Type cannot be empty")
    private String accountType;
    ////////////////////////////////end////////////////////////
    @Schema(
            name = "Branch address",
            description = "Account's branch address and location",
            example = "Sri Lanka"
    )
    @NotEmpty(message = "Branch Address cannot be empty")
    private String branchAddress;
}
