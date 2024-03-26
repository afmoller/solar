package moller.solar.solarbackend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnergyCostRepository extends JpaRepository<EnergyCostEntry, Integer> {

}
