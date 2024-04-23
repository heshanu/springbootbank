package com.eazybyte.cards.Cards.service.Impl;
import com.eazybyte.cards.Cards.constraint.CardsConstants;
import com.eazybyte.cards.Cards.dto.CardsDTO;
import com.eazybyte.cards.Cards.entity.CardEntity;
import com.eazybyte.cards.Cards.exception.CardAlreadyExistsException;
import com.eazybyte.cards.Cards.exception.ResourceNotFoundException;
import com.eazybyte.cards.Cards.mapper.CardsMapper;
import com.eazybyte.cards.Cards.repository.CardsRepository;
import com.eazybyte.cards.Cards.service.iCardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
@Service
@AllArgsConstructor
@Slf4j
public class CardServiceImpl implements iCardService {

    @Autowired
    private CardsRepository cardsRepository;

    @Override
    public void createCard(String mobileNumber) {
        log.info("Inside the createCard method");
        Optional<CardEntity> optionalCards= cardsRepository.findByMobileNumber(mobileNumber);
        if(optionalCards.isPresent()){
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber "+mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));
        log.info("create card for {}",mobileNumber);
    }


    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new card details
     */
    private CardEntity createNewCard(String mobileNumber) {
        log.info("Inside the create new Card");
        CardEntity newCard = new CardEntity();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        log.info("new card created",newCard);
        return newCard;
    }

    /**
     *
     * @param mobileNumber - Input mobile Number
     * @return Card Details based on a given mobileNumber
     */
    @Override
    public CardsDTO fetchCard(String mobileNumber) {
        log.info("Inside the fetchCard method");
        CardEntity cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        log.info("fetch data according to {}",mobileNumber);
        return CardsMapper.mapToCardsDto(cards, new CardsDTO());
    }

    /**
     *
     * @param cardsDto - CardsDto Object
     * @return boolean indicating if the update of card details is successful or not
     */
    @Override
    public boolean updateCard(CardsDTO cardsDto) {
        log.info("Inside the update Card method");
        CardEntity cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "CardNumber", cardsDto.getCardNumber()));
        CardsMapper.mapToCards(cardsDto, cards);
        cardsRepository.save(cards);
        log.info("update card successfully:",cardsDto);
        return  true;
    }


    /**
     * @param mobileNumber - Input MobileNumber
     * @return boolean indicating if the delete of card details is successful or not
     */
    @Override
    public boolean deleteCard(String mobileNumber) {
        log.info("Inside the delete card method");
        CardEntity cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        cardsRepository.deleteById(cards.getCardId());
        log.info("delete successfully {}",mobileNumber);
        return true;
    }

}
