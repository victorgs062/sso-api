package br.com.mtashop.sso_api.exception.usuario;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(String msg) { super(msg); }
}