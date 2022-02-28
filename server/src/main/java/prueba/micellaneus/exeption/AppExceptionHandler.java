package prueba.micellaneus.exeption;


import prueba.micellaneus.dto.GenericDto;
import prueba.micellaneus.dto.MessageExceptionDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class AppExceptionHandler {


    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<?> handleException(BusinessException exception) {
        log.info("[{}] Exception BusinessException, mensaje: {}", Thread.currentThread().getId(), exception.getMessage());
        MessageExceptionDto exceptionDto = MessageExceptionDto.builder().build();
        exceptionDto.setData(exception.getMessage());

        return ResponseEntity.status(HttpStatus.OK).body(GenericDto.failed(exceptionDto));
    }
}
