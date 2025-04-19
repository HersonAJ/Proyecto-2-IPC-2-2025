/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.security.Key;
import java.util.Date;

/**
 *
 * @author herson
 */
public class JWTHelper {

    private final String secretKey = "MiClaveSecreta"; // Clave para codificar
    private final long expirationTime = 3600000; // 1 hora en milisegundos
    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public JWTHelper() {
        // Algoritmo para crear y verificar tokens
        this.algorithm = Algorithm.HMAC256(secretKey);
        this.verifier = JWT.require(algorithm).build();
    }

    public String generateToken(String correo, String rol, String nombre, int idUsuario) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return JWT.create()
                .withSubject(correo)
                .withClaim("rol", rol)
                .withClaim("nombre", nombre)
                .withClaim("idUsuario", idUsuario)
                .withIssuedAt(now)
                .withExpiresAt(expiryDate)
                .sign(algorithm);
    }

    // Método para validar el token
    public boolean validateToken(String token) {
        try {
            verifier.verify(token); // Verifica el token
            return true;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Extraer información del token
    public String getCorreoFromToken(String token) {
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }

    public String getRolFromToken(String token) {
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getClaim("rol").asString();
    }

    public int getIdUsuarioFromToken(String token) {
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getClaim("idUsuario").asInt();
    }

    // Método combinado para validar el token y obtener el ID del usuario
    public int validateAndGetId(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new JWTVerificationException("Token inválido o ausente.");
        }

        token = token.substring(7); // Remover "Bearer "

        try {
            verifier.verify(token); // Validar el token
            return getIdUsuarioFromToken(token); // Devolver el ID del usuario
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Token inválido o vencido.");
        }
    }

    public boolean validateToken2(String token) {
        try {
            String cleanedToken = cleanToken(token);
            verifier.verify(cleanedToken); // Verifica el token
            return true;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Método auxiliar para limpiar el token

    public String cleanToken(String token) {
        if (token.startsWith("Bearer ")) {
            String cleanedToken = token.substring(7).trim();
            return cleanedToken;
        }
        return token.trim();
    }
}
