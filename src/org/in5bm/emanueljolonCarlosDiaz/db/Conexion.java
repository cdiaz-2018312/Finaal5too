package org.in5bm.emanueljolonCarlosDiaz.db;

/**
 *
 * @author Carlos Diaz 
 * @date 3/05/2022 
 * @Grado: Quinto Perito
 * 
 */

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class Conexion {
    
    private final String URL;
    private final String IP_SERVER;
    private final String PORT;
    private final String DB;
    private final String USER;
    private final String PASSWORD;
    private Connection conexion;
   
    
    
    
    
    private static Conexion instancia;
    
    public static Conexion getInstance(){
        
        if(instancia == null){
            instancia = new Conexion();
        }
        return instancia;
        
    }
    
    private  Conexion(){
        IP_SERVER = "Localhost";
        PORT = "3306";
        DB = "control_academico_in5bm";
        USER = "kinal";
        PASSWORD  = "admin";
        
        URL = "jdbc:mysql://" + IP_SERVER +":"+ PORT + "/" + DB;
        
              try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión Exitosa");
            
            DatabaseMetaData dma = conexion.getMetaData();
            System.out.println("\nConnected to: " + dma.getURL());
            System.out.println("Driver: " + dma.getDriverName());
            System.out.println("Version: " + dma.getDriverVersion() + "\n");
            
        }catch(ClassNotFoundException e){   
            System.err.println("No se encuentra ninguna definición para la clase");
            e.printStackTrace();
   
        }catch(CommunicationsException e){
            System.err.println("No se puede establecer comunicación con el server de MYSQL");
            System.err.println("Verifique que el servicio de MSQL este levantado," +
                    "que la IP_SERVER y el puerto estén correctos");
            e.printStackTrace();
        }catch(SQLException e){
            System.err.println("Se produjo un error de tipo SQLException");
            System.out.println("SQLSate: "+ e.getSQLState());
            System.out.println("ErrorCode:" + e.getErrorCode());
            System.out.println("Message:" + e.getMessage());
            System.out.println("\n");
            e.printStackTrace();       
        }catch(Exception e){
            System.out.println("Se produjo un error al intentar establecer una conexión con la bd");
            e.printStackTrace();
        }
    }
    
    public Connection getConexion(){
        return conexion;
    }
}
