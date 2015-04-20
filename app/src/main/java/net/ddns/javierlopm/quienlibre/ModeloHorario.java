package net.ddns.javierlopm.quienlibre;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by javierlopm on 08/04/15.
 */
public class ModeloHorario extends SQLiteOpenHelper {

    String stringCrear = "CREATE TABLE " +
            "horarios( "    +
            "trimestre VARCHAR(22), "  +
            "anio NUMERIC, "           +
            "nombre VARCHAR(255), "    +
            "dia VARCHAR(10), "        +
            "hora NUMERIC(2), "        +
            "PRIMARY KEY(trimestre,anio,nombre,dia,hora));";

    String stringBorralo = "DROP TABLE horarios;";

    ModeloHorario(Context context){
        super(context,"quienlibre",null,12);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(stringCrear);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(stringBorralo);
        db.execSQL(stringCrear);
    }


    public void agregarClase(String trim, int anio, String nombre, String dia, int hora){
        ContentValues cv = new ContentValues();
        cv.put("trimestre",trim);
        cv.put("anio",anio);
        cv.put("nombre",nombre);
        cv.put("dia",dia);
        cv.put("hora",hora);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("horarios",null,cv);
        db.close();
    }
}
