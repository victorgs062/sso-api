package br.com.mtashop.sso_api.controller;

import br.com.mtashop.sso_api.dto.usuario.UsuarioDTO;
import br.com.mtashop.sso_api.security.user.CustomUserDetails;
import br.com.mtashop.sso_api.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Retorna os dados do usuário autenticado")
    @GetMapping("/perfil")
    public UsuarioDTO meuPerfil(Authentication auth) {
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        var u = user.getUsuario();
        return new UsuarioDTO(u.getPublicId(), u.getNome(), u.getEmail(), u.getPermissao().name(), u.getStatus());
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'DESENVOLVEDOR', 'USUARIO')")
    @Operation(summary = "Lista todos os usuários (ADMINISTRADOR ou DESENVOLVEDOR)")
    @GetMapping
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioService.listarUsuarios()
                .stream()
                .map(u -> new UsuarioDTO(
                        u.getPublicId(),
                        u.getNome(),
                        u.getEmail(),
                        u.getPermissao().name(),
                        u.getStatus()
                ))
                .toList();
    }
}