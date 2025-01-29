package prosky.complexversion.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DivisionByZeroException extends RuntimeException {

    public DivisionByZeroException(String message) {
        super(message);
    }

}
