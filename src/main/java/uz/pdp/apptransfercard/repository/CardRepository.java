package uz.pdp.apptransfercard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.apptransfercard.entity.Card;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Integer> {

    Optional<Card> findByUserName(String name);

    Optional<Card> findByNumber(String card);
}
