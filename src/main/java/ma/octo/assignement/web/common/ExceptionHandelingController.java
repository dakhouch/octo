package ma.octo.assignement.web.common;

import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.octo.assignement.exceptions.TransactionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionHandelingController {

    @ExceptionHandler(SoldeDisponibleInsuffisantException.class)
    public ResponseEntity<String> handleSoldeDisponibleInsuffisantException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), null, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CompteNonExistantException.class)
    public ResponseEntity<String> handleCompteNonExistantException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), null, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(TransactionException.class)
    public  ResponseEntity<String> handleTransactionException(Exception ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }
}
