package fr.mr_market.mr_customer.exception;

import org.apache.commons.lang3.StringUtils;

public class CustomerNotFoundException extends GenericException {

    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException(String message, String param) {
        super(StringUtils.replace(message, "{}", param));
    }
}
