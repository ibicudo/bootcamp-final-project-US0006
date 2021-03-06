package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.config;


import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.exceptions.*;
import com.newrelic.api.agent.NewRelic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ApiError> noHandlerFoundException(HttpServletRequest req, NoHandlerFoundException ex) {
		ApiError apiError = new ApiError("route_not_found", String.format("Route %s not found", req.getRequestURI()), HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(apiError.getStatus())
				.body(apiError);
	}

	@ExceptionHandler(value = { ApiException.class })
	protected ResponseEntity<ApiError> handleApiException(ApiException e) {
		Integer statusCode = e.getStatusCode();
		boolean expected = HttpStatus.INTERNAL_SERVER_ERROR.value() > statusCode;
		NewRelic.noticeError(e, expected);
		if (expected) {
			LOGGER.warn("Internal Api warn. Status Code: " + statusCode, e);
		} else {
			LOGGER.error("Internal Api error. Status Code: " + statusCode, e);
		}

		ApiError apiError = new ApiError(e.getCode(), e.getDescription(), statusCode);
		return ResponseEntity.status(apiError.getStatus())
				.body(apiError);
	}

	@ExceptionHandler(value = { Exception.class })
	protected ResponseEntity<ApiError> handleUnknownException(Exception e) {
		LOGGER.error("Internal error", e);
		NewRelic.noticeError(e);

		ApiError apiError = new ApiError("internal_error", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value());
		return ResponseEntity.status(apiError.getStatus())
				.body(apiError);
	}

	@ExceptionHandler(value = { HttpMessageNotReadableException.class })
	protected ResponseEntity<ApiError> handleBadFormattedJSONException(Exception e) {
		LOGGER.error("Bad JSON", e);
		NewRelic.noticeError(e);

		ApiError apiError = new ApiError("internal_error", "The JSON body contains syntax error", HttpStatus.BAD_REQUEST.value());
		return ResponseEntity.status(apiError.getStatus())
				.body(apiError);
	}


	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public List<ValidationError> handleException(MethodArgumentNotValidException ex) {
		return ex.getBindingResult().getAllErrors()
				.stream()
				.map(this::mapError)
				.collect(Collectors.toList());
	}

	private ValidationError mapError(ObjectError objectError) {
		if (objectError instanceof FieldError) {
			return new ValidationError(((FieldError) objectError).getField(),
					objectError.getDefaultMessage());
		}
		return new ValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({
			BadRequestException.class
	})
	@ResponseBody
	public ApiError badRequest(Exception ex) {
		LOGGER.info("executing exception handler (REST)");
		return new ApiError(
				ex.getClass().getName(),
				ex.getMessage(),
				HttpStatus.BAD_REQUEST.value()
		);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({
			NotFoundException.class
	})
	@ResponseBody
	public ApiError notFoundRequest(Exception ex) {
		return new ApiError(
				ex.getClass().getName(),
				ex.getMessage(),
				HttpStatus.NOT_FOUND.value()
		);

	}
}
