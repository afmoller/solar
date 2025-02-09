package moller.solar.solarpersistence.mapper;

import moller.openapi.persistence.solar.model.EnergyCostEntry;
import moller.solar.solarpersistence.persistence.entity.EnergyCostEntryEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", config = MapstructConfig.class)
public interface EnergyCostMapper {

    List<EnergyCostEntry> map(List<EnergyCostEntryEntity> allSortedByDate);

    EnergyCostEntry map(EnergyCostEntryEntity energyCostEntryEntity);

    EnergyCostEntryEntity map(EnergyCostEntry energyCostEntry);
}
