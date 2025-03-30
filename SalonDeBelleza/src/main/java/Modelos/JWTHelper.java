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

    private final String secretKey = "MiClaveSecreta"; //clave para codificar
    private final long expirationTime = 3600000; // 1 hora en milisegundos
    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public JWTHelper() {
        // algoritmo para crear el token
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


    //metodo para validar el token de regreso
    public boolean validateToken(String token) {
        try {
            verifier.verify(token); // Verifica el token 
            return true;
        } catch (JWTVerificationException e) {
            e.printStackTrace(); 
            return false;
        }
    }


    public String getCorreoFromToken(String token) {
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }

    public String getRolFromToken(String token) {
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getClaim("rol").asString(); // Extre el rol 
    }
    
    public int getIdUsuarioFromToken(String token) {
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getClaim("idUsuario").asInt();
    }
}

