package net.ddns.javierlopm.quienlibre;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by javierlopm on 08/04/15.
 */
public class ModeloHorario extends SQLiteOpenHelper {
    public static final int COLUMNA_TRIMESTRE = 1;
    public static final int COLUMNA_ANIO      = 2;
    public static final int COLUMNA_NOMBRE    = 3;
    public static final int COLUMNA_HORA      = 4;

    String stringCrear = "CREATE TABLE " +
            "horarios( "    +
            "trimestre VARCHAR(22), "  +
            "anio NUMERIC, "           +
            "nombre VARCHAR(255), "    +
            "hora NUMERIC, "           +
            "PRIMARY KEY(trimestre,anio,nombre,hora));";

    String stringBorralo = "DROP TABLE horarios;";

    ModeloHorario(Context context){
        super(context,"quienlibre",null,2);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(stringCrear);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(stringBorralo);
        db.execSQL(stringCrear);
    }
}
