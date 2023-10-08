package my.work.countrybyphone.business.service;

import java.util.Optional;

public interface CountryCallingCodeDescriptorService {
	Optional<CountryCallingCodeDescriptorDto> findByPhoneNumber(String phoneNumber);
}
