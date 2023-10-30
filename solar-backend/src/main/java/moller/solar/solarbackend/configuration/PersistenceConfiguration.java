package moller.solar.solarbackend.configuration;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "moller.solar.solarbackend.persistence")
public class PersistenceConfiguration {
}
