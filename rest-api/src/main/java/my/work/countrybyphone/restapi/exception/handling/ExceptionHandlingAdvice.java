package my.work.countrybyphone.restapi.exception.handling;

import my.work.countrybyphone.business.common.ValidationException;
import my.work.countrybyphone.restapi.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ExceptionHandlingAdvice {

	private static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingAdvice.class);

	@ExceptionHandler({ResourceNotFoundException.class})
	@ResponseStatus(NOT_FOUND)
	public ResponseEntity<ErrorDto> handleResourceNotFoundException(ResourceNotFoundException ex) {
		return new ResponseEntity<>(new ErrorDto(ex.getMessage()), NOT_FOUND);
	}

	@ExceptionHandler({ValidationException.class})
	@ResponseStatus(BAD_REQUEST)
	public ResponseEntity<ErrorDto> handleValidationException(ValidationException ex) {
		return new ResponseEntity<>(new ErrorDto(ex.getMessage()), BAD_REQUEST);
	}

	@ExceptionHandler({MissingServletRequestParameterException.class})
	@ResponseStatus(BAD_REQUEST)
	public ResponseEntity<ErrorDto> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
		return new ResponseEntity<>(new ErrorDto(ex.getMessage()), BAD_REQUEST);
	}

	@ExceptionHandler({Exception.class})
	@ResponseStatus(INTERNAL_SERVER_ERROR)
	public ResponseEntity<ErrorDto> handleException(Exception ex) {
		LOGGER.error(ex.getMessage(), ex);
		return new ResponseEntity<>(new ErrorDto(INTERNAL_SERVER_ERROR.getReasonPhrase()), INTERNAL_SERVER_ERROR);
	}
}
