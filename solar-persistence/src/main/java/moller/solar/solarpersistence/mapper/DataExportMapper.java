package moller.solar.solarpersistence.mapper;

import moller.solar.solarpersistence.persistence.entity.DataExportEntryEntity;
import moller.solarpersistence.openapi.model.DataExportEntry;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DataExportMapper {
    DataExportEntry mapEntryToEntity(DataExportEntryEntity dataExportEntryEntity);

    List<DataExportEntry> mapEntityToEntry(List<DataExportEntryEntity> dataExportEntryEntity);

    List<DataExportEntryEntity> mapEntryToEntity(List<DataExportEntry> dateExportEntries);

    DataExportEntryEntity map(DataExportEntry dataExportEntry);
}
