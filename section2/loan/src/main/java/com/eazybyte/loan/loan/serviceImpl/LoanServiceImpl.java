package com.eazybyte.loan.loan.serviceImpl;

import com.eazybyte.loan.loan.contant.LoanConstant;
import com.eazybyte.loan.loan.dto.LoanDTO;
import com.eazybyte.loan.loan.entity.LoanEntity;
import com.eazybyte.loan.loan.exception.LoanAlreadyExistsException;
import com.eazybyte.loan.loan.exception.ResourceNotFoundException;
import com.eazybyte.loan.loan.mapper.LoanMapper;
import com.eazybyte.loan.loan.repo.LoanRepository;
import com.eazybyte.loan.loan.service.iLoanService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
@Slf4j
/**
 * @param mobileNumber - Mobile Number of the Customer
 */
public class LoanServiceImpl implements iLoanService {

    private LoanRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {
        log.info("Inside the createLoan method");
        Optional<LoanEntity> optionalLoans= loansRepository.findByMobileNumber(mobileNumber);
        if(optionalLoans.isPresent()){
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber "+mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));
        log.info("loan created successfully {}",mobileNumber);
    }
    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new loan details
     */
    private LoanEntity createNewLoan(String mobileNumber) {
        log.info("Inside the createNew Loan method");
        LoanEntity newLoan = new LoanEntity();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoanConstant.HOME_LOAN);
        newLoan.setTotalLoan((long) LoanConstant.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0L);
        newLoan.setOutstandingAmount((long)LoanConstant.NEW_LOAN_LIMIT);
        log.info("new loan created",newLoan);
        return newLoan;
    }

    @Override
    public LoanDTO fetchLoan(String mobileNumber) {
        log.info("Inside the fetchLoan");
        LoanEntity loans = loansRepository
                .findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        log.info("fetch loan for mobileNumber {}",mobileNumber);
        return LoanMapper.mapToLoansDto(loans, new LoanDTO());
    }

    @Override
    public boolean updateLoan(LoanDTO loansDto) {
        log.info("Inside the update loan");
        LoanEntity loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.getLoanNumber()));
        LoanMapper.mapToLoans(loansDto, loans);
        loansRepository.save(loans);
        log.info("successfully update {}",loansDto.getMobileNumber());
        return  true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        log.info("Inside the delete loan method");
        LoanEntity loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        loansRepository.deleteById(loans.getLoanId());
        log.info("Sucessfully deleted {}",mobileNumber);
        return true;
    }
}
