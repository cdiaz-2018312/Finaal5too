package org.in5bm.emanueljolonCarlosDiaz.models;

/**
 *
 * @author Emanuel Esau Jolon Tzul
 * @date 26/04/2022 
 * @Grado: Quinto Perito
 * @Carnet: 2021058
 */

public class Salones {
    private String codigo;
    private String descripcion;
    private int capacidad;
    private String edificio;
    private int nivel;

    //CONSTRUCTOR NULO
    public Salones(){
        
    }
    
    public Salones(String codigo){
      this.codigo = codigo;  
    }
    
    public Salones(String descripcion, int capacidad){
        this.descripcion = descripcion;
        this.capacidad = capacidad;
        
        
    }
    
    //CONSTRUCTOR CON PARAMETROS
    public Salones(String descripcion, int capacidad, String edificio, int nivel){
        this.descripcion = descripcion;
        this.capacidad = capacidad;
        this.edificio = edificio;
        this.nivel = nivel;
      
        
    }

    public Salones(String codigo, String descripcion, int capacidad, String edificio, int nivel){
       this.codigo = codigo;
       this.descripcion = descripcion;
       this.capacidad = capacidad;
       this.edificio = edificio;
       this.nivel = nivel;
     
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "Salones{" + "codigo=" + codigo + ", descripcion=" + descripcion + ", capacidad=" + capacidad + ", edificio=" + edificio + ", nivel=" + nivel + '}';
    }



}
        
        
        

