package moller.solar.solarpersistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SolarPersistenceApplication {

	static void main(String[] args) {
		SpringApplication.run(SolarPersistenceApplication.class, args);
	}
}
