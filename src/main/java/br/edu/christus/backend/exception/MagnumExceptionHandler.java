package br.edu.christus.backend.exception;

import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.util.stream.Collectors;

// Aplica-se somente às rotas de Magnum; mantém o tratamento da Ercília intacto.
@Order(-1)
@RestControllerAdvice(basePackages = {
        "br.edu.christus.backend.controller.Author",
        "br.edu.christus.backend.controller.Loan",
        "br.edu.christus.backend.controller.Review"
})
public class MagnumExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validarCampos(MethodArgumentNotValidException exception) {
        String mensagem = exception.getBindingResult().getFieldErrors().stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .sorted().collect(Collectors.joining("; "));
        return ResponseEntity.badRequest().body(new ErrorResponse(mensagem));
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> dadosInvalidos(Exception exception) {
        return ResponseEntity.badRequest().body(new ErrorResponse(
                "Dados inválidos. Confira os tipos dos campos, o ID numérico e as datas no formato AAAA-MM-DD."));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> conflito(DataIntegrityViolationException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("Registro duplicado ou vinculado a outros registros."));
    }

    public record ErrorResponse(String message) {}
}
