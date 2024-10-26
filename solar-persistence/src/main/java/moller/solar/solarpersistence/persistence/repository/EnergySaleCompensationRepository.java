package moller.solar.solarpersistence.persistence.repository;

import moller.solar.solarpersistence.persistence.entity.EnergySaleCompensationEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnergySaleCompensationRepository extends JpaRepository<EnergySaleCompensationEntryEntity, Integer> {

}
