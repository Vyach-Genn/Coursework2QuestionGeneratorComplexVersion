package prosky.complexversion.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TooManyQuestionsRequestedException extends RuntimeException {

    public TooManyQuestionsRequestedException(String message) {
        super(message);
    }
}
