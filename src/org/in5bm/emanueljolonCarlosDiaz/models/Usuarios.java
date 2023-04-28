
package org.in5bm.emanueljolonCarlosDiaz.models;

/**
 *
 * @author carlos
 */
public class Usuarios {
    private String usuario;
    private String pass;
    private String nombre;
    private int rol;
    private String usuario2;
    private String pass2;
    private String nombre2;
    private int rol2;
    
    public Usuarios (){
    };
    public Usuarios (String user,String pass, String nombre,int rol){
    this.usuario=user;
    this.pass=pass;
    this.nombre=nombre;
    this.rol=rol;
    };
    public void Usuarios2 (String user2,String pass2, String nombre2,int rol2){
    this.usuario2=user2;
    this.pass2=pass2;
    this.nombre2=nombre2;
    this.rol2=rol2;
    };

    public String getUsuario2() {
        return usuario2;
    }

    public void setUsuario2(String usuario2) {
        this.usuario2 = usuario2;
    }

    public String getPass2() {
        return pass2;
    }

    public void setPass2(String pass2) {
        this.pass2 = pass2;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public int getRol2() {
        return rol2;
    }

    public void setRol2(int rol2) {
        this.rol2 = rol2;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }
    
}
