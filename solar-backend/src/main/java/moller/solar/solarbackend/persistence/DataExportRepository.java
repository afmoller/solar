package moller.solar.solarbackend.persistence;

import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DataExportRepository extends JpaRepository<DataExportEntry, Integer> {
    @Query(value = "SELECT d FROM DataExportEntry d WHERE d.timestampYear = :year AND d.timestampMonth = :month")
    @QueryHints(@QueryHint(name="org.hibernate.fetchSize", value="25000"))
    List<DataExportEntry> findByYearAndMonth(int year, int month);

    @Query(value = "SELECT d FROM DataExportEntry d WHERE d.timestamp between :fromDateTime AND :toDateTime ORDER BY d.timestamp")
    List<DataExportEntry> findByTimespan(LocalDateTime fromDateTime, LocalDateTime toDateTime);
}
