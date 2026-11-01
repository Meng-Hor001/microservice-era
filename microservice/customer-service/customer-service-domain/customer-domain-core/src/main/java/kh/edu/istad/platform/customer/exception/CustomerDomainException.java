package kh.edu.istad.platform.customer.exception;

import kh.edu.istad.common.domain.exception.DomainException;

public class CustomerDomainException extends DomainException {
    public CustomerDomainException(String message) {
        super(message);
    }

    public CustomerDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
