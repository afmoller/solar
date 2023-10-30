package moller.solar.solarbackend;

import moller.solar.solarbackend.persistence.DataExportRepository;
import moller.solar.solarbackend.persistence.DataExportEntry;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SolarBackendApplicationTests {

	private final DataExportRepository dataExportRepository;

	public SolarBackendApplicationTests(DataExportRepository dataExportRepository) {
		this.dataExportRepository = dataExportRepository;
	}

	@Test
	void contextLoads() {
		List<DataExportEntry> all = dataExportRepository.findAll();
		System.err.println(all.size());
	}
}
