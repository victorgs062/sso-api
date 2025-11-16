package br.com.mtashop.sso_api.exception;

import br.com.mtashop.sso_api.exception.auth.EmailJaExisteException;
import br.com.mtashop.sso_api.exception.usuario.UsuarioNaoEncontradoException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailJaExisteException.class)
    public ResponseEntity<?> emailJaExiste(EmailJaExisteException e) {
        return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<?> usuarioNaoEncontrado(UsuarioNaoEncontradoException e) {
        return ResponseEntity.status(404).body(Map.of("erro", e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> general(Exception e) {
        return ResponseEntity.internalServerError().body(Map.of("erro", e.getMessage()));
    }
}