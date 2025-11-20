package br.com.mtashop.sso_api.controller;

import br.com.mtashop.sso_api.dto.auth.AuthRequestDTO;
import br.com.mtashop.sso_api.dto.auth.AuthResponseDTO;
import br.com.mtashop.sso_api.dto.usuario.RegistrarUsuarioDTO;
import br.com.mtashop.sso_api.dto.usuario.UsuarioDTO;
import br.com.mtashop.sso_api.model.entity.Usuario;
import br.com.mtashop.sso_api.service.AuthService;
import br.com.mtashop.sso_api.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UsuarioService usuarioService;

    public AuthController(AuthService authService, UsuarioService usuarioService) {
        this.authService = authService;
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Faz login com email e senha e retorna JWT")
    @PostMapping(value = "/autenticar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthResponseDTO login(@Valid @RequestBody AuthRequestDTO dto) {
        return authService.login(dto);
    }

    @Operation(summary = "Cria um novo usuário")
    @PostMapping(value = "/registrar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UsuarioDTO registrar(@Valid @RequestBody RegistrarUsuarioDTO dto) {
        Usuario u = usuarioService.registrar(dto);
        return new UsuarioDTO(u.getPublicId(), u.getNome(), u.getEmail(), u.getPermissao().name(), u.getStatus());
    }
}