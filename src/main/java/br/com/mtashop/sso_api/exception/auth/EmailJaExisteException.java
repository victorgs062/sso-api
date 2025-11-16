package br.com.mtashop.sso_api.exception.auth;

public class EmailJaExisteException extends RuntimeException {
    public EmailJaExisteException(String msg) { super(msg); }
}