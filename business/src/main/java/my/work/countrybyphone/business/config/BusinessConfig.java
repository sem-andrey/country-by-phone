package my.work.countrybyphone.business.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "my.work.countrybyphone.business",
	excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
@EnableJpaRepositories(basePackages = {"my.work.countrybyphone.business.persistence.repository"})
@EntityScan("my.work.countrybyphone.business.persistence.entity")
public class BusinessConfig {
}
