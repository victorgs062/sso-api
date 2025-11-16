package br.com.mtashop.sso_api.controller;

import br.com.mtashop.sso_api.dto.usuario.UsuarioDTO;
import br.com.mtashop.sso_api.security.user.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Operation(description = "Retorna os dados do usuário autenticado")
    @GetMapping("/perfil")
    public UsuarioDTO meuPerfil(Authentication auth) {
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        var u = user.getUsuario();
        return new UsuarioDTO(u.getPublicId(), u.getNome(), u.getEmail(), u.getPermissao().name(), u.getStatus());
    }
}