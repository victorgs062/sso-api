package br.com.mtashop.sso_api.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequestDTO(@Email(message = "E-mail inválido!")
                             @NotBlank(message = "O e-mail é obrigatório!")
                             @Size(max = 150, message = "E-mail maior do que permitido!")
                             String email,
                             @Size(max = 150, message = "Senha maior do que permitida!")
                             @NotBlank(message = "Digite a senha!")
                             String senha) {
}
