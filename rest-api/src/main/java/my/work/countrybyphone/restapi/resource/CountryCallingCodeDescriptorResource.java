package my.work.countrybyphone.restapi.resource;

import my.work.countrybyphone.business.common.ValidationException;
import my.work.countrybyphone.business.service.CountryCallingCodeDescriptorDto;
import my.work.countrybyphone.business.service.CountryCallingCodeDescriptorService;
import my.work.countrybyphone.restapi.exception.ResourceNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.apache.commons.lang3.StringUtils.isBlank;

@RestController
@RequestMapping(path = CountryCallingCodeDescriptorResource.RESOURCE_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
public class CountryCallingCodeDescriptorResource {
	static final String RESOURCE_ENDPOINT = "/api/v1/country-calling-code-descriptors";

	static final String PHONE_NUMBER_PARAM = "phoneNumber";

	static final String PHONE_NUMBER_IS_BLANK = "%s parameter is blank".formatted(PHONE_NUMBER_PARAM);

	static final String PHONE_NUMBER_REQUIRES_AT_LEAST_2_DIGITS = "%s parameter requires at least 2 digits".formatted(PHONE_NUMBER_PARAM);

	private final CountryCallingCodeDescriptorService countryCallingCodeDescriptorService;

	@Autowired
	public CountryCallingCodeDescriptorResource(CountryCallingCodeDescriptorService countryCallingCodeDescriptorService) {
		this.countryCallingCodeDescriptorService = countryCallingCodeDescriptorService;
	}

	@GetMapping
	public CountryCallingCodeDescriptorDto findCountryCallingInfoItemByPhoneNumber(@RequestParam(name = PHONE_NUMBER_PARAM) String phoneNumber) {
		validatePhoneNumber(phoneNumber);
		return countryCallingCodeDescriptorService.findByPhoneNumber(phoneNumber)
			.orElseThrow(() -> new ResourceNotFoundException("Country not found by phone number: %s".formatted(phoneNumber)));
	}

	private void validatePhoneNumber(String phoneNumber) {
		if (isBlank(phoneNumber)) {
			throw new ValidationException(PHONE_NUMBER_IS_BLANK);
		}

		if (StringUtils.getDigits(phoneNumber).length() < 2) {
			throw new ValidationException(PHONE_NUMBER_REQUIRES_AT_LEAST_2_DIGITS);
		}
	}
}
