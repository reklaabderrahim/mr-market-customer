package fr.mr_market.mr_customer.controller.handler;

import com.github.dozermapper.core.converters.ConversionException;
import fr.mr_market.mr_customer.exception.CustomerNotFoundException;
import fr.mr_market.mr_market_customer.swagger.model.customer.Error;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    public static final String THIS_SHOULD_BE_APPLICATION_SPECIFIC = "Bad request arguments";
    public static final String THE_BODY_CANNOT_BE_CAST = "The body cannot be cast";

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    protected Error handleConflict(RuntimeException ex) {
        log.error(THIS_SHOULD_BE_APPLICATION_SPECIFIC, ex);
        Error error = new Error();
        error.setCode(HttpStatus.CONFLICT.value());
        error.setMessage(ex.getMessage());
        error.setDescription(THIS_SHOULD_BE_APPLICATION_SPECIFIC);
        return error;
    }

    @ExceptionHandler(value = {ConstraintViolationException.class, ConversionException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected Error handleConstraintViolation(RuntimeException ex) {
        log.error(THE_BODY_CANNOT_BE_CAST, ex);
        Error error = new Error();
        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(ex.getMessage());
        error.setDescription(THE_BODY_CANNOT_BE_CAST);
        return error;
    }

    @ExceptionHandler(value = {CustomerNotFoundException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected Error handleCustomerNotFoundException(RuntimeException ex) {
        log.error(ex.getMessage());
        Error error = new Error();
        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(ex.getMessage());
        return error;
    }
}
