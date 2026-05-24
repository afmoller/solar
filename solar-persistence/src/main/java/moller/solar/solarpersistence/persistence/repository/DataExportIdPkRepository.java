package moller.solar.solarpersistence.persistence.repository;

import jakarta.persistence.QueryHint;
import moller.solar.solarpersistence.persistence.entity.DataExportIdPkEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DataExportIdPkRepository extends JpaRepository<DataExportIdPkEntryEntity, Integer> {
    @Query(value = "SELECT d FROM DataExportIdPkEntryEntity d WHERE d.timestampYear = :year AND d.timestampMonth = :month")
    @QueryHints(@QueryHint(name="org.hibernate.fetchSize", value="25000"))
    List<DataExportIdPkEntryEntity> findByYearAndMonth(int year, int month);

    @Query(value = "SELECT d FROM DataExportIdPkEntryEntity d WHERE d.timestamp between :fromDateTime AND :toDateTime ORDER BY d.timestamp")
    List<DataExportIdPkEntryEntity> findByTimespanOrderedByTimestamp(LocalDateTime fromDateTime, LocalDateTime toDateTime);

    @Query(value = "SELECT d FROM DataExportIdPkEntryEntity d WHERE d.iid = :iid")
    Optional<DataExportIdPkEntryEntity> findByIid(int iid);
}
