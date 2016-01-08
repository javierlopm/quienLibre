package net.ddns.javierlopm.quienlibre;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by javierlopm on 08/04/15.
 */
public class ModeloHorario extends SQLiteOpenHelper {

    private final String dbtag = "DATABASE";

    String stringCrearHorarios = "CREATE TABLE " +
            "horarios( "    +
            "trimestre VARCHAR(22), "  +
            "anio NUMERIC, "           +
            "nombre VARCHAR(255), "    +
            "PRIMARY KEY(trimestre,anio,nombre));";



    String stringCrearMaterias = "CREATE TABLE materias("+
                                    "nombre VARCHAR(255) NOT NULL," +
                                    "codigo VARCHAR(10)  NOT NULL," +
                                    "PRIMARY KEY (codigo));";

    String stringCrearBloquePropio =
            "CREATE TABLE bloque_personal(" +
                    "hora     NUMERIC(3) NOT NULL," +
                    "dia      NUMERIC(10)NOT NULL," +
                    "edificio VARCHAR(3) NOT NULL," +
                    "salon    NUMERIC(3) NOT NULL," +
                    "codigo_materia VARCHAR(10) NOT NULL," +
                    "FOREIGN KEY(codigo_materia) REFERENCES materias(codigo));";

    String stringCrearBloqueAmigo =
            "CREATE TABLE bloque_amigo(" +
                    "hora      NUMERIC(3) NOT NULL," +
                    "dia      NUMERIC(10) NOT NULL," +
                    "nombre   TEXT       NOT NULL);" ;

    ModeloHorario(Context context){
        super(context, "quienlibre", null, 15);
    }

    public void onCreate(SQLiteDatabase db){
//        db.execSQL(stringCrearHorarios);
        db.execSQL(stringCrearMaterias);
        db.execSQL(stringCrearBloquePropio);
        db.execSQL(stringCrearBloqueAmigo);
        //this.poblar();
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS bloque_personal;" +
                "DROP TABLE IF EXISTS bloque_amigo;" +
                "DROP TABLE IF EXISTS materias; " +
                "DROP TABLE IF EXISTS horarios;");
//        db.execSQL(stringCrearHorarios);
        db.execSQL(stringCrearMaterias);
        db.execSQL(stringCrearBloquePropio);
        db.execSQL(stringCrearBloqueAmigo);
        //this.poblar();
    }


    public void agregarClase( String trim, int anio, String nombre, String dia, int hora){
        ContentValues cv = new ContentValues();
        cv.put("trimestre",trim);
        cv.put("anio",anio);
        cv.put("nombre", nombre);
        cv.put("dia", dia);
        cv.put("hora", hora);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("horarios", null, cv);
        db.close();
    }

    public void agregarMateria(String nombre, String codigo){
        ContentValues cv = new ContentValues();
        cv.put("nombre", nombre);
        cv.put("codigo", codigo);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("materias", null, cv);
        db.close();
    }

    public void poblar(){
        Materia m = new Materia(this,"Lenguajes de programación 1","CI3641");
        m.agregar();

        BloquePropio bp = new BloquePropio(this,3,Dias.Lunes,Edificio.AUL,214,"CI3641");
        agregarBloquePropio(bp);
        bp.edit(4, Dias.Lunes, Edificio.AUL, 214, "CI3641");
        agregarBloquePropio(bp);
        bp.edit(3, Dias.Miércoles, Edificio.AUL, 214, "CI3641");
        agregarBloquePropio(bp);
        bp.edit(4, Dias.Miércoles, Edificio.AUL, 214, "CI3641");
        agregarBloquePropio(bp);

    }

    public void agregarBloquePropio(BloquePropio bp){
        ContentValues cv = new ContentValues();
        cv.put("hora", bp.hora);
        cv.put("dia", bp.dia.toString());
        cv.put("salon", bp.salon);
        cv.put("codigo_materia", bp.materia);
        cv.put("edificio", bp.edificio.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("bloque_personal", null, cv);
    }

    public void agregarBloquesAjenos(Integer horas[], Dias dias[] , String persona ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("persona",persona);
        for (int i = 0; i < horas.length ; i++) {
            cv.put("hora",horas[i]);
            cv.put("dia",dias[i].toString());
            db.insert("bloque_amigo",null,cv);
            cv.remove("hora");
            cv.remove("dia");
        }
    }

    public Materia cursorAMateria(Cursor c){
        return  null;
    }


    public boolean hayHorario(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM bloque_personal", null);

        BloquePropio bp;
        Log.v("InicioBloques","================");
        if (c.moveToFirst()){
            do {
                bp = new BloquePropio(c);
                Log.v(dbtag,bp.toString());
            }while (c.moveToNext());
        } else {
            Log.v(dbtag,"not wii?");
        }
        Log.v("FinBloques","===================");
        return c.getCount()!=0;
    }
}

