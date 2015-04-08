package net.ddns.javierlopm.quienlibre;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Calendar;


public class CambioTrimestre extends Activity implements AdapterView.OnItemSelectedListener {

    int anioSeleccionado;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_trimestre);

        spinner = (Spinner) findViewById(R.id.spTrimestre);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.trimestres, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //Selector de anio
        anioSeleccionado = Calendar.getInstance().get(Calendar.YEAR);

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

    public void actTrim(View view){
        File trimestreActual = new File(getApplicationContext().getFilesDir(),"trimestreActual");

        if(trimestreActual.exists()){
            trimestreActual.delete();
        }

        try {
            trimestreActual.createNewFile();
            FileWriter     fw = new FileWriter(trimestreActual.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(String.valueOf(anioSeleccionado));
            bw.write(" ");
            bw.write(spinner.getSelectedItem().toString());
            bw.close();

            Toast toast = Toast.makeText(getApplicationContext(), "Se guardo marico!", Toast.LENGTH_SHORT);
            toast.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cambio_trimestre, menu);
        return true;
    }

    //Terminamos la actividad al regresar para que MenuPrincipal pueda verificar modificaciones
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            setResult(RESULT_OK);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }



    public void onItemSelected(AdapterView<?> parent, View view,
              int pos, long id) {
        int hue = 42;
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
