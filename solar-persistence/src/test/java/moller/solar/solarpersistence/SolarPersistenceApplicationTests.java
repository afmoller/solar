package moller.solar.solarpersistence;

import moller.solar.solarpersistence.persistence.repository.DataExportRepository;
import moller.solar.solarpersistence.persistence.entity.DataExportEntryEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SolarPersistenceApplicationTests {

	private final DataExportRepository dataExportRepository;

	public SolarPersistenceApplicationTests(DataExportRepository dataExportRepository) {
		this.dataExportRepository = dataExportRepository;
	}

	@Test
	void contextLoads() {
		List<DataExportEntryEntity> all = dataExportRepository.findAll();
		System.err.println(all.size());
	}
}
