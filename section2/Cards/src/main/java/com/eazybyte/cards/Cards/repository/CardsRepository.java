package com.eazybyte.cards.Cards.repository;

import com.eazybyte.cards.Cards.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardsRepository extends JpaRepository<CardEntity,Long> {
    Optional<CardEntity> findByMobileNumber(String mobileNumber);

    Optional<CardEntity> findByCardNumber(String cardNumber);
}
