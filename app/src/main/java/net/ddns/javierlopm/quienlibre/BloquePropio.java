package net.ddns.javierlopm.quienlibre;


import android.content.Context;
import android.database.Cursor;

import java.util.Calendar;

/**
 * Created by javierlopm on 05/01/16.
 */
public class BloquePropio implements Comparable<BloquePropio> {
    public Integer hora;
    Dias           dia;
    Edificio       edificio;
    Integer        salon;
    String         materia;
    private ModeloHorario mh;

    public BloquePropio(){
        Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int minutos = c.get(Calendar.MINUTE);
        int h;
        if      (hora <=  7 && minutos <= 30) h = 0;
        else if (estaEnBloque(7 ,hora, minutos)) h = 1;
        else if (estaEnBloque(8 ,hora,minutos)) h = 2;
        else if (estaEnBloque(9 ,hora,minutos)) h = 3;
        else if (estaEnBloque(10,hora,minutos)) h = 4;
        else if (estaEnBloque(11, hora, minutos)) h = 5;
        else if (estaEnBloque(12, hora, minutos)) h = 6;
        else if (estaEnBloque(13,hora,minutos)) h = 7;
        else if (estaEnBloque(14,hora,minutos)) h = 8;
        else h = 9;
        this.hora = h;
        this.dia = Dias.obtenerDia();
    }

    BloquePropio(Context c,Integer hora, Dias dia, Edificio edificio, Integer salon, String materia){
        mh = new ModeloHorario(c);
        this.hora     = hora;
        this.dia      = dia;
        this.salon    = salon;
        this.materia  = materia;
        this.edificio = edificio;
    }

    BloquePropio(ModeloHorario mh,Integer hora, Dias dia, Edificio edificio, Integer salon, String materia){
        this.mh       = mh;
        this.hora     = hora;
        this.dia      = dia;
        this.salon    = salon;
        this.materia  = materia;
        this.edificio = edificio;
    }

    BloquePropio(Integer hora, Dias dia){
        this.hora     = hora;
        this.dia      = dia;
    }

    BloquePropio(Context c){
        mh = new ModeloHorario(c);
    }

    BloquePropio(Cursor c){
        hora     = c.getInt(c.getColumnIndex("hora"));
        dia      = Dias.valueOf(c.getString(c.getColumnIndex("dia")));
        salon    = c.getInt(c.getColumnIndex("salon"));
        edificio = Edificio.valueOf(c.getString(c.getColumnIndex("edificio")));
        materia  = c.getString(c.getColumnIndex("codigo_materia"));
    }

    public void edit(Integer hora, Dias dia, Edificio edificio, Integer salon, String materia){
        this.hora     = hora;
        this.dia      = dia;
        this.salon    = salon;
        this.materia  = materia;
        this.edificio = edificio;
    }

    public void agregar(){
        mh.agregarBloquePropio(this);
    }

    @Override
    public int compareTo(BloquePropio otro){
        if (dia != otro.dia){
            return this.dia.compareTo(otro.dia);
        } else {
            return this.hora.compareTo(otro.hora);
        }
    }



    public String toString(){
        String s = "{ Hora:" + hora + "," +
                " Dia: "      + dia.toString() + "," +
                " Salon: "    + salon+ "," +
                " Edificio: " + edificio.toString() + "," +
                " Materia: "  + materia + "}";

        return s;
    }

    public String minToString(){
        String s = "{ Hora:" + hora + "," + " Dia: "      + dia.toString() + "}";
        return s;
    }

    static private boolean estaEnBloque(Integer h,Integer hComp,Integer min){
        return  (h  == hComp && min > 30) || (hComp == (h+1) && min <= 30);
    }

    public void siguienteHora(){
        if (hora==9){
            hora = 1;
            dia = dia.siguiente();
        } else {
            hora = hora+1;
        }
    }



}