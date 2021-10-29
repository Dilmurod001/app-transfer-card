package uz.pdp.apptransfercard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.apptransfercard.entity.Income;

public interface IncomeRepository extends JpaRepository<Income, Integer> {

}
