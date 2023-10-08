package my.work.countrybyphone.restapi.resource;

import my.work.countrybyphone.business.service.CountryCallingCodeDescriptorDto;
import my.work.countrybyphone.business.service.CountryCallingCodeDescriptorService;
import my.work.countrybyphone.restapi.exception.handling.ExceptionHandlingAdvice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static my.work.countrybyphone.restapi.resource.CountryCallingCodeDescriptorResource.PHONE_NUMBER_PARAM;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CountryCallingCodeDescriptorResource.class)
@ContextConfiguration(classes = {CountryCallingCodeDescriptorResource.class,
	CountryCallingCodeDescriptorService.class,
	ExceptionHandlingAdvice.class}
)
public class CountryCallingCodeDescriptorResourceMvcTest {

	private static final String RESOLVABLE_PHONE_NUMBER = "77112227231";

	private static final String UNRESOLVABLE_PHONE_NUMBER = "999999999999999";

	private static final CountryCallingCodeDescriptorDto RETURNED_DTO = CountryCallingCodeDescriptorDto.newBuilder()
		.withCallingCode("77")
		.withCountryName("Kazakhstan")
		.build();

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CountryCallingCodeDescriptorService countryCallingCodeDescriptorService;

	@Test
	public void shouldReturnCountryCallingCodeDescriptor() throws Exception {
		//Given
		when(countryCallingCodeDescriptorService.findByPhoneNumber(RESOLVABLE_PHONE_NUMBER)).thenReturn(Optional.of(RETURNED_DTO));
		String requestedUrl = new StringBuilder(CountryCallingCodeDescriptorResource.RESOURCE_ENDPOINT)
			.append("?")
			.append(PHONE_NUMBER_PARAM).append("=").append(RESOLVABLE_PHONE_NUMBER)
			.toString();

		//When & Then
		mockMvc.perform(get(requestedUrl))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.callingCode").value(RETURNED_DTO.callingCode()))
			.andExpect(jsonPath("$.countryName").value(RETURNED_DTO.countryName()));

		verify(countryCallingCodeDescriptorService).findByPhoneNumber(RESOLVABLE_PHONE_NUMBER);
	}

	@Test
	void shouldReturnNotFoundWhenCountryCallingCodeDescriptorNotFound() throws Exception {
		//Given
		when(countryCallingCodeDescriptorService.findByPhoneNumber(UNRESOLVABLE_PHONE_NUMBER)).thenReturn(Optional.empty());
		String requestedUrl = new StringBuilder(CountryCallingCodeDescriptorResource.RESOURCE_ENDPOINT)
			.append("?")
			.append(PHONE_NUMBER_PARAM).append("=").append(UNRESOLVABLE_PHONE_NUMBER)
			.toString();

		//When & Then
		mockMvc.perform(get(requestedUrl))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.error").value(containsString(UNRESOLVABLE_PHONE_NUMBER)));

		verify(countryCallingCodeDescriptorService).findByPhoneNumber(UNRESOLVABLE_PHONE_NUMBER);
	}

	@Test
	void shouldReturnStatusCode500WhenUnexpectedExceptionIsThrown() throws Exception {
		//Given
		String unexpectedExceptionMessage = "Something went wrong";
		when(countryCallingCodeDescriptorService.findByPhoneNumber(anyString())).thenThrow(new RuntimeException(unexpectedExceptionMessage));
		String requestedUrl = new StringBuilder(CountryCallingCodeDescriptorResource.RESOURCE_ENDPOINT)
			.append("?")
			.append(PHONE_NUMBER_PARAM).append("=").append(RESOLVABLE_PHONE_NUMBER)
			.toString();

		//When & Then
		mockMvc.perform(get(requestedUrl))
			.andDo(print())
			.andExpect(status().isInternalServerError())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.error").value(not(containsString(unexpectedExceptionMessage))))
			.andExpect(jsonPath("$.error").value(containsString(INTERNAL_SERVER_ERROR.getReasonPhrase())));

		verify(countryCallingCodeDescriptorService).findByPhoneNumber(RESOLVABLE_PHONE_NUMBER);
	}
}
