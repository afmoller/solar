package moller.solar.solarpersistence.mapper;

import moller.openapi.persistence.solar.model.SummaryPerDayEntry;
import moller.solar.solarpersistence.persistence.entity.SummaryPerDayEntryEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", config = MapstructConfig.class)
public interface SummaryPerDayMapper {

    List<SummaryPerDayEntryEntity> mapEntriesToEntities(List<SummaryPerDayEntry> summaryPerDayEntries);

    SummaryPerDayEntryEntity map(SummaryPerDayEntry summaryPerDayEntry);

    SummaryPerDayEntry map(SummaryPerDayEntryEntity summaryPerDayEntryEntity);

    List<SummaryPerDayEntry> map(List<SummaryPerDayEntryEntity> allEntries);
}
