package org.in5bm.emanueljolonCarlosDiaz.system;

import org.in5bm.emanueljolonCarlosDiaz.db.Conexion;

/**
 *
 * @author Emanuel Esau Jolon Tzul
 * @date 5/05/2022 
 * @Grado: Quinto Perito
 * @Carnet: 2021058
 */
public class testConection {
    public static void main(String[] args){
        Conexion con = Conexion.getInstance();
        Conexion con2 = Conexion.getInstance();
        Conexion con3 = Conexion.getInstance();
    }
}
