package moller.solar.solarpersistence.mapper;

import moller.solar.solarpersistence.persistence.entity.EnergySaleCompensationEntryEntity;
import moller.solarpersistence.openapi.model.EnergySaleCompensationEntry;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", config = MapstructConfig.class)
public interface EnergySaleCompensationMapper {

    List<EnergySaleCompensationEntry> map(List<EnergySaleCompensationEntryEntity> allSortedByCompensationDate);

    EnergySaleCompensationEntryEntity map(EnergySaleCompensationEntry energySaleCompensationEntry);

    EnergySaleCompensationEntry map(EnergySaleCompensationEntryEntity savedEnergySaleCompensationEntryEntity);
}
