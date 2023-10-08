package my.work.countrybyphone.business.service.impl;

import my.work.countrybyphone.business.persistence.entity.CountryCallingCodeDescriptor;
import my.work.countrybyphone.business.persistence.repository.CountryCallingCodeDescriptorRepository;
import my.work.countrybyphone.business.service.CountryCallingCodeDescriptorDto;
import my.work.countrybyphone.business.service.CountryCallingCodeDescriptorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
class DefaultCountryCallingCodeDescriptorService implements CountryCallingCodeDescriptorService {

	private final CountryCallingCodeDescriptorRepository countryCallingCodeDescriptorRepo;

	@Autowired
	DefaultCountryCallingCodeDescriptorService(CountryCallingCodeDescriptorRepository countryCallingCodeDescriptorRepo) {
		this.countryCallingCodeDescriptorRepo = countryCallingCodeDescriptorRepo;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<CountryCallingCodeDescriptorDto> findByPhoneNumber(String phoneNumber) {
		String clearedPhoneNumber = StringUtils.getDigits(phoneNumber);
		return countryCallingCodeDescriptorRepo.findByPhoneNumber(clearedPhoneNumber)
			.stream()
			.filter(codeDescriptor -> codeDescriptor.getCode().length() < phoneNumber.length())
			.findFirst()
			.map(this::makeFrom);
	}

	private CountryCallingCodeDescriptorDto makeFrom(CountryCallingCodeDescriptor countryCallingCodeDescriptor) {
		return CountryCallingCodeDescriptorDto.newBuilder()
			.withCallingCode(countryCallingCodeDescriptor.getCode())
			.withCountryName(countryCallingCodeDescriptor.getCountry())
			.build();
	}
}
