package moller.solar.solarpersistence.configuration;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "moller.solar.solarpersistence.persistence")
public class PersistenceConfiguration {
}
