package net.ddns.javierlopm.quienlibre;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class MenuPrincipal extends Activity {

    Boolean trimestreSeleccionado = false;
    Boolean tengoHorario          = false;
    Boolean tengoAmigos           = false; // Forever alone var
    File archivoTrimestre;
    String trimestre;
    int anio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        //archivoTrimestre.delete(); //Util en caso de reinicio en formato del archivo
        actualizarVariables();

    }

    public void actualizarVariables(){
        archivoTrimestre = new File(getApplicationContext().getFilesDir(),"trimestreActual");
        //archivoTrimestre.delete();
        trimestreSeleccionado = archivoTrimestre.exists();

        String buffer;
        String[] tokens;

        if(trimestreSeleccionado){
            try {
                //Lectura y tokenize
                Scanner lector = new Scanner(archivoTrimestre);
                buffer = lector.nextLine();
                tokens = buffer.split(" ");

                //Actualizacion de variables
                anio = Integer.parseInt(tokens[0]);
                trimestre = tokens[1];

                //Consulta para ver si tengo amigos :D y un horario
                ModeloHorario modeloHorario = new ModeloHorario(this);
                SQLiteDatabase db = modeloHorario.getReadableDatabase();

                String[] argumentos = new String[3];
                argumentos[0] = trimestre;
                argumentos[1] = Integer.toString(anio);

                String[] arg = new String[1];
                arg[0] = "me";

                tengoAmigos = DatabaseUtils.longForQuery(
                                    db,
                                    "SELECT COUNT(*) FROM horarios WHERE nombre!=?",
                                    arg) > 0L;

                tengoHorario = DatabaseUtils.longForQuery(
                                    db,
                                    "SELECT COUNT(*) FROM horarios WHERE nombre=?",
                                    arg) > 0L;

                db.close();




            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }


    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_principal, menu);
        return true;
    }*/

    @Override
    public void onResume(){
        super.onResume();
        actualizarVariables();
    }


    public void enviarMensaje(String mensaje){
        Toast toast = Toast.makeText(
                getApplicationContext(),
                mensaje,
                Toast.LENGTH_SHORT);
        toast.show();
    }

    //Funcion para que cada boton inicie el activity correspondiente
    public void cambTrim(View view){
        Intent intent = new Intent(this, CambioTrimestre.class);
        startActivity(intent);

    }
    public void agregarPersona(View view){
        Intent intent = new Intent(this, AdicionHorario.class);
        startActivity(intent);
    }
    public void verDisp(View view){
        actualizarVariables();
        if (trimestreSeleccionado && tengoHorario && tengoAmigos){
            //Si to_do marcha bien mostrar horario con disponibilidad de amigos
            Intent intent = new Intent(this, VisualizarQuienLibre.class);
            startActivity(intent);

        }
        else if(!trimestreSeleccionado){
            enviarMensaje("Parece que no has seleccionado un trimestre! Qué esperas?");
        }
        else if(trimestreSeleccionado && !tengoHorario){
            enviarMensaje("Parece que has olvidado agrega tu horario este trimestre");
        }
        else if(trimestreSeleccionado && tengoHorario && !tengoAmigos){
            enviarMensaje("Parece que no tienes el horario de nadie mas este trimestre :(");
        }
        else {
            enviarMensaje("Sinceramente no se que hago aqui");
        }

    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
}
