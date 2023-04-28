package org.in5bm.emanueljolonCarlosDiaz.models;

/**
 *
 * @author Emanuel Esau Jolon Tzul
 * @date 26/04/2022 
 * @Grado: Quinto Perito
 * @Carnet: 2021058
 */

public class Carreras {
    private String codigo;
    private String carrera;
    private String grado;
    private String seccion;
    private String jornada;

    //CONSTRUCTOR NULO
    public Carreras(){
        
    }
    
    public Carreras(String codigo){
      this.codigo = codigo;  
    }
    
    public Carreras(String codigo, String carrera, String grado){
        this.codigo = codigo;
        this.carrera = carrera;
        this.grado = grado;
        
    }
    
    //CONSTRUCTOR CON PARAMETROS
    public Carreras(String carrera, String grado, String seccion, String jornada){
        this.carrera = carrera;
        this.grado = grado;
        this.seccion = seccion;
        this.jornada = jornada;
      
        
    }

    public Carreras(String codigo, String carrera, String grado, String seccion, String jornada){
       this.codigo = codigo;
       this.carrera = carrera;
        this.grado = grado;
        this.seccion = seccion;
        this.jornada = jornada;
     
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    @Override
    public String toString() {
        return "Carreras{" + "codigo=" + codigo + ", carrera=" + carrera + ", grado=" + grado + ", seccion=" + seccion + ", jornada=" + jornada + '}';
    }


}
        
        
        

