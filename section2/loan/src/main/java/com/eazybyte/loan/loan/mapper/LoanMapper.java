package com.eazybyte.loan.loan.mapper;

import com.eazybyte.loan.loan.dto.LoanDTO;
import com.eazybyte.loan.loan.entity.LoanEntity;

public class LoanMapper {
    public static LoanDTO mapToLoansDto(LoanEntity loan, LoanDTO loanDTO) {
        loanDTO.setLoanNumber(loan.getLoanNumber());
        loanDTO.setLoanType(loan.getLoanType());
        loanDTO.setMobileNumber(loan.getMobileNumber());
        loanDTO.setTotalLoan(Math.toIntExact(loan.getTotalLoan()));
        loanDTO.setAmountPaid(Math.toIntExact(loan.getAmountPaid()));
        loanDTO.setOutstandingAmount(Math.toIntExact(loan.getOutstandingAmount()));
        return loanDTO;
    }

    public static LoanEntity mapToLoans(LoanDTO loansDto, LoanEntity loans) {
        loans.setLoanNumber(loansDto.getLoanNumber());
        loans.setLoanType(loansDto.getLoanType());
        loans.setMobileNumber(loansDto.getMobileNumber());
        loans.setTotalLoan((long) loansDto.getTotalLoan());
        loans.setAmountPaid((long) loansDto.getAmountPaid());
        loans.setOutstandingAmount((long) loansDto.getOutstandingAmount());
        return loans;
    }

}
