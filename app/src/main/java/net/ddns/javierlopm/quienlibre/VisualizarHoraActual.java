package net.ddns.javierlopm.quienlibre;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.TimeZone;


public class VisualizarHoraActual extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        View view = inflater.inflate(R.layout.fragment_visualizar_hora_actual,container,false);
        TextView texto = (TextView) view.findViewById(R.id.esHora);
        texto.setText(Integer.toString( obtenerHora()));

        if(getActivity() == null) Log.w("Visualizar Actual", "hey... actividad null");

        Spinner sp = (Spinner) view.findViewById(R.id.spLibres);
        ArrayAdapter<String> personas = libres(getActivity(),obtenerHora());
        sp.setAdapter(personas);


        return view;
    }

    public int obtenerHora(){
        Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int minutos = c.get(Calendar.MINUTE);
        if      ( (7  == hora && minutos > 30) || (hora == 8 && minutos <= 30)) return 1;
        else if ( (8  == hora && minutos > 30) || (hora == 9 && minutos <= 30)) return 2;
        else if ( (9  == hora && minutos > 30) || (hora == 10 && minutos <= 30)) return 3;
        else if ( (10 == hora && minutos > 30) || (hora == 11 && minutos <= 30)) return 4;
        else if ( (11 == hora && minutos > 30) || (hora == 12 && minutos <= 30)) return 5;
        else if ( (12 == hora && minutos > 30) || (hora == 13 && minutos <= 30)) return 6;
        else if ( (13 == hora && minutos > 30) || (hora == 14 && minutos <= 30)) return 7;
        else if ( (14 == hora && minutos > 30) || (hora == 15 && minutos <= 30)) return 8;
        else return 0;
    }
    public String obtenerDia(){

        TimeZone timezone = TimeZone.getDefault();
        Calendar cal = new GregorianCalendar(timezone);

        Locale l = new Locale("es", "ES");

        return cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, l);


    }

    public ArrayAdapter<String> libres(Activity c ,int hora){
        String[] tokens;
        String buffer;
        String[] arg = new String[4];

        File archivoTrimestre = new File(c.getFilesDir(),"trimestreActual");
        try{
            Scanner lector = new Scanner(archivoTrimestre);
            buffer = lector.nextLine();
            tokens = buffer.split(" ");
            //anio = Integer.parseInt(tokens[0]);
            //trimestre = tokens[1];


            arg[0] = tokens[1];
            arg[1] = tokens[0];
            arg[2] = Integer.toString(hora);
            arg[3] = obtenerDia();

            //Log.w("visualizar","es dia " + arg[3]);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ModeloHorario db = new ModeloHorario(c);
        SQLiteDatabase sql = db.getReadableDatabase();
        //Arreglo inutil que sqlite necesita...
        String[] columnas = {"nombre"};
        Cursor res = sql.query(
                true, //Distinct
                "horarios", //Tabla
                 columnas,
                    "trimestre=?     AND " +
                    "anio=?          AND " +
                    "nombre!=\'me\'  AND " +
                    "hora=?          AND " +
                    "dia=?",
                arg,null,null,null,null);

        List<String> personas = new ArrayList<String>();

        int columna = res.getColumnIndex("nombre");

        Log.w("Visualizar hora",Integer.toString(hora));

        if(res.moveToFirst()){
            do{
                personas.add(res.getString(columna));
                Log.w("VisualizarHora", "Agregando a la lista a " + res.getString(columna));
                if(!res.isLast()) res.moveToNext();
            }while(!res.isLast());
        }


        ArrayAdapter<String> ar = new ArrayAdapter(c,android.R.layout.simple_spinner_item,personas);
        ar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return ar;
    }

}
