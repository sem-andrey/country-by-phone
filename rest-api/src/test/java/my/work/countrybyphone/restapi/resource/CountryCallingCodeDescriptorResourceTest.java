package my.work.countrybyphone.restapi.resource;

import my.work.countrybyphone.business.common.ValidationException;
import my.work.countrybyphone.business.service.CountryCallingCodeDescriptorDto;
import my.work.countrybyphone.business.service.CountryCallingCodeDescriptorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static my.work.countrybyphone.restapi.resource.CountryCallingCodeDescriptorResource.PHONE_NUMBER_PARAM;
import static my.work.countrybyphone.restapi.resource.CountryCallingCodeDescriptorResource.PHONE_NUMBER_REQUIRES_AT_LEAST_2_DIGITS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryCallingCodeDescriptorResourceTest {
	@Mock
	private CountryCallingCodeDescriptorService countryCallingCodeDescriptorService;

	@Test
	void shouldReturnResponseWhenRequestIsValid() {
		//Given
		String passedPhoneNumber = "11";
		CountryCallingCodeDescriptorDto expectedDto = CountryCallingCodeDescriptorDto.newBuilder()
			.withCallingCode("1")
			.withCountryName("United States or Canada")
			.build();
		when(countryCallingCodeDescriptorService.findByPhoneNumber(passedPhoneNumber)).thenReturn(Optional.of(expectedDto));
		CountryCallingCodeDescriptorResource resource = new CountryCallingCodeDescriptorResource(countryCallingCodeDescriptorService);

		//When
		CountryCallingCodeDescriptorDto returnedDto = resource.findCountryCallingInfoItemByPhoneNumber("11");

		//Then
		assertThat(returnedDto, is(expectedDto));
	}

	@Test
	void shouldThrowValidationExceptionWhenPhoneNumberIsNull() {
		//Given
		CountryCallingCodeDescriptorResource resource = new CountryCallingCodeDescriptorResource(countryCallingCodeDescriptorService);

		//When
		ValidationException exception = assertThrows(ValidationException.class, () -> resource.findCountryCallingInfoItemByPhoneNumber(null));

		//Then
		assertThat(exception.getMessage(), containsString(PHONE_NUMBER_PARAM));
		assertThat(exception.getMessage(), containsString("is blank"));
	}

	@Test
	void shouldThrowValidationExceptionWhenPhoneNumberIsBlank() {
		//Given
		CountryCallingCodeDescriptorResource resource = new CountryCallingCodeDescriptorResource(countryCallingCodeDescriptorService);

		//When
		ValidationException exception = assertThrows(ValidationException.class, () -> resource.findCountryCallingInfoItemByPhoneNumber(" "));

		//Then
		assertThat(exception.getMessage(), containsString(PHONE_NUMBER_PARAM));
		assertThat(exception.getMessage(), containsString("is blank"));
	}

	@Test
	void shouldThrowValidationExceptionWhenPhoneNumberContainsLess2Digits() {
		//Given
		CountryCallingCodeDescriptorResource resource = new CountryCallingCodeDescriptorResource(countryCallingCodeDescriptorService);

		//When
		ValidationException exception = assertThrows(ValidationException.class, () -> resource.findCountryCallingInfoItemByPhoneNumber("1"));

		//Then
		assertThat(exception.getMessage(), equalTo(PHONE_NUMBER_REQUIRES_AT_LEAST_2_DIGITS));
	}
}
