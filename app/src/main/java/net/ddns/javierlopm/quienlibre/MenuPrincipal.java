package net.ddns.javierlopm.quienlibre;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class MenuPrincipal extends ActionBarActivity {

    Boolean trimestreSeleccionado = false;
    File archivoTrimestre;
    String trimestre;
    int anio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        archivoTrimestre = new File(getApplicationContext().getFilesDir(),"trimestreActual");
        //archivoTrimestre.delete(); //Util en caso de reinicio en formato del archivo
        trimestreSeleccionado = archivoTrimestre.exists();
        
        if(trimestreSeleccionado){
            try {
                Scanner lector = new Scanner(archivoTrimestre);
                anio = lector.nextInt();
                trimestre = lector.nextLine();
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

    public void vamosSeleciona(){
        Toast toast = Toast.makeText(
                getApplicationContext(),
                "Parece que no has seleccionado un trimestre! Qué esperas?",
                Toast.LENGTH_SHORT);
        toast.show();
    }

    //Funcion para que cada boton inicie el activity correspondiente
    public void cambTrim(View view){
        Intent intent = new Intent(this, CambioTrimestre.class);
        startActivity(intent);

    }
    public void agregarPersona(View view){
        if (trimestreSeleccionado){
            Toast toast = Toast.makeText(
                    getApplicationContext(),
                    trimestre,
                    Toast.LENGTH_SHORT);
            toast.show();
        }
        else{
            vamosSeleciona();
        }

    }
    public void verDisp(View view){
        if (trimestreSeleccionado){

        }
        else{
            vamosSeleciona();
        }
    }

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
}
