package net.ddns.javierlopm.quienlibre;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Implementacion de horario como un arreglo de horas libres y sus botones respectivos
 *
 * Cada arreglo es de 3 dimensiones [bloqueHora] [diaDeSemana] [PosicionDeBloqueHora]
 * Los bloques de hora son 1-2, 3-4, 5-6 y 7-8 la posicionDeBloque hora indica cual numero de la
 * tupla se debe tomar
 *
 * Created by javierlopm on 17/04/15.
 */
public class Horario {
    private Boolean[][][] libre;      //Arreglo para consultas y almacenamientos de horas libres
    private int[][][]       bot;      //Arreglo con los id de los botones usados en agregarTabla.xml
    View view;

    public Horario(View v){
        libre = new Boolean[4][5][2];
        bot   = new     int[4][5][2];
        inicializarBotones();
        inicializarLibres();
        view = v;
    }

    public void agregarHora(int hora,String dia){
        int bloque,diaEntero,pos;

        diaEntero = DiaAInt(dia);
        pos    = hora % 2;
        bloque = (hora/2 + pos) -1;


        libre[bloque][diaEntero][pos] = true;
    }

    public void actualizarBotones(){

        int supSelected    =  R.drawable.supselected;       //todo Deberia cambiar los selected por algo azul en vez de girs
        int supNotSelected =  R.drawable.supnotselected;
        int botSelected    =  R.drawable.botselected;
        int botNotSelected =  R.drawable.botnotselected;

        Button aux;

        for (int i=0;i<4;i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k <= 1; k++) {

                    aux = (Button) view.findViewById(bot[i][j][k]);

                    switch (k) {
                        case 0:
                            if (libre[i][j][k]){aux.setBackgroundResource(supSelected);
                                Log.w("Horario"," Aqui estoy colocando uno marcado");}
                            else aux.setBackgroundResource(supNotSelected);
                            break;
                        case 1:
                            if (libre[i][j][k]) aux.setBackgroundResource(botSelected);
                            else {aux.setBackgroundResource(botNotSelected);
                                Log.w("Horario"," Aqui estoy colocando uno marcado");}
                            break;
                    }
                }
            }
        }



    }

    public void inicializarLibres(){
        for (int i=0;i<4;i++) for (int j=0;j<5;j++) for (int k=0;k<=1;k++)libre[i][j][k] = false;
    }

    public void cargarTodosLibres(Context c){

        inicializarLibres();

        String[] tokens;
        String buffer;
        String[] arg = new String[2];

        File archivoTrimestre = new File(c.getFilesDir(),"trimestreActual");

        try{
            Scanner lector = new Scanner(archivoTrimestre);
            buffer = lector.nextLine();
            tokens = buffer.split(" ");

            arg[0] = tokens[1];
            arg[1] = tokens[0];

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ModeloHorario db = new ModeloHorario(c);            //todo mover consulta a ModeloHorario
        SQLiteDatabase sql = db.getReadableDatabase();
        //Arreglo inutil que sqlite necesita...
        String[] columnas = {"hora","dia"};
        Cursor res = sql.query(
                true, //Distinct
                "horarios", //Tabla
                columnas,
                "trimestre=?     AND " +
                        "anio=?          AND " +
                        "nombre!=\'me\'      ",
                arg,null,null,null,null);


        int colHora = res.getColumnIndex("hora");
        int colDia  = res.getColumnIndex("dia");


        if(res.moveToFirst()){
            do{
                agregarHora(res.getInt(colHora), res.getString(colDia));

                if(!res.isLast()) res.moveToNext();
            }while(!res.isLast());
        }
    }


    private void inicializarBotones(){
        bot[0][0][0] = R.id.but00a;
        bot[0][0][1] = R.id.but00b;
        bot[0][1][0] = R.id.but01a;
        bot[0][1][1] = R.id.but01b;
        bot[0][2][0] = R.id.but02a;
        bot[0][2][1] = R.id.but02b;
        bot[0][3][0] = R.id.but03a;
        bot[0][3][1] = R.id.but03b;
        bot[0][4][0] = R.id.but04a;
        bot[0][4][1] = R.id.but04b;

        bot[1][0][0] = R.id.but10a;
        bot[1][0][1] = R.id.but10b;
        bot[1][1][0] = R.id.but11a;
        bot[1][1][1] = R.id.but11b;
        bot[1][2][0] = R.id.but12a;
        bot[1][2][1] = R.id.but12b;
        bot[1][3][0] = R.id.but13a;
        bot[1][3][1] = R.id.but13b;
        bot[1][4][0] = R.id.but14a;
        bot[1][4][1] = R.id.but14b;

        bot[2][0][0] = R.id.but20a;
        bot[2][0][1] = R.id.but20b;
        bot[2][1][0] = R.id.but21a;
        bot[2][1][1] = R.id.but21b;
        bot[2][2][0] = R.id.but22a;
        bot[2][2][1] = R.id.but22b;
        bot[2][3][0] = R.id.but23a;
        bot[2][3][1] = R.id.but23b;
        bot[2][4][0] = R.id.but24a;
        bot[2][4][1] = R.id.but24b;

        bot[3][0][0] = R.id.but30a;
        bot[3][0][1] = R.id.but30b;
        bot[3][1][0] = R.id.but31a;
        bot[3][1][1] = R.id.but31b;
        bot[3][2][0] = R.id.but32a;
        bot[3][2][1] = R.id.but32b;
        bot[3][3][0] = R.id.but33a;
        bot[3][3][1] = R.id.but33b;
        bot[3][4][0] = R.id.but34a;
        bot[3][4][1] = R.id.but34b;
    }
    public int DiaAInt(String a) {
        int dia;
        switch (a) {
            case "lunes":
                dia = 0;
                break;
            case "martes":
                dia = 1;
                break;
            case "miércoles":
                dia = 2;
                break;
            case "jueves":
                dia = 3;
                break;
            case "viernes":
                dia = 4;
                break;
            default:
                dia = -1;
                break;
        }
        return dia;
    }
    public String intADia(int a){
        String dia;
        switch (a) {
            case 0:  dia = "lunes";
                break;
            case 1:  dia = "martes";
                break;
            case 2:  dia = "miércoles";
                break;
            case 3:  dia = "jueves";
                break;
            case 4:  dia = "viernes";
                break;
            default: dia = "invalido";
                break;
        }

        return  dia;
    }
}
