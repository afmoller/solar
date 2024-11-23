package moller.solar.solarpersistence.mapper;

import moller.solar.solarpersistence.persistence.entity.EnergyCostEntryEntity;
import moller.solarpersistence.openapi.model.EnergyCostEntry;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", config = MapstructConfig.class)
public interface EnergyCostMapper {

    List<EnergyCostEntry> map(List<EnergyCostEntryEntity> allSortedByDate);

    EnergyCostEntry map(EnergyCostEntryEntity energyCostEntryEntity);

    EnergyCostEntryEntity map(EnergyCostEntry energyCostEntry);
}
