package br.com.mtashop.sso_api.dto.usuario;

public record UsuarioDTO(Integer publicId,
                         String nome,
                         String email,
                         String permissao,
                         Boolean status) {
}
