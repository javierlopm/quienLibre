package net.ddns.javierlopm.quienlibre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class AgregarComprobante extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_comprobante);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_agregar_comprobante, menu);
        return true;
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

    public void conectarDace(View view){
        EditText usuario = (EditText) findViewById(R.id.stringUsbId);
        EditText clave   = (EditText) findViewById(R.id.stringPassword);

        ModeloHorario md = new ModeloHorario(this);

        Boolean paso = false;

        if (md.hayHorario()){
            enviarMensaje("Parece que ya tenías horario :/ shame on you");
            Intent intent = new Intent(this, MenuPrincipal.class);
            startActivity(intent);
        }

        HorarioParser hp = new HorarioParser(this,usuario.getText().toString(),clave.getText().toString());
        paso = hp.agregarHorario();

        if (paso){
            enviarMensaje("Éxito! horario agregado!");
            Intent intent = new Intent(this, MenuPrincipal.class);
            startActivity(intent);
        }else {
            enviarMensaje("Lo siento, algo extraño ha ocurrido :(");

        }
    }

    public void enviarMensaje(String mensaje){
        Toast toast = Toast.makeText(
                getApplicationContext(),
                mensaje,
                Toast.LENGTH_SHORT);
        toast.show();
    }
}
