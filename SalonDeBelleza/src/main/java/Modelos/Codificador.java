/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 *
 * @author herson
 */
public class Codificador {
    
    public Codificador() {
        
    }
    
    public String codificar(String texto) {
        if (texto == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(texto.getBytes(StandardCharsets.UTF_8));
    }
}
