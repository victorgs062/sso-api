package br.com.mtashop.sso_api.service;

import br.com.mtashop.sso_api.dto.usuario.RegistrarUsuarioDTO;
import br.com.mtashop.sso_api.exception.auth.EmailJaExisteException;
import br.com.mtashop.sso_api.exception.usuario.UsuarioNaoEncontradoException;
import br.com.mtashop.sso_api.model.enums.Permissao;
import br.com.mtashop.sso_api.model.entity.Usuario;
import br.com.mtashop.sso_api.repository.UsuarioRepository;
import br.com.mtashop.sso_api.util.PublicIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;
    private final PasswordEncoder encoder;
    private final PublicIdGenerator publicIdGenerator;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository repo, PasswordEncoder encoder, PublicIdGenerator publicIdGenerator) {
        this.repo = repo;
        this.encoder = encoder;
        this.publicIdGenerator = publicIdGenerator;
    }

    @Transactional
    public Usuario registrar(RegistrarUsuarioDTO dto) {
        if (repo.existsByEmail(dto.email())) {
            throw new EmailJaExisteException("E-mail já cadastrado.");
        }
        Usuario u = new Usuario();
        u.setPublicId(publicIdGenerator.generateUniquePublicId());
        u.setNome(dto.nome());
        u.setEmail(dto.email());
        u.setSenha(encoder.encode(dto.senha()));
        u.setPermissao(Permissao.USUARIO);
        u.setStatus(true);
        return repo.save(u);
    }

    public Usuario buscarPorEmail(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
    }

    public Usuario buscarPorPublicId(Integer publicId) {
        return repo.findAll()
                .stream()
                .filter(u -> publicId.equals(u.getPublicId()))
                .findFirst()
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }
}