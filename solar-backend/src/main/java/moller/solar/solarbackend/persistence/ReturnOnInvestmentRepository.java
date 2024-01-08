package moller.solar.solarbackend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnOnInvestmentRepository extends JpaRepository<ReturnOnInvestmentEntry, Integer> {

}
