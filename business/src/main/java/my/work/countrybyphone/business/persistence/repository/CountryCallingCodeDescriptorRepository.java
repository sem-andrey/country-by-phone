package my.work.countrybyphone.business.persistence.repository;

import my.work.countrybyphone.business.persistence.entity.CountryCallingCodeDescriptor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryCallingCodeDescriptorRepository extends ReadOnlyRepository<CountryCallingCodeDescriptor, String> {

	@Query(
		value = "SELECT * FROM country_phone.country_calling_code_descriptor d " +
			"WHERE :phoneNumber BETWEEN d.code AND d.code || '9999999999999' " +
			"ORDER BY code DESC " +
			"LIMIT 2",
		nativeQuery = true)
	List<CountryCallingCodeDescriptor> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
