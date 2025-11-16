package br.com.mtashop.sso_api.dto.auth;

public record AuthResponseDTO(String token,
                              Integer publicId,
                              String nome,
                              String permissao) {
}
