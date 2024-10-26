package moller.solar.solarpersistence.persistence.repository;

import moller.solar.solarpersistence.persistence.entity.ReturnOnInvestmentEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnOnInvestmentRepository extends JpaRepository<ReturnOnInvestmentEntryEntity, Integer> {

}
