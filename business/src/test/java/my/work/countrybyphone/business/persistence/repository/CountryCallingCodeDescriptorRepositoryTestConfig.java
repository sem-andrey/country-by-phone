package my.work.countrybyphone.business.persistence.repository;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@ComponentScan(value = "my.work.countrybyphone.business.persistence",
	excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
@EnableJpaRepositories(basePackages = {"my.work.countrybyphone.business.persistence.repository"})
@EntityScan("my.work.countrybyphone.business.persistence.entity")
class CountryCallingCodeDescriptorRepositoryTestConfig {
}
