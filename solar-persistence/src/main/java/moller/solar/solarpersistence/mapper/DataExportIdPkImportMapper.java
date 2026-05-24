package moller.solar.solarpersistence.mapper;

import moller.solar.solarpersistence.persistence.entity.DataExportEntryEntity;
import moller.solar.solarpersistence.persistence.entity.DataExportIdPkEntryEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DataExportIdPkImportMapper {

    List<DataExportIdPkEntryEntity> mapEntryToEntity(List<DataExportEntryEntity> dateExportEntries);


}
