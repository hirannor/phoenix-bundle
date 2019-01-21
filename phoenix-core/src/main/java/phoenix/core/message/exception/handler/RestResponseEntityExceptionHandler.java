package phoenix.core.message.exception.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import phoenix.core.message.exception.MessageAwareException;
import phoenix.core.message.type.Message;

/**
 * @author mate.karolyi
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = MessageAwareException.class)
    public final ResponseEntity<Message> handle(Exception ex, WebRequest request) throws Exception {
        return new ResponseEntity<Message>(new Message(false, ex.getMessage()), new HttpHeaders(), HttpStatus.CONFLICT);
    }
}