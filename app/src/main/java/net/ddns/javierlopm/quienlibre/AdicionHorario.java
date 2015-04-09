package net.ddns.javierlopm.quienlibre;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;


public class AdicionHorario extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    String nombre;
    int posicionFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicion_horario);

        Spinner spinner = (Spinner) findViewById(R.id.spPersona);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.persona, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_adicion_horario, menu);
        return true;
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        TextView editarNombre = (TextView) findViewById(R.id.ponNombre);

        Spinner spinner = (Spinner) findViewById(R.id.spPersona);
        RelativeLayout.LayoutParams posicion = (RelativeLayout.LayoutParams) spinner.getLayoutParams();


        if(pos != 1) {
            editarNombre.setVisibility(View.INVISIBLE);
            posicion.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
        }
        else {
            editarNombre.setVisibility(View.VISIBLE);
            posicion.addRule(RelativeLayout.CENTER_HORIZONTAL,0);
        }

        spinner.setLayoutParams(posicion);

        posicionFinal = pos;
    }

    public void mostrarTabla(View view){

        if (posicionFinal == 0)nombre = "me";
        else {
            TextView editarNombre = (TextView) findViewById(R.id.ponNombre);
            nombre = editarNombre.getText().toString();
        }

        Intent intent = new Intent(this, AgregarTablaHorario.class);
        intent.putExtra("nombre",nombre);
        startActivity(intent);
    }

    public void borrarTexto(View view){
        TextView editarNombre = (TextView) findViewById(R.id.ponNombre);
        editarNombre.setText("");
    }


    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
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
