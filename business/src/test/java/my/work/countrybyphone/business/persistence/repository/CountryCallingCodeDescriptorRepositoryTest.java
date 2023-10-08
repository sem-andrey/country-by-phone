package my.work.countrybyphone.business.persistence.repository;

import my.work.countrybyphone.business.persistence.entity.CountryCallingCodeDescriptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(classes = CountryCallingCodeDescriptorRepositoryTestConfig.class)
@TestPropertySource(properties = {
	"spring.autoconfigure.exclude=my.work.countrybyphone.business.config.BusinessConfig",
	"spring.datasource.url=jdbc:h2:mem:test_db;MODE=PostgreSQL",
	"spring.datasource.driverClassName=org.h2.Driver",
	"spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect"
})
@Sql(scripts = {"/db/schema.sql", "/db/data.sql"})
class CountryCallingCodeDescriptorRepositoryTest {
	@Autowired
	private CountryCallingCodeDescriptorRepository countryCallingCodeDescriptorRepo;

	@Test
	void shouldFindAllRelevantCountryCallingCodeDescriptorByPhoneNumber() {
		//When
		List<CountryCallingCodeDescriptor> countryCallingCodeDescriptors = countryCallingCodeDescriptorRepo.findByPhoneNumber("77112227231");

		//Then
		assertThat(countryCallingCodeDescriptors, hasSize(2));
		assertThat(countryCallingCodeDescriptors.get(0).getCode(), equalTo("77"));
		assertThat(countryCallingCodeDescriptors.get(0).getCountry(), equalTo("Kazakhstan"));
		assertThat(countryCallingCodeDescriptors.get(1).getCode(), equalTo("7"));
		assertThat(countryCallingCodeDescriptors.get(1).getCountry(), equalTo("Russia"));
	}

	@Test
	void shouldFindRussiaCountryCallingCodeDescriptorByRussiaPhoneNumber() {
		//When
		List<CountryCallingCodeDescriptor> countryCallingCodeDescriptors = countryCallingCodeDescriptorRepo.findByPhoneNumber("71423423412");

		//Then
		assertThat(countryCallingCodeDescriptors, hasSize(1));
		assertThat(countryCallingCodeDescriptors.get(0).getCode(), equalTo("7"));
		assertThat(countryCallingCodeDescriptors.get(0).getCountry(), equalTo("Russia"));
	}
}
