package com.eazybytes.accounts.mapper;

import com.eazybytes.accounts.dto.AccountDTO;
import com.eazybytes.accounts.entities.AccountEntity;

public class AccountMapper {
    public static AccountDTO mapToAccountsDTO(AccountEntity accounts, AccountDTO accountsDTO) {
        accountsDTO.setAccountNumber(accounts.getAccountNumber());
        accountsDTO.setAccountType(accounts.getAccountType());
        accountsDTO.setBranchAddress(accounts.getBranchAddress());
        return accountsDTO;
    }

    public static AccountEntity mapToAccountEntity(AccountDTO accountsDto, AccountEntity accounts) {
        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        return accounts;
    }

}
