package net.ddns.javierlopm.quienlibre;

import android.content.Context;

/**
 * Created by javierlopm on 05/01/16.
 */
public class Materia {
    private String nombre = null;
    private ModeloHorario mh;
    public  String codigo = null;

    Materia (Context context, String nombre,String codigo){
        mh = new ModeloHorario(context);
        this.nombre = nombre;
        this.codigo = codigo;
    }

    Materia (Context context){
        mh = new ModeloHorario(context);
    }

    Materia (ModeloHorario mh, String nombre,String codigo){
        this.mh = mh;
        this.nombre = nombre;
        this.codigo = codigo;
    }

    public void editar(String nombre,String codigo){
        this.nombre = nombre;
        this.codigo = codigo;
    }

    public void agregar(){
        // Solo si no existe ya
        mh.agregarMateria(nombre,codigo);
    }

    public String obtenerNombre(){
        if (nombre!= null){
            return nombre;
        } else {
            return mh.obterNombreAsignatura(codigo);
        }
    }


}
