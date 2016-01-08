package net.ddns.javierlopm.quienlibre;

import android.content.Context;

/**
 * Created by javierlopm on 05/01/16.
 */
public class Materia {
    public String nombre;
           String codigo;
    private ModeloHorario mh;

    Materia (Context context, String nombre,String codigo){
        mh = new ModeloHorario(context);
        this.nombre = nombre;
        this.codigo = codigo;
    }

    Materia (ModeloHorario mh, String nombre,String codigo){
        this.mh = mh;
        this.nombre = nombre;
        this.codigo = codigo;
    }

    public void agregar(){
        mh.agregarMateria(nombre,codigo);
    }


}
