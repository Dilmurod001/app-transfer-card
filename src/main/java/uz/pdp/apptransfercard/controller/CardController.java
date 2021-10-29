package uz.pdp.apptransfercard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.apptransfercard.entity.Card;
import uz.pdp.apptransfercard.entity.Income;
import uz.pdp.apptransfercard.entity.Outcome;
import uz.pdp.apptransfercard.pyload.OutcomeDto;
import uz.pdp.apptransfercard.repository.CardRepository;
import uz.pdp.apptransfercard.repository.IncomeRepository;
import uz.pdp.apptransfercard.repository.OutcomeRepository;
import uz.pdp.apptransfercard.security.JwtProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/card")
public class CardController {
    final
    JwtProvider jwtProvider;
    final
    CardRepository cardRepository;
    final
    OutcomeRepository outcomeRepository;
    final
    IncomeRepository incomeRepository;

    public CardController(JwtProvider jwtProvider, CardRepository cardRepository, OutcomeRepository outcomeRepository, IncomeRepository incomeRepository) {
        this.jwtProvider = jwtProvider;
        this.cardRepository = cardRepository;
        this.outcomeRepository = outcomeRepository;
        this.incomeRepository = incomeRepository;
    }

    @PostMapping
    public HttpEntity<?> addCard(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")) {
            token = token.substring(7);
            String userNameFromToken = jwtProvider.getUserNameFromToken(token);
            Card card = new Card();
            card.setUserName(userNameFromToken);
            card.setBalance(0d);
            String randomNum = UUID.randomUUID().toString();
            String substring = randomNum.substring(0, 4);
            card.setNumber(substring);
            card.setExpiredDate(new Date(System.currentTimeMillis() + (1000L * 86400 * 365 * 2)));
            cardRepository.save(card);

        }
        return ResponseEntity.ok("Karta Ochildi!");
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> outCome(HttpServletRequest request, @RequestBody OutcomeDto outcomeDto) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")) {
            token = token.substring(7);
            String userNameFromToken = jwtProvider.getUserNameFromToken(token);
            Optional<Card> byUserName = cardRepository.findByUserName(userNameFromToken);
            if (byUserName.get().getNumber().equals(outcomeDto.getFrom())) {
                //karta oziniki ekan
                Outcome outcome = new Outcome();
                outcome.setFromCard(cardRepository.findByNumber(outcomeDto.getFrom()).get());
                outcome.setToCard(cardRepository.findByNumber(outcomeDto.getTo()).get());
                //100000
                outcome.setAmount(outcomeDto.getAmount());
                outcome.setDate(new Date());

                double summa = outcomeDto.getAmount() - (outcomeDto.getAmount() / 100);
                if (summa > byUserName.get().getBalance()) {
                    return ResponseEntity.ok("Cardda yetarlicha mablag yo'q");
                }
                byUserName.get().setBalance(byUserName.get().getBalance() - summa);
                cardRepository.save(byUserName.get());

                Optional<Card> toCard = cardRepository.findByNumber(outcomeDto.getTo());
                toCard.get().setBalance(toCard.get().getBalance() + outcomeDto.getAmount());
                cardRepository.save(toCard.get());

                Income income = new Income();
                income.setFromCard(cardRepository.findByNumber(outcomeDto.getTo()).get());
                income.setToCard(cardRepository.findByNumber(outcomeDto.getFrom()).get());
                income.setAmount(outcomeDto.getAmount());
                income.setDate(new Date());

                incomeRepository.save(income);
                outcomeRepository.save(outcome);
            }
        }
        return ResponseEntity.ok("Success!");
    }
}
