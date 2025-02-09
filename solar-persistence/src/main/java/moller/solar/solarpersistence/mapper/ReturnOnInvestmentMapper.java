package moller.solar.solarpersistence.mapper;

import moller.openapi.persistence.solar.model.ReturnOnInvestmentEntry;
import moller.solar.solarpersistence.persistence.entity.ReturnOnInvestmentEntryEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", config = MapstructConfig.class)
public interface ReturnOnInvestmentMapper {

    List<ReturnOnInvestmentEntry> map(List<ReturnOnInvestmentEntryEntity> allSortedByDate);

    ReturnOnInvestmentEntryEntity map(ReturnOnInvestmentEntry returnOnInvestmentEntry);

    ReturnOnInvestmentEntry map(ReturnOnInvestmentEntryEntity returnOnInvestmentEntryEntity);
}
