package com.eazybytes.accounts.repository;

import com.eazybytes.accounts.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerEntity,Long> {

    Optional<CustomerEntity> findByMobileNumber(String mobileNumber);

}
