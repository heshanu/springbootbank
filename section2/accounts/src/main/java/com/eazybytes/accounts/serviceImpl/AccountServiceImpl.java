package com.eazybytes.accounts.serviceImpl;

import com.eazybytes.accounts.constant.AccountConstant;
import com.eazybytes.accounts.dto.AccountDTO;
import com.eazybytes.accounts.dto.CustomerDTO;
import com.eazybytes.accounts.entities.AccountEntity;
import com.eazybytes.accounts.entities.CustomerEntity;
import com.eazybytes.accounts.exception.AcccountNotFetchException;
import com.eazybytes.accounts.exception.CustomerAlreadyExistException;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountRepo;
import com.eazybytes.accounts.repository.CustomerRepo;
import com.eazybytes.accounts.service.iAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Qualifier("accountServiceImpl")
@AllArgsConstructor
@Slf4j
public class AccountServiceImpl implements iAccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public void createAccount(CustomerDTO customerDTO) {
        log.info("inside the create Account{}",customerDTO.getMobileNumber());
        CustomerEntity customer= CustomerMapper.mapToCustomer(customerDTO,new CustomerEntity());
        Optional<CustomerEntity> optionalCustomerEntity=customerRepo.findByMobileNumber(customerDTO.getMobileNumber());
        //check customer in available i
        if(optionalCustomerEntity.isPresent()){
            throw new CustomerAlreadyExistException("Customer already registered with given mobileNumber"+customerDTO.getMobileNumber());
        }
//        customer.setCreatedAt(LocalDateTime.now());
//        customer.setCreatedBy("Anon");
        CustomerEntity savedCustomer=customerRepo.save(customer);
        accountRepo.save(createNewAccount(savedCustomer));
        log.info("Customer {} Succssfully created for mobileNumber",customerDTO.getMobileNumber());

    }
    private AccountEntity createNewAccount(CustomerEntity customer) {
        AccountEntity newAccount = new AccountEntity();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountConstant.SAVINGS);
        newAccount.setBranchAddress(AccountConstant.ADDRESS);
//        newAccount.setCreatedAt(LocalDateTime.now());
//        newAccount.setCreatedBy("Anon");
        return newAccount;
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        log.info("Inside of get all accounts");
        //get all entity from db
        List <AccountEntity> allAccountsEntity=accountRepo.findAll();
        //create list for dto bcoz we need return dto
        List <AccountDTO> allAccountsDTO=new ArrayList<>();

        //check db is empty
        if(!allAccountsEntity.isEmpty()){
            for(AccountEntity account: allAccountsEntity){
                AccountDTO accountDTO=new AccountDTO();
                AccountMapper.mapToAccountsDTO(account,accountDTO);
                allAccountsDTO.add(accountDTO);
            }
            return allAccountsDTO;
        }
        else{
            throw new AcccountNotFetchException("Unable to fetch all Accounts");
        }
    }

    @Override
    public CustomerDTO fetchAccount(String mobileNumber) {
        log.info("inside fetch account impl");
        CustomerEntity customer=customerRepo.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Customer","mobileNumber",mobileNumber));

        AccountEntity account=accountRepo.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()->new ResourceNotFoundException("Account","CustomerId",customer.getCustomerId().toString()));

        CustomerDTO customerDTO= CustomerMapper.mapToCustomerDto(customer,new CustomerDTO());
        customerDTO.setAccountDTO(AccountMapper.mapToAccountsDTO(account,new AccountDTO()));
        log.info("fetched customer using mobile {}",customerDTO.getMobileNumber());
        return customerDTO;
    }

    @Override
    public boolean updateAccount(CustomerDTO customerDTO) {
        log.info("inside th e update Account");
        boolean isUpdated = false;
        AccountDTO accountsDto = customerDTO.getAccountDTO();
        if(accountsDto !=null ){
            AccountEntity accounts = accountRepo.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountMapper.mapToAccountEntity(accountsDto, accounts);
            accounts = accountRepo.save(accounts);

            Long customerId = accounts.getCustomerId();
            CustomerEntity customer = customerRepo.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDTO,customer);
            customerRepo.save(customer);
            log.info("Successfully updated {}",customerDTO);
            isUpdated = true;
        }
        return  isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        log.info("inside the delete function");
        CustomerEntity customer = customerRepo.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountRepo.deleteByCustomerId(customer.getCustomerId());
        customerRepo.deleteById(customer.getCustomerId());
        log.info("customer id {} is deleted suceessfully",customer.getCustomerId());
        return true;
    }
}
