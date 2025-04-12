/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackendDB;

import Modelos.HorarioGeneral;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author herson
 */
public class HorarioGeneralDB {
    
    //metodo para establecer el horario general 
    public boolean crearHorarioGeneral(List<HorarioGeneral> horarios) {
        String queryEliminar =  "DELETE FROM Horarios WHERE  ID_Empleado IS NULL"; //eliminar horarios generales existentes
        String queryInsertar = "INSERT INTO Horarios (Hora_Apertura, Hora_Cierre, Día_Semana, ID_Empleado) VALUES(?, ?, ?, NULL)";
        boolean exito = true;
        
        //elimiar horarios anteriores
        try (Connection connection = ConexionDB.getConnection();
                PreparedStatement eliminarStmt = connection.prepareStatement(queryEliminar)) {
            
            eliminarStmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
            exito = false;// si hay error se marca el fallo
        }
        
        //insertar los nuevos horarios
        for (HorarioGeneral horario : horarios) {
            try (Connection connection = ConexionDB.getConnection();
                    PreparedStatement insertarStmt = connection.prepareStatement(queryInsertar)) {
                
                insertarStmt.setTime(1, java.sql.Time.valueOf(horario.getHoraApertura()));
                insertarStmt.setTime(2, java.sql.Time.valueOf(horario.getHoraCierre()));
                insertarStmt.setInt(3, horario.getDiaSemana());
                insertarStmt.executeUpdate();
                
            } catch (SQLException e) {
                e.printStackTrace();
                exito = false; //hi hay error se marca el fallo 
            }
        }
        return exito;
    }
    
    //metodo para obtener el horario general 
    public List<HorarioGeneral> obtenerHorarioGeneral() {
        List<HorarioGeneral> horarios = new ArrayList<>();
        String query = "SELECT Día_Semana, Hora_Apertura, Hora_Cierre FROM Horarios WHERE ID_Empleado IS NULL";
        
        try (Connection connection = ConexionDB.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query);
                ResultSet resultSet = stmt.executeQuery()) {
            
            //leer los datos del resultSet y construir los objetos Horario
            while (resultSet.next()) {
                HorarioGeneral horario = new HorarioGeneral();
                horario.setDiaSemana(resultSet.getInt("Día_Semana"));
                horario.setHoraApertura(resultSet.getTime("Hora_Apertura").toLocalTime());
                horario.setHoraCierre(resultSet.getTime("Hora_Cierre").toLocalTime());
                horarios.add(horario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return horarios;
    }
}
