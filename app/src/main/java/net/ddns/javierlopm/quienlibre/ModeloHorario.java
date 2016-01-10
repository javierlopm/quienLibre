package net.ddns.javierlopm.quienlibre;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Arrays;

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
                    "codigo_materia VARCHAR(10) NOT NULL);";
                    //"FOREIGN KEY(codigo_materia) REFERENCES materias(codigo));";

    String stringCrearBloqueAmigo =
            "CREATE TABLE bloque_amigo(" +
                    "hora      NUMERIC(3) NOT NULL," +
                    "dia      NUMERIC(10) NOT NULL," +
                    "nombre   TEXT       NOT NULL);" ;

    ModeloHorario(Context context){
        super(context, "quienlibre", null, 17);
    }

    public void onCreate(SQLiteDatabase db){
//        db.execSQL(stringCrearHorarios);
        db.execSQL(stringCrearMaterias);
        db.execSQL(stringCrearBloquePropio);
        db.execSQL(stringCrearBloqueAmigo);
        //this.poblar();
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS bloque_personal;");
//        db.execSQL("DROP TABLE IF EXISTS materias; ");
        db.execSQL("DROP TABLE IF EXISTS bloque_amigo;");
        db.execSQL("DROP TABLE IF EXISTS horarios;");
//        db.execSQL(stringCrearHorarios);
        db.execSQL(stringCrearMaterias);
        db.execSQL(stringCrearBloquePropio);
        db.execSQL(stringCrearBloqueAmigo);
        //this.poblar();
    }

    public void limpiarTablas(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM bloque_personal;");
        db.execSQL("DELETE FROM materias; ");
        db.execSQL("DELETE FROM bloque_amigo;");
        db.close();
    }


    public void agregarClase( String trim, int anio, String nombre, String dia, int hora){
        ContentValues cv = new ContentValues();
        cv.put("trimestre", trim);
        cv.put("anio", anio);
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
        m.editar("LABORATORIO DE LENGUAJES DE PROGRAMACION I", "CI3661");
        m.agregar();
        m.editar("ACTIVIDAD FISICA,SALUD Y CALID. DE VIDA","PBB413");
        m.agregar();
        m.editar("SISTEMAS DE INFORMACION","PS1115");
        m.agregar();


        BloquePropio bp = new BloquePropio(this,3,Dias.Lunes,Edificio.AUL,214,"CI3641");
        agregarBloquePropio(bp);
        bp.edit(4, Dias.Lunes, Edificio.AUL, 214, "CI3641");
        agregarBloquePropio(bp);
        bp.edit(3, Dias.Miércoles, Edificio.AUL, 214, "CI3641");
        agregarBloquePropio(bp);
        bp.edit(4, Dias.Miércoles, Edificio.AUL, 214, "CI3641");
        agregarBloquePropio(bp);

        bp.edit(6, Dias.Viernes, Edificio.AUL, 012, "PS1115");
        agregarBloquePropio(bp);
        bp.edit(7, Dias.Viernes, Edificio.AUL, 012, "PS1115");
        agregarBloquePropio(bp);
        bp.edit(8, Dias.Viernes, Edificio.AUL, 012, "PS1115");
        agregarBloquePropio(bp);
        bp.edit(2, Dias.Viernes, Edificio.AUL, 015, "CI3661");
        agregarBloquePropio(bp);
        bp.edit(3, Dias.Viernes, Edificio.AUL, 015, "CI3661");
        agregarBloquePropio(bp);
        bp.edit(4, Dias.Viernes, Edificio.AUL, 015, "CI3661");
        agregarBloquePropio(bp);
        bp.edit(3, Dias.Jueves, Edificio.AUL, 107, "PS115");
        agregarBloquePropio(bp);
        bp.edit(4, Dias.Jueves, Edificio.AUL, 107, "PS115");
        agregarBloquePropio(bp);
        bp.edit(8, Dias.Miércoles, Edificio.EGE, 005, "PBB413");
        agregarBloquePropio(bp);
        bp.edit(3, Dias.Martes, Edificio.AUL, 205, "PS115");
        agregarBloquePropio(bp);
        bp.edit(4, Dias.Martes, Edificio.AUL, 205, "PS115");
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
        cv.put("persona", persona);
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

        return c.getCount()!=0;
    }

    public BloquePropio[] obtenerBloques(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM bloque_personal", null);
        if (c.getCount()>0){
            int i = 0;
            BloquePropio[] resultado = new BloquePropio[c.getCount()];

            BloquePropio bp;
            c.moveToFirst();
            do{
                bp = new BloquePropio(c);
                resultado[i] = bp;
                i++;
            }while (c.moveToNext());
            Arrays.sort(resultado);
            for (BloquePropio bpAux: resultado) {
//                Log.v(dbtag, bpAux.toString());
            }
            return resultado;
        } else {
            return null;
        }
    }

    public BloquePropio proximoBloque(){
        BloquePropio []bloques = obtenerBloques();
        BloquePropio bAux = new BloquePropio();

        if (bloques == null) return null;

        int i=0;
        while(i < bloques.length && bloques[i].compareTo(bAux) < 0 ) {
            i++;
        }

        if (i==bloques.length) return bloques[0];
        else return bloques[i];

    }

    public String obterNombreAsignatura(String codigo){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("materias",new String []{"nombre"},"codigo=?",new String[] {codigo},null,null,null,null);

        if (c.getCount() == 0) return null;
        else {
            c.moveToFirst();
            return c.getString(0);
        }
    }
}

