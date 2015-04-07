package net.ddns.javierlopm.quienlibre;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;


public class CambioTrimestre extends Activity implements AdapterView.OnItemSelectedListener {

    int anioSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_trimestre);

        Spinner spinner = (Spinner) findViewById(R.id.spTrimestre);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.trimestres, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //Selector de anio
        NumberPicker pickAYear = (NumberPicker) findViewById(R.id.anioActual);
        pickAYear.setMinValue(2011);
        pickAYear.setMaxValue(2050); //Why not?
        pickAYear.setValue( Calendar.getInstance().get(Calendar.YEAR) );
        pickAYear.setWrapSelectorWheel(true);
        pickAYear.setOnValueChangedListener(
                new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        anioSeleccionado = newVal;
                    }
                }
        );

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cambio_trimestre, menu);
        return true;
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
    }*/



    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        parent.getItemAtPosition(pos);

        //Hacer caso para cada posicion y cargar en mem principal
    }

    public void actTrim(View view){
        //Realizamos escritura sobre el archivo de trimestre

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
