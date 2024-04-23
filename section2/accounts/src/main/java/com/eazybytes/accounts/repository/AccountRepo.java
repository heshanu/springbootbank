package com.eazybytes.accounts.repository;

import com.eazybytes.accounts.entities.AccountEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<AccountEntity,Long> {

    Optional<AccountEntity> findByCustomerId(Long customerId);

    @Transactional
    @Modifying
    @Query("DELETE FROM AccountEntity a WHERE a.customerId = :customerId")
    void deleteByCustomerId(Long customerId);
}
