package br.com.mtashop.sso_api.security.jwt;

import br.com.mtashop.sso_api.security.user.CustomUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtParser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms:3600000}")
    private Long expirationMs;

    private SecretKey signingKey;
    private JwtParser parser;

    @PostConstruct
    public void init() {
        if (secret == null || secret.getBytes(StandardCharsets.UTF_8).length < 32) {
            throw new IllegalStateException("jwt.secret must be defined and at least 32 bytes long");
        }

        signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        parser = Jwts.parser()
                .verifyWith(signingKey)
                .build();
    }

    public String gerarToken(CustomUserDetails user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("publicId", user.getUsuario().getPublicId())
                .claim("permissao", user.getUsuario().getPermissao().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extrairEmail(String token) {
        return parser.parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean valido(String token) {
        try {
            parser.parseSignedClaims(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}