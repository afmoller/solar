package moller.solar.solarpersistence.persistence.repository;

import jakarta.persistence.QueryHint;
import moller.solar.solarpersistence.persistence.entity.DataExportEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DataExportRepository extends JpaRepository<DataExportEntryEntity, Integer> {
    @Query(value = "SELECT d FROM DataExportEntryEntity d WHERE d.timestampYear = :year AND d.timestampMonth = :month")
    @QueryHints(@QueryHint(name="org.hibernate.fetchSize", value="25000"))
    List<DataExportEntryEntity> findByYearAndMonth(int year, int month);

    @Query(value = "SELECT d FROM DataExportEntryEntity d WHERE d.timestamp between :fromDateTime AND :toDateTime ORDER BY d.timestamp")
    List<DataExportEntryEntity> findByTimespanOrderedByTimestamp(LocalDateTime fromDateTime, LocalDateTime toDateTime);
}
