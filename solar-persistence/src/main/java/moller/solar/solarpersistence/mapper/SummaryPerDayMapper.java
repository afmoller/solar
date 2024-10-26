package moller.solar.solarpersistence.mapper;

import moller.solar.solarpersistence.persistence.entity.SummaryPerDayEntryEntity;
import moller.solarpersistence.openapi.model.SummaryPerDayEntry;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SummaryPerDayMapper {

    List<SummaryPerDayEntryEntity> mapEntriesToEntities(List<SummaryPerDayEntry> summaryPerDayEntries);

    SummaryPerDayEntryEntity map(SummaryPerDayEntry summaryPerDayEntry);

    SummaryPerDayEntry map(SummaryPerDayEntryEntity summaryPerDayEntryEntity);

    List<SummaryPerDayEntry> map(List<SummaryPerDayEntryEntity> allEntries);
}
