package br.com.mtashop.sso_api.service;

import br.com.mtashop.sso_api.dto.auth.AuthRequestDTO;
import br.com.mtashop.sso_api.dto.auth.AuthResponseDTO;
import br.com.mtashop.sso_api.model.entity.Usuario;
import br.com.mtashop.sso_api.security.user.CustomUserDetails;
import br.com.mtashop.sso_api.security.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authManager;
    private final UsuarioService usuarioService;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authManager, UsuarioService usuarioService, JwtService jwtService) {
        this.authManager = authManager;
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
    }

    public AuthResponseDTO login(AuthRequestDTO dto) {
        var token = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());
        authManager.authenticate(token); // lança se inválido

        Usuario u = usuarioService.buscarPorEmail(dto.email());
        var userDetails = new CustomUserDetails(u);
        String jwt = jwtService.gerarToken(userDetails);
        return new AuthResponseDTO(jwt, u.getPublicId(), u.getNome(), u.getPermissao().name());
    }
}