package my.work.countrybyphone.business.service.impl;

import my.work.countrybyphone.business.persistence.entity.CountryCallingCodeDescriptor;
import my.work.countrybyphone.business.persistence.repository.CountryCallingCodeDescriptorRepository;
import my.work.countrybyphone.business.service.CountryCallingCodeDescriptorDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultCountryCallingCodeDescriptorServiceTest {

	private static final CountryCallingCodeDescriptor DESCRIPTOR_WITH_CALLING_CODE_77 = CountryCallingCodeDescriptor.makeByCodeAndCountry(
		"77", "Kazakhstan"
	);

	private static final CountryCallingCodeDescriptor DESCRIPTOR_WITH_CALLING_CODE_7 = CountryCallingCodeDescriptor.makeByCodeAndCountry(
		"7", "Russia"
	);

	@Mock
	private CountryCallingCodeDescriptorRepository countryCallingCodeDescriptorRepo;

	@Test
	void shouldInvokeCountryCallingCodeDescriptorRepo() {
		//Given
		String phoneNumber = "771234567";
		when(countryCallingCodeDescriptorRepo.findByPhoneNumber(phoneNumber)).thenReturn(List.of(DESCRIPTOR_WITH_CALLING_CODE_77, DESCRIPTOR_WITH_CALLING_CODE_7));
		DefaultCountryCallingCodeDescriptorService service = new DefaultCountryCallingCodeDescriptorService(countryCallingCodeDescriptorRepo);

		//When
		CountryCallingCodeDescriptorDto countryCallingCodeDescriptorDto = service.findByPhoneNumber(phoneNumber).get();

		//Then
		verify(countryCallingCodeDescriptorRepo).findByPhoneNumber(phoneNumber);
		assertThat(countryCallingCodeDescriptorDto.callingCode(), equalTo(DESCRIPTOR_WITH_CALLING_CODE_77.getCode()));
		assertThat(countryCallingCodeDescriptorDto.countryName(), equalTo(DESCRIPTOR_WITH_CALLING_CODE_77.getCountry()));
	}

	@Test
	void shouldFilterDescriptorsHavingCodeEqualsPhoneNumber() {
		//Given
		String phoneNumber = "77";
		when(countryCallingCodeDescriptorRepo.findByPhoneNumber(phoneNumber)).thenReturn(List.of(DESCRIPTOR_WITH_CALLING_CODE_77, DESCRIPTOR_WITH_CALLING_CODE_7));
		DefaultCountryCallingCodeDescriptorService service = new DefaultCountryCallingCodeDescriptorService(countryCallingCodeDescriptorRepo);

		//When
		CountryCallingCodeDescriptorDto countryCallingCodeDescriptorDto = service.findByPhoneNumber(phoneNumber).get();

		//Then
		verify(countryCallingCodeDescriptorRepo).findByPhoneNumber(phoneNumber);
		assertThat(countryCallingCodeDescriptorDto.callingCode(), equalTo(DESCRIPTOR_WITH_CALLING_CODE_7.getCode()));
		assertThat(countryCallingCodeDescriptorDto.countryName(), equalTo(DESCRIPTOR_WITH_CALLING_CODE_7.getCountry()));
	}

	@Test
	void shouldReturnEmptyOptionalWhenRepoReturnsEmptyList() {
		//Given
		String phoneNumber = "99999";
		when(countryCallingCodeDescriptorRepo.findByPhoneNumber(phoneNumber)).thenReturn(List.of());
		DefaultCountryCallingCodeDescriptorService service = new DefaultCountryCallingCodeDescriptorService(countryCallingCodeDescriptorRepo);

		//When
		Optional<CountryCallingCodeDescriptorDto> countryCallingCodeDescriptorDtoOptional = service.findByPhoneNumber(phoneNumber);

		//Then
		verify(countryCallingCodeDescriptorRepo).findByPhoneNumber(phoneNumber);
		assertThat(countryCallingCodeDescriptorDtoOptional.isEmpty(), is(true));
	}
}
