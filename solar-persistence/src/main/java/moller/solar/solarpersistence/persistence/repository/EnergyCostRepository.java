package moller.solar.solarpersistence.persistence.repository;

import moller.solar.solarpersistence.persistence.entity.EnergyCostEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnergyCostRepository extends JpaRepository<EnergyCostEntryEntity, Integer> {

}
