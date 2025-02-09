package moller.solar.solarpersistence.mapper;

import moller.openapi.persistence.solar.model.DataExportEntry;
import moller.solar.solarpersistence.persistence.entity.DataExportEntryEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", config = MapstructConfig.class)
public interface DataExportMapper {
    DataExportEntry mapEntryToEntity(DataExportEntryEntity dataExportEntryEntity);

    List<DataExportEntry> mapEntityToEntry(List<DataExportEntryEntity> dataExportEntryEntity);

    List<DataExportEntryEntity> mapEntryToEntity(List<DataExportEntry> dateExportEntries);

    DataExportEntryEntity map(DataExportEntry dataExportEntry);
}
