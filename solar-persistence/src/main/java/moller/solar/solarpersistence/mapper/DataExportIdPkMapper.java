package moller.solar.solarpersistence.mapper;

import moller.openapi.persistence.solar.model.DataExportEntry;
import moller.solar.solarpersistence.persistence.entity.DataExportIdPkEntryEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", config = MapstructConfig.class)
public interface DataExportIdPkMapper {
    DataExportEntry mapEntryToEntity(DataExportIdPkEntryEntity dataExportEntryEntity);

    List<DataExportEntry> mapEntityToEntry(List<DataExportIdPkEntryEntity> dataExportEntryEntity);

    List<DataExportIdPkEntryEntity> mapEntryToEntity(List<DataExportEntry> dateExportEntries);

    DataExportIdPkEntryEntity map(DataExportEntry dataExportEntry);
}
