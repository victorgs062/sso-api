package br.com.mtashop.sso_api.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistrarUsuarioDTO(@NotBlank(message = "O nome é obrigatório!")
                                  @Size(max = 150, message = "Nome maior do que o permitido!")
                                  String nome,
                                  @Email(message = "E-mail inválido!")
                                  @NotBlank(message = "O e-mail é obrigatório!")
                                  @Size(max = 150, message = "E-mail maior do que permitido!")
                                  String email,
                                  @Size(max = 255, message = "Senha maior do que permitida!")
                                  @NotBlank(message = "Digite a senha!")
                                  String senha) {
}
