package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.constant.*;
import com.eazybytes.accounts.dto.*;
import com.eazybytes.accounts.service.iAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(
        name="CRUD REST APIs for Accounts in bank",
        description = "CRUD operations"
)
@RestController
@RequestMapping(value = "/api/v1/account",produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountController {
    //depedencey injection part of inverstion of control in spring
    // hollywood theroy dont call us,we will call u
    @Autowired
    private iAccountService iAccountService;

    @Value("${build.version}")
    private String buildVersion;

    //for documentation
    @Operation(
            summary = "create account REST API",
            description = "Create account for customer and account"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",description = "HTTP status created!"

            ),
            @ApiResponse(
                    responseCode = "500",description = "HTTP Status Internal Server Error!",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )

    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createAccount(@Valid @RequestBody CustomerDTO customerDTO){
        iAccountService.createAccount(customerDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ResponseDTO(AccountConstant.STATUS_201, AccountConstant.MESSAGE_201));
    }

    ////////////////////////////////end////////////////////////
    @Operation(
            summary = "fetch all accounts REST API",
            description = "fetch all accounts for rest to show accounts"
    )
    @GetMapping("/allAccounts")
    public ResponseEntity<List<AccountDTO>> getAllAccount() {
        List<AccountDTO> accountDTOs = iAccountService.getAllAccounts();
        return ResponseEntity.ok(accountDTOs);
    }

    ////////////////////////////////end////////////////////////

    @Operation(
            summary = "fetch customer and account API",
            description = "fetch customer and account by mobile phone"
    )
    @ApiResponse(
            responseCode = "200",description = "HTTP status OK!"
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDTO> fetchAccountDetails(@Valid @RequestParam

                                                          @Pattern(regexp ="(^$|[0-9]{10})", message = "Account number must be 10 digits") String mobileNumber){
       CustomerDTO customerDTO=iAccountService.fetchAccount(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDTO);
    }

    ////////////////////////////////end////////////////////////
    @Operation(
            summary = "update accounts REST API",
            description = "update accounts for rest to show accounts"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201", description = "HTTP status created!"
            ),
            @ApiResponse(
                    responseCode = "500",description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateAccountDetails(@Valid @RequestBody CustomerDTO customerDto) {
        boolean isUpdated = iAccountService.updateAccount(customerDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountConstant.STATUS_200, AccountConstant.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(AccountConstant.STATUS_417, AccountConstant.MESSAGE_417_UPDATE));
        }
    }

    ////////////////////////////////end////////////////////////

    @Operation(
            summary = "delete accounts REST API",
            description = "delete account by mobile phone"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status Internal Server Error"
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteAccountDetails(@RequestParam
                                                            @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber) {
        boolean isDeleted = iAccountService.deleteAccount(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountConstant.STATUS_200,AccountConstant.MESSAGE_200));
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(AccountConstant.STATUS_417, AccountConstant.MESSAGE_417_DELETE));
        }
    }


    @Operation(
            summary = "Get Build information",
            description = "Get Build information that is deployed into accounts microservice"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }




}
