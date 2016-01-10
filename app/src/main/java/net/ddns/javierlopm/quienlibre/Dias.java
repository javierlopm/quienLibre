package net.ddns.javierlopm.quienlibre;

import java.util.Calendar;

/**
 * Created by javierlopm on 05/01/16.
 */
public enum Dias {
    Lunes,Martes,Miércoles,Jueves,Viernes,Sábado,Domingo;

    public static Dias obtenerDia(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.SUNDAY:
                return Domingo;
            case Calendar.MONDAY:
                return Lunes;
            case Calendar.TUESDAY:
                return Martes;
            case Calendar.WEDNESDAY:
                return Miércoles;
            case Calendar.THURSDAY:
                return Jueves;
            case Calendar.FRIDAY:
                return Viernes;
            case Calendar.SATURDAY:
                return Sábado;
        }
        return Lunes; // Fucking java
    }

    public Dias siguiente(){
        return values()[ (this.ordinal()+1) % values().length ];
    }


}
